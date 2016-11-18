package com.jeff.shareapp.net;


/**
 * Created by 张武 on 2016/5/28.
 */
public interface MyVolleyListener <T>{

    void onSuccess(T data);
    void onFailure(int failureCode,String failureMessage);
    void onError(int errorCode,String errorMessage);

}
