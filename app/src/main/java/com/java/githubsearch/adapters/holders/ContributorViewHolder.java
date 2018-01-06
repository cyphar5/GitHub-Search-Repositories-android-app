package com.java.githubsearch.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.githubsearch.R;

/**
 * Created by achau on 28-12-2017.
 */

public class ContributorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView loginName;
    public ImageView imageView;


    public ContributorViewHolder(View view){
        super(view);

        loginName = view.findViewById(R.id.ca_name);
        imageView = view.findViewById(R.id.ca_image);
    }

    @Override
    public void onClick(View v) {

    }
}
