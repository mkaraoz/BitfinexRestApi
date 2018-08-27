package com.duvarapps.bitfinexapi.core;

import android.support.annotation.Nullable;

import okhttp3.Headers;
import okhttp3.ResponseBody;

public class ApiResponse
{
    private final okhttp3.Response rawResponse;
    private final @Nullable ResponseBody errorBody;

    public ApiResponse(okhttp3.Response rawResponse, @Nullable ResponseBody errorBody)
    {
        this.rawResponse = rawResponse;
        this.errorBody = errorBody;
    }

    public okhttp3.Response raw()
    {
        return rawResponse;
    }

    /**
     * HTTP status code.
     */
    public int code()
    {
        return rawResponse.code();
    }

    /**
     * HTTP status message or null if unknown.
     */
    public String message()
    {
        return rawResponse.message();
    }

    /**
     * HTTP headers.
     */
    public Headers headers()
    {
        return rawResponse.headers();
    }

    /**
     * Returns true if {@link #code()} is in the range [200..300).
     */
    public boolean isSuccessful()
    {
        return rawResponse.isSuccessful();
    }

    /**
     * The raw response body of an {@linkplain #isSuccessful() unsuccessful} response.
     */
    public @Nullable
    ResponseBody errorBody()
    {
        return errorBody;
    }

    @Override
    public String toString()
    {
        return rawResponse.toString();
    }
}

