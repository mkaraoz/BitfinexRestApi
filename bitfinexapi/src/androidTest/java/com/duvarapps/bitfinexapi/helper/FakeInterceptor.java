package com.duvarapps.bitfinexapi.helper;

import android.support.annotation.NonNull;

import com.crypto.dealer.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FakeInterceptor implements Interceptor
{
    private final String mResponseString;

    public FakeInterceptor(String fakeResponse)
    {
        mResponseString = fakeResponse;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException
    {
        Response response;

        if(BuildConfig.DEBUG)
        {
            response = new Response.Builder()
                    .code(200)
                    .message(mResponseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), mResponseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();

            return response;
        }

        return chain.proceed(chain.request());
    }
}
