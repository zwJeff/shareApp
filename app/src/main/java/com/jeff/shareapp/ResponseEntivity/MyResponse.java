package com.jeff.shareapp.ResponseEntivity;

/**
 * Created by 张武 on 2016/5/29.
 */
public class MyResponse<T> {


    private int status;
    private String message;
    private int total;
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
