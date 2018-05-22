package com.terapanth.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Developer on 18-03-2018.
 */

public class RulesResponce {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<Rules> mRules;
    @SerializedName("page_token")
    @Expose
    private int page_token;


    public class Rules {

        @SerializedName("rules_id")
        @Expose
        private String rules_id;
        @SerializedName("rules_title")
        @Expose
        private String rules_title;
        @SerializedName("rules_name")
        @Expose
        private String rules_name;

        public String getRules_id() {
            return rules_id;
        }

        public void setRules_id(String rules_id) {
            this.rules_id = rules_id;
        }

        public String getRules_title() {
            return rules_title;
        }

        public void setRules_title(String rules_title) {
            this.rules_title = rules_title;
        }

        public String getRules_name() {
            return rules_name;
        }

        public void setRules_name(String rules_name) {
            this.rules_name = rules_name;
        }
    }

    public int getPage_token() {
        return page_token;
    }

    public void setPage_token(int page_token) {
        this.page_token = page_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Rules> getmRules() {
        return mRules;
    }

    public void setmRules(ArrayList<Rules> mRules) {
        this.mRules = mRules;
    }
}
