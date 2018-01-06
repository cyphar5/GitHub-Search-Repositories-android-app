package com.java.githubsearch.rest.api_calls;

import com.java.githubsearch.model.Contributors;
import com.java.githubsearch.model.RepoUser;
import com.java.githubsearch.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by achau on 29-12-2017.
 */

public interface GetContributorInfo {

    @GET
    Call<RepoUser> getContributorInfo(@Url String url);
}
