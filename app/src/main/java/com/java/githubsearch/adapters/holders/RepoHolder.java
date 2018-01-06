package com.java.githubsearch.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.java.githubsearch.R;

/**
 * Created by achau on 29-12-2017.
 */

public class RepoHolder extends RecyclerView.ViewHolder {

    public TextView repoDescription , repoHtml , repoCounter;


    public RepoHolder(View v){
        super(v);

        repoDescription = v.findViewById(R.id.rua_name);
        repoCounter = v.findViewById(R.id.rua_count);
        repoHtml = v.findViewById(R.id.rua_url);

    }
}
