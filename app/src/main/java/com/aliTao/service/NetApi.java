package com.aliTao.service;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 接口请求类
 */

public class NetApi {

    protected static void invokeGet(String url, Map<String, String> params, Callback callback) {
        OkHttpUtils.get().url(url)
                .params(params == null ? new HashMap<String, String>() : params)
                .headers(getHeaders())
                .build()
                .execute(callback);
    }

    /**
     * 普通Post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    protected static void invokePost(String url, Map<String, String> params, Callback callback) {
        OkHttpUtils.post().url(url)
                .headers(getHeaders())
                .build()
                .execute(callback);
    }

    /**
     * form-dataPost请求
     *
     * @param url
     * @param params
     * @param callback
     */
    protected static void invokePostFormData(String url, Map<String, String> params, Map<String, File> files, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("courseId", params.get("courseId"));
        builder.addFormDataPart("current", params.get("current"));
        builder.addFormDataPart("amount", params.get("amount"));
       // builder.addFormDataPart("payId", params.get("payId"));
        builder.addFormDataPart("payMoney", params.get("payMoney"));
        for (int i = 0; i < files.size(); i++) {
            builder.addFormDataPart("filedata", files.get("filePath"+i).getName(), RequestBody.create(MediaType.parse("image/png"), files.get("filePath"+i)));
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //创建Request
        final Request request = new Request.Builder().url(url).headers(Headers.of(getHeaders())).post(body).build();
        client.newCall(request).enqueue(callback);
    }
    /**
     * form-dataPost请求
     *
     * @param url
     * @param params
     * @param callback
     */
    protected static void invokePostTransfer(String url, Map<String, String> params, Map<String, File> files, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("current", params.get("current"));
        builder.addFormDataPart("amount", params.get("amount"));
        for (int i = 0; i < files.size(); i++) {
            builder.addFormDataPart("filedata", files.get("filePath"+i).getName(), RequestBody.create(MediaType.parse("image/png"), files.get("filePath"+i)));
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //创建Request
        final Request request = new Request.Builder().url(url).headers(Headers.of(getHeaders())).post(body).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Json 请求
     *
     * @param url
     * @param string
     * @param callback
     */
    protected static void invokePostJson(String url, String string, Callback callback) {
        OkHttpUtils.postString()
                .headers(getHeaders())
                .url(url)
                .content(string)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callback);
    }


    protected static void invokePostUpload(String url, Map<String, String> params, Map<String, File> files, Callback callback) {
        Map<String, String> hearder = new HashMap<>();
        hearder.put("User-Agent", "okhttp/3.3.1");
        hearder.put("accept-encoding", "gzip, deflate");
        hearder.put("accept", "*/*");
        hearder.put("Content-Type", "image/jpeg");

        OkHttpUtils.post().url(url)
                .headers(hearder)
                .files("file", files)
                .build()
                .execute(callback);
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> hearder = new HashMap<>();
     //   hearder.put("User-Agent", "okhttp/3.3.1");
        hearder.put("User-Agent", "android");
//        if(isJson) {
//            hearder.put("Content-Type", "application/json");
//        }
      //  hearder.putAll(getToken());
        return hearder;
    }


    /**
     * 获取Token
     *
     * @return
     */
//    private static Map<String, String> getToken() {
//        RequestParameters requestParameters = new RequestParameters();
//        String token = ShawnApplication.getTokenStr();
//        Logger.i("token =" + token);
//        if (token != null) {
//            requestParameters.add("token", token);
//        } else {
//            Logger.e("token 为空：" + token);
//        }
//        return requestParameters.getMapParameters();
//    }
//
//
//    private static Map<String, String> setToken(Map<String, String> params) {
//        String token = ShawnApplication.getTokenStr();
//        if (token != null) {
//            params.put("token", token);
//        }
//        Logger.d("setToken --> token=" + token);
//        return params;
//    }

}
