package com.java.githubsearch.rest.api_calls;

import com.java.githubsearch.model.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by achau on 28-12-2017.
 */

public interface GetRepoInfo {


    @GET("repos/{full_name}")
    Call<Repository> get(@Path(value = "full_name", encoded = true) String fullName);

}
