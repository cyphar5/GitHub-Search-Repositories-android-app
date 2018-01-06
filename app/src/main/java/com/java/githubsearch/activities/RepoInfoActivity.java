package com.java.githubsearch.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.java.githubsearch.R;
import com.java.githubsearch.adapters.ContributorAdapter;
import com.java.githubsearch.adapters.RepositoryAdapter;
import com.java.githubsearch.model.Contributors;
import com.java.githubsearch.model.RepoObject;
import com.java.githubsearch.model.Repository;
import com.java.githubsearch.rest.ApiClient;
import com.java.githubsearch.rest.api_calls.GetContributors;
import com.java.githubsearch.rest.api_calls.GetDefaultRepo;
import com.java.githubsearch.rest.api_calls.GetRepoInfo;
import com.java.githubsearch.rest.callback.GetContributorsCallback;
import com.java.githubsearch.utility.Constants;
import com.java.githubsearch.utility.Util;

import java.util.ArrayList;
import java.util.List;

public class RepoInfoActivity extends AppCompatActivity implements Callback<Repository>,GetContributorsCallback.GetContributorsCallbackListener  {

    private ImageView repoImage;
    private TextView  repoName,repoWatchers,repoOpenIssues,repoDescription,repoContributors;
    private RecyclerView recyclerView;
    private String fullname;
    private ContributorAdapter contributorAdapter;
    private ProgressBar progressBar;
    private AppBarLayout actionBar;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_info);

        repoName = findViewById(R.id.ria_user_name);
        repoDescription = findViewById(R.id.ria_description);
        repoOpenIssues = findViewById(R.id.ria_open_issues);
        repoDescription = findViewById(R.id.ria_description);
        repoContributors = findViewById(R.id.ria_contributors);
        repoWatchers = findViewById(R.id.ria_watchers);
        repoImage = findViewById(R.id.ria_image);
        recyclerView = findViewById(R.id.ria_recylcerview);
        progressBar = findViewById(R.id.ac_progress_dialog);
        actionBar = findViewById(R.id.app_bar_layout);
        back = findViewById(R.id.ari_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        actionBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(verticalOffset == 0){
//                    actionBar.setBackgroundColor(getResources().getColor(R.color.transparent));
//                }
//
//                else{
//                    actionBar.setBackgroundColor(getResources().getColor(R.color.pink));
//                }
//            }
//        });


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        fullname = intent.getStringExtra(Constants.FULL_NAME);

        if(Util.isNetWorkAvailable(this)) {
            fetchRepoInfo(fullname);
            fetchContributors(fullname);
        }
        else{
            Toast.makeText(this,"Internet not available" ,Toast.LENGTH_LONG ).show();
            progressBar.setVisibility(View.GONE);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onResponse(Call<Repository> call, Response<Repository> response) {

        Log.v("RESPONSE", response.toString());

        if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
            Repository repo = response.body();
            if (repo!=null) {
                repoName.setText(repo.getFull_name());
                repoDescription.setText(repo.getHtml_url());
                repoWatchers.setText(repo.getWatchers_count()+"");
                repoOpenIssues.setText(repo.getOpen_issues() + "");
                Glide.with(this)
                        .load(repo.getOwner().avatar_url)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(repoImage);
            } else
                Toast.makeText(this,getString(R.string.error_fetcching),Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onFailure(Call<Repository> call, Throwable t) {

    }

    public void fetchRepoInfo(String fullname){
        GetRepoInfo apiService =  ApiClient.getClient().create(GetRepoInfo.class);
        Call<Repository> call = apiService.get(fullname);
        call.enqueue(RepoInfoActivity.this);
    }

    public void fetchContributors(String name){
        GetContributors apiService =  ApiClient.getClient().create(GetContributors.class);
        Call<List<Contributors>> call = apiService.get(name);
        call.enqueue(new GetContributorsCallback(this));
    }

    @Override
    public void onPostedSuccess(List<Contributors> contributorsList) {

        if (contributorsList != null && contributorsList.size() > 0) {
            repoContributors.setText(contributorsList.size()+"");
            contributorAdapter = new ContributorAdapter(this, contributorsList);
            recyclerView.setAdapter(contributorAdapter);

            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPostFailure() {
        Toast.makeText(this,getString(R.string.error_fetcching),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
