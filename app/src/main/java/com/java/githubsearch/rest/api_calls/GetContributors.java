package com.java.githubsearch.rest.api_calls;

import com.java.githubsearch.model.Contributors;
import com.java.githubsearch.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by achau on 28-12-2017.
 */

public interface GetContributors {

    @GET("repos/{full_name}/contributors")
    Call<List<Contributors>> get(@Path(value = "full_name", encoded = true) String fullName);
}
