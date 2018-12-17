package com.ys.mgr.config;

import com.ys.mgr.util.MyStringUtils;
import com.ys.mgr.util.crypto.MyAesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 拓展HttpServletRequest，参数的自动加解密等
 *
 *
 * Date: 2017/12/8 11:55
 */
@Slf4j
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> parameterMap = new HashMap<>();
    private Vector<String> parameterNames = new Vector<>();

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     *
     * @throws IllegalArgumentException if the request is null
     */
    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        parseParameterValues(request);
    }

    private void parseParameterValues(HttpServletRequest request) {
        /*try {
            //1. 先解析body
            //如果采用了java的spring框架，在过滤器和拦截器中getReader了http流之后，这个流就不存在了
            String body = "";
            if (!ServletFileUpload.isMultipartContent(request)) {
                // body = IOUtils.toString(request.getReader());
                // java.lang.IllegalStateException: getInputStream() has already been called for this request

                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = null;
                try {
                    InputStream inputStream = request.getInputStream();
                    if (inputStream != null) {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        char[] charBuffer = new char[128];
                        int bytesRead;
                        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                            stringBuilder.append(charBuffer, 0, bytesRead);
                        }
                    } else {
                        stringBuilder.append("");
                    }
                } catch (IOException ex) {
                    log.error("parseParameterValues Error reading the request payload", ex);
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException iox) {
                            // ignore
                        }
                    }
                }
                body = stringBuilder.toString();
            }
            convertParams(body);
        } catch (Exception e) {
            log.error("parseParameterValues1 error ", e);
        }*/

        try {
            //2.再解析queryString
            Map<String, String[]> paramMap = super.getParameterMap();
            if (paramMap != null && !paramMap.isEmpty()) {
                for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                    if (entry.getValue() == null) {
                        continue;
                    }
                    String[] values = new String[entry.getValue().length];
                    for (int i = 0; i < entry.getValue().length; i++) {
                        if (isSecretParam(entry.getKey())) {
                            values[i] = entry.getValue()[i];
                            decrypt(entry.getKey(), values[i]);// 解密
                        } else {
                            values[i] = HtmlUtils.htmlEscape(entry.getValue()[i], "UTF-8");//防止XSS
                        }
                    }

                    String[] oldValues = this.parameterMap.get(entry.getKey());
                    this.parameterNames.add(entry.getKey());
                    if (oldValues == null) {
                        this.parameterMap.put(entry.getKey(), values);
                    } else {
                        this.parameterMap.put(entry.getKey(), ArrayUtils.addAll(oldValues, values));
                    }
                }
            }

        } catch (Exception e) {
            log.error("parseParameterValues2 error ", e);
        }

        //TODO 用户ID应过滤掉，只接受accessToken

    }

    private void decrypt(String paramName, String paramValue) {
        if (MyStringUtils.isBlank(paramName) || MyStringUtils.isBlank(paramValue)) {
            return;
        }
        // 解密，解密之后的参数添加到parameterNames和parameterMap，之后就可以直接获取到了
        String params;
        try {
            params = MyAesUtils.decrypt(paramValue, MyConst.getParamKeyByName(paramName));
        } catch (Exception e) {
            params = null;
            log.error("param decrypt error", e);
        }
        convertParams(params);
    }

    private void convertParams(String params) {
        if (MyStringUtils.isNotBlank(params)) {
            String[] kv = params.split("&");
            if (kv != null && kv.length > 0) {
                for (int i = 0; i < kv.length; i++) {
                    int pos = kv[i].indexOf("=");
                    if (pos != -1) {
                        String key = kv[i].substring(0, pos);
                        String value = kv[i].substring(pos + 1);
                        if (value == null) {
                            value = "";
                        }
                        if (isSecretParam(key)) {
                            decrypt(key, value);// 解密
                        } else {
                            value = HtmlUtils.htmlEscape(value, "UTF-8");//防止XSS
                        }

                        String[] oldValues = this.parameterMap.get(key);
                        this.parameterNames.add(key);
                        if (oldValues == null) {
                            this.parameterMap.put(key, new String[]{value});
                        } else {
                            this.parameterMap.put(key, ArrayUtils.add(oldValues, value));
                        }
                    }
                }
            }
        }
    }

    // 是否是加密参数
    private boolean isSecretParam(String param) {
        if (MyStringUtils.isBlank(param))
            return false;
        if (ArrayUtils.contains(MyConst.HASH_PARAM_NAMES, param))
            return true;
        return false;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        HtmlUtils.htmlEscape(value, "UTF-8");//防止XSS
        return value;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameterMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return this.parameterNames.elements();
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameterMap.get(name);
    }

}
