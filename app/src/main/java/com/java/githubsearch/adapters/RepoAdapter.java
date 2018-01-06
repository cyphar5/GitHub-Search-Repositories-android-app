package com.java.githubsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.githubsearch.R;
import com.java.githubsearch.activities.ContributorActivity;
import com.java.githubsearch.activities.RepoWebViewActivity;
import com.java.githubsearch.adapters.holders.RepoHolder;
import com.java.githubsearch.model.Repository;
import com.java.githubsearch.utility.Constants;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by achau on 29-12-2017.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoHolder> {

    List<Repository> list = new LinkedList<Repository>();
    Context context;
    public int count = 1;

    public RepoAdapter(Context context, List<Repository> list){
        this.context = context ;
        this.list = list;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_url_adapter,parent,false);
       return  new RepoHolder(v);

    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
    final  Repository repo = list.get(position);
      holder.repoHtml.setText(repo.getHtml_url());
      if(repo.getDescription()!=null)
       holder.repoDescription.setText(repo.getDescription());
      holder.repoCounter.setText((position+1) +" .");
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String url = repo.getHtml_url();
              Intent intent = new Intent(context, RepoWebViewActivity.class);
              intent.putExtra(Constants.REPO_HTML_URL , url);
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
