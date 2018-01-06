package com.java.githubsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.java.githubsearch.R;
import com.java.githubsearch.activities.ContributorActivity;
import com.java.githubsearch.activities.RepoInfoActivity;
import com.java.githubsearch.adapters.holders.ContributorViewHolder;
import com.java.githubsearch.adapters.holders.RepositoryHolder;
import com.java.githubsearch.model.Contributors;
import com.java.githubsearch.model.Repository;
import com.java.githubsearch.utility.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by achau on 28-12-2017.
 */

public class ContributorAdapter extends RecyclerView.Adapter<ContributorViewHolder> {

    List<Contributors> cont = new LinkedList<>();
    Context context;

    public ContributorAdapter(Context context,List<Contributors> cont){
        this.cont = cont;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {
        final Contributors contributors = cont.get(position);
        if(contributors!=null){
            Glide.with(context)
                    .load(contributors.getAvatar_url())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.imageView);
            holder.loginName.setText(contributors.getLogin());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = contributors.getRepos_url();
                Intent intent = new Intent(context,ContributorActivity.class);
                intent.putExtra(Constants.GET_REPOS , url);
                intent.putExtra(Constants.GET_REPO_URL,contributors.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributor_adapter , parent , false);
        return new ContributorViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return cont.size();
    }


}
