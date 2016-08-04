package com.jeff.shareapp.util;


/**
 * Created by 张武 on 2016/5/28.
 */
public interface MyVolleyListener <T>{

    void onSuccess(Object data);
    void onFailure(int failureCode,String failureMessage);
    void onError(int errorCode,String errorMessage);

}
