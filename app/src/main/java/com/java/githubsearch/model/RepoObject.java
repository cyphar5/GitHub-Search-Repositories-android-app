package com.java.githubsearch.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by achau on 28-12-2017.
 */

public class RepoObject {
    ArrayList<Repository> items;

    public ArrayList<Repository> getItems() {
        return items;
    }

    public void setItems(ArrayList<Repository> items) {
        this.items = items;
    }
}
