package com.leaf.response;

import lombok.Data;

public class Response<T> {
    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";


    private String code;
    private String msg;
    private T data;

    public Response(T data) {
        this.code = Response.SUCCESS;
        this.data = data;
    }

    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Response success(Object data){
        return new Response(data);
    }

    public static Response failed(String code, String msg){
        return new Response(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
