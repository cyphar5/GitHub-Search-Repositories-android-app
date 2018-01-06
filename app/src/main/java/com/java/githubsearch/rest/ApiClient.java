package com.java.githubsearch.rest;

import com.java.githubsearch.utility.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by achau on 27-12-2017.
 */

public class ApiClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient(){

        if(retrofit ==null){
                retrofit = new Retrofit.Builder().
                        baseUrl(Constants.BASE_URL).
                        addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;

    }
}
