package com.java.githubsearch.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.githubsearch.R;
import com.java.githubsearch.adapters.RepositoryAdapter;
import com.java.githubsearch.model.Repository;

import org.w3c.dom.Text;

/**
 * Created by achau on 28-12-2017.
 */

public class RepositoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView userName,description, starsCount , score , forksCount ;
    public ImageView userImage;


    public RepositoryHolder(View view){
        super(view);

        userName  = view.findViewById(R.id.sa_user_name);
        description = view.findViewById(R.id.sa_description);
        starsCount = view.findViewById(R.id.sa_stars_count);
        score = view.findViewById(R.id.sa_score);
        forksCount = view.findViewById(R.id.sa_forks_count);
        userImage = view.findViewById(R.id.sa_user_image);


    }

    @Override
    public void onClick(View v) {

    }
}
