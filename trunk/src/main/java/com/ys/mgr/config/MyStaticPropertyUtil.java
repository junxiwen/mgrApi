package com.ys.mgr.config;

/*import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;*/
import com.ys.mgr.util.MyDateUtils;
import com.ys.mgr.util.MyStringUtils;
import com.ys.mgr.util.http.MyHttpUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Date: 2017/12/28 12:04
 */
@Slf4j
@Component
@Data
public class MyStaticPropertyUtil {
    @Value("${custom.props.staticPath}")
    private String staticPath;

    private static Map<String, Object> staticProperties;

    public Map<String, Object> getProperties() {
        if (staticProperties == null) {
            try {
                staticProperties = new Yaml().loadAs(new FileInputStream(ResourceUtils.getFile(staticPath)), Map.class);
                log.debug("static properties : {}", staticProperties);
            } catch (Exception e) {
                log.error("load static properties error", e);
                return null;
            }
        }
        return staticProperties;
    }

    private Map<String, Object> getProxyConfig() {
        try {
            return (Map<String, Object>) getProperties().get("proxy");
        } catch (Exception e) {
            log.error("load static proxy properties error", e);
            return null; // 默认值
        }
    }

    public String getProxyUrl() {
        try {
            return getProxyConfig().get("url").toString();
        } catch (Exception e) {
            log.error("load static proxy.url properties error", e);
            return "http://api.ip.data5u.com/dynamic/get.html?order=61fbc26a3a7cd3f62bf8be2e7a60c83a&ttl=1&sep=3"; // 默认值
        }
    }

    public Integer getProxyTimeout() {
        try {
            return (Integer) getProxyConfig().get("url");
        } catch (Exception e) {
            log.error("load static proxy.timeout properties error", e);
            return 3000; // 默认值3秒
        }
    }

    public Map<String, Object> getResProperties() {
        try {
            return (Map<String, Object>) getProperties().get("res");
        } catch (Exception e) {
            log.error("load static res properties error", e);
            return null; // 默认值
        }
    }

    public String getResSavePath() {
        try {
            return ResourceUtils.getFile(getResProperties().get("savePath").toString()).getAbsolutePath();
        } catch (Exception e) {
            log.error("load static res.dir properties error", e);
            return null; // 默认值
        }
    }

    public String getResUrlPath() {
        try {
            return getResProperties().get("urlPath").toString();
        } catch (Exception e) {
            log.error("load static res.url properties error", e);
            return MyHttpUtils.getCurrentUrlPathNoSplash(); // 默认值
        }
    }

    public List<String> getFileSyncUrls() {
        try {
            return (List<String>) getResProperties().get("fileSyncUrl");
        } catch (Exception e) {
            log.debug("load static res.fileSyncUrl properties error", e);
            return null; // 默认值
        }
    }

    public String getFileSyncToken() {
        try {
            return getResProperties().get("fileSyncToken").toString();
        } catch (Exception e) {
            log.debug("load static res.fileSyncToken properties error", e);
            return null; // 默认值
        }
    }

    /**
     * 获取金楼短信平台营销短信url
     *
     * @return
     */
    public String getSmsUrl() {
        try {
            return getProperties().get("smsurl").toString();
        } catch (Exception e) {
            return null;
        }
    }

    //打款excel文件
    public String getExcelLocalPath() throws Exception{
        try {
            return getProperties().get("outComeExcelLocalPath").toString();
        } catch (Exception e) {
            return "D://";
        }
    }

    //excel文件名称
    public String getExcelName() throws Exception{
        String temp = MyDateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        try {
            String outComeExcelName = getProperties().get("outComeExcelName").toString();
            String outComeExcelSuffix = getProperties().get("outComeExcelSuffix").toString();
            return outComeExcelName+"-"+ temp +outComeExcelSuffix;
        }catch (Exception e){
            return "用户申请提现表-"+ temp +".xls";
        }
    }

    //nginx映射目录
    public String getExcelNginxPath() throws Exception{
        try {
            return getProperties().get("outComeExcelNginxPath").toString();
        } catch (Exception e) {
            return "http://192.168.0.22:8880/excel/";
        }
    }


    //获取推送对象
   /* public DefaultAcsClient getDefaultAcsClient() throws Exception{
        try {
            String regionId = getProperties().get("regionId").toString();
            String accessKeyId = getProperties().get("accessKeyId").toString();
            String accessKeySecret = getProperties().get("accessKeySecret").toString();
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
            return defaultAcsClient;
        }catch (Exception e){
            log.error("获取推送对象失败");
            return null;
        }
    }*/

    //获取appKeyForAndroid
    public Long getAppKeyForAndroid() throws Exception{
        try {
            String appKeyForAndroid = getProperties().get("appKeyForAndroid").toString();
            return Long.parseLong(appKeyForAndroid);
        }catch (Exception e){
            log.error("获取appKeyForAndroid失败,{}",e);
            return null;
        }
    }

    //获取appKeyForIOS
    public Long getAppKeyForIOS() throws Exception{
        try {
            String appKeyForIOS = getProperties().get("appKeyForIOS").toString();
            return Long.parseLong(appKeyForIOS);
        }catch (Exception e){
            log.error("获取获取appKeyForIOS失败,{}",e);
            return null;
        }
    }


    /**
     * 返回管理员邮箱列表
     *
     * @return
     */
    public String[] getReceiveMail() {
        String[] defaultMails = new String[]{"rongjian@miidi.net"};
        try {
            String recive = getProperties().get("receiveMail").toString();

            if (MyStringUtils.isEmpty(recive)) {
                throw new RuntimeException("未设置receiveMail值");
            } else {
                return recive.split(",");
            }
        } catch (Exception e) {
            log.error("load static receiveMail properties error", e);
            return defaultMails; // 默认值
        }
    }

}
