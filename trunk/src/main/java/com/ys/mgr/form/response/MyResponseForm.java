package com.ys.mgr.form.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RestAPI 返回值统一格式
 *
 *
 * Date: 2017-12-01 10:27:49
 **/
@Data // 自动生成get、set方法
@NoArgsConstructor // 自动生成没有参数的构造函数
public class MyResponseForm<T> {
    private int code = 0;
    private String message = "request success";
    private T data;

    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private static final int FAIL = 2;

    private static final int NO_LOGIN = 3;
    private static final int NO_PERMISSION = 4;

    /**
     * 构造函数
     * @param data 需要返回的数据
     */
    public MyResponseForm(T data) {
        this.data = data;
    }


    /**
     * 请求正确执行: code = 0
     *
     * @param message 提示信息，默认：request success
     * @return MyResponseForm
     */
    @JsonIgnore
    public MyResponseForm<T> returnSuccess(String... message) {
        this.code = SUCCESS;
        if (message == null || message.length == 0) {
            this.message = "request success";
        } else {
            this.message = message[0];
        }
        return this;
    }

    /**
     * 请求出现未知错误: code = 1
     *
     * @param message 提示信息，默认：request error
     * @return MyResponseForm
     */
    @JsonIgnore
    public MyResponseForm<T> returnError(String... message) {
        this.code = ERROR;
        if (message == null || message.length == 0) {
            this.message = "request error";
        } else {
            this.message = message[0];
        }
        return this;
    }

    /**
     * 请求失败: code = 2，需友好提示用户message信息
     *
     * @param message 提示信息，默认：request fail
     * @return MyResponseForm
     */
    @JsonIgnore
    public MyResponseForm<T> returnFail(String... message) {
        this.code = FAIL;
        if (message == null || message.length == 0) {
            this.message = "request fail";
        } else {
            this.message = message[0];
        }
        return this;
    }

    /**
     * 需要登陆的api用户没有登陆: code = 3，需友好提示用户没有登陆
     *
     * @param message 提示信息，默认：no login
     * @return MyResponseForm
     */
    @JsonIgnore
    public MyResponseForm<T> returnNoLogin(String... message) {
        this.code = NO_LOGIN;
        if (message == null || message.length == 0) {
            this.message = "no login";
        } else {
            this.message = message[0];
        }
        return this;
    }

    /**
     * 需要权限的api用户没有相应权限: code = 4，需友好提示用户没有相应权限
     *
     * @param message 提示信息，默认：no permission
     * @return MyResponseForm
     */
    @JsonIgnore
    public MyResponseForm<T> returnNoPermission(String... message) {
        this.code = NO_PERMISSION;
        if (message == null || message.length == 0) {
            this.message = "no permission";
        } else {
            this.message = message[0];
        }
        return this;
    }
}
