package com.java.githubsearch.rest.callback;

import com.java.githubsearch.model.Contributors;
import com.java.githubsearch.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by achau on 29-12-2017.
 */

public class GetReposCallback implements Callback<List<Repository>> {

    private GetReposCallbackListener listener;

    public interface GetReposCallbackListener {
        void onPostedSuccess(List<Repository> repos);
        void onPostFailure();
    }

    public GetReposCallback(GetReposCallbackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {

        if(response.isSuccessful() && (response.code() == 200 || response.code() == 201)){
            listener.onPostedSuccess(response.body());
        }
        else{
            listener.onPostFailure();
        }
    }

    @Override
    public void onFailure(Call<List<Repository>> call, Throwable t) {

    }
}
