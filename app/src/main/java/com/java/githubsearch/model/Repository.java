package com.java.githubsearch.model;

/**
 * Created by achau on 27-12-2017.
 */

public class Repository {
    public String full_name = "";
    public String description = "";
    public String html_url = "";
    public String contributors_url = "";

    public int getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(int open_issues) {
        this.open_issues = open_issues;
    }

    public int open_issues= 0;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float score = 0.0f;


    public int stargazers_count = 0;
    public int forks_count = 0;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner owner;

    public int getWatchers_count() {
        return watchers_count;
    }


    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public int watchers_count = 0;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public void setContributors_url(String contributors_url) {
        this.contributors_url = contributors_url;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }
}
