package com.java.githubsearch.rest.api_calls;

import com.java.githubsearch.model.RepoObject;
import com.java.githubsearch.model.Repository;
import com.java.githubsearch.utility.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by achau on 27-12-2017.
 */

public interface GetDefaultRepo {

    @GET("search/repositories")
    Call<RepoObject> getRepository( @QueryMap Map<String, String> options);
}
