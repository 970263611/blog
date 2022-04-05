package com.dahuaboke.blog.model;

/**
 * auth: dahua
 * time: 2022/3/27 15:38
 */
public class Result {

    private boolean success;
    private Object data;

    public Result() {
    }

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result success(Object obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setData(obj);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(false);
        return result;
    }

    public static Result error(Object obj) {
        Result result = new Result();
        result.setSuccess(false);
        result.setData(obj);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
