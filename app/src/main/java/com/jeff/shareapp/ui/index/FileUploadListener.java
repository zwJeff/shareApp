package com.jeff.shareapp.ui.index;

/**
 * Created by 张武 on 2016/5/31.
 */
public interface FileUploadListener {

    void onStart();
    void onUploading(int percent);
    void onSuccess(String result);
    void onFile();


}
