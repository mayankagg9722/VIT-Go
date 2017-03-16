package com.example.mayankaggarwal.viteventsapp.rest;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mayankaggarwal on 12/02/17.
 */

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(final Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        String[] BASE_URLS = {"https://vitmantra.feedveed.com","https://customserver2.feedveed.com"};

        Random rn = new Random();
        int i = rn.nextInt(BASE_URLS.length);

        String url=BASE_URLS[i];

//        Log.d("tagg","random:"+url);

//        if (retrofit == null) {
            OkHttpClient ok = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(ok.newBuilder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS).build())
                    .build();
//        }

        return retrofit;
    }
}
