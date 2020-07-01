package com.aliTao.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aliTao.model.BaseResult;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Logger;

import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 */

public abstract class JsonCallback<T> extends Callback<T> {

    private long expireTime = -1L;        //缓存时间
    private Class<T> entityClass;

    private Context context;

    public JsonCallback() {
        this(-1L);
    }


    /**
     * 需要自动处理异常时需要使用该构造函数
     *
     * @param context
     */
    public JsonCallback(Context context) {
        this(-1L);
        this.context = context;
    }


    public JsonCallback(long expireTime) {
        this.expireTime = expireTime;
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public void onError(Call call, Exception e, int id) {

        onFail(call, e, id);
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
//        Logger.d("↓↓↓↓↓↓↓↓↓↓NetworkResponse↓↓↓↓↓↓↓↓↓");
//        Logger.d("↑↑↑↑↑↑↑↑↑↑NetworkResponse↑↑↑↑↑↑↑↑↑↑");

        if (entityClass == String.class) {
            return (T) string;
        }

        T entityC = null;
        try {
            entityC = GsonProvider.getInstance().getGson().fromJson(string, entityClass);

            return entityC;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return entityC;
    }


    @Override
    public void onResponse(T response, int id) {

      preException(response, id);
    }


    /**
     * 异常请求预处理
     *
     * @param response
     * @param id
     */
    private void preException(T response, int id) {
        if (response instanceof BaseResult) {
            BaseResult baseResult = (BaseResult) response;
            if (baseResult .getCode() == 0 ){
                onSuccess(response,id);
            } else {
                onException(response,id);
            }
        } else {
            onSuccess(response,id);
        }

    }

    /**
     * 请求数据失败，一般为网络异常
     *
     * @param call
     * @param e
     * @param id
     */
    public abstract void onFail(Call call, Exception e, int id);

    /**
     * 返回数据异常
     *
     * @param response
     * @param id
     */
    public abstract void onException(T response, int id);

    /**
     * 成功
     *
     * @param response
     * @param id
     */
    public abstract void onSuccess(T response, int id);

    /**
     * 获取缓存的key
     *
     * @param request
     * @return
     */
    private String getCacheKey(Request request) {
        String url = request.url().toString();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null) {

            }
        }
        return url;
    }
}
