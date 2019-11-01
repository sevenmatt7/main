package com.algosenpai.app.logic.models;

import java.util.ArrayList;

public class ReviewTracingListModel {

    private ArrayList<String> reviewMethodList;

    public ReviewTracingListModel() {
        reviewMethodList = new ArrayList<>();
    }

    public void addReviewTracingModel(String reviewStep) {
        this.reviewMethodList.add(reviewStep);
    }

    @Override
    public String toString() {
        int counter = 1;
        StringBuilder totalString = new StringBuilder();
        for (String x: reviewMethodList) {
            totalString.append(counter++).append(". ").append(x).append("\n");
        }
        return totalString.toString();
    }

}
