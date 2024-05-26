package com.example.medicinefinder.Model;

import com.google.gson.annotations.SerializedName;

public class Rating_review {

    @SerializedName("id")
    private String id;

    @SerializedName("rating_star")
    private String rating_star;

    @SerializedName("review")
    private String review;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating_star() {
        return rating_star;
    }

    public void setRating_star(String rating_star) {
        this.rating_star = rating_star;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
