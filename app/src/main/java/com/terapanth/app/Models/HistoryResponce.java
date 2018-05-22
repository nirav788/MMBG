package com.terapanth.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Developer on 18-03-2018.
 */

public class HistoryResponce {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("page_token")
    @Expose
    private int page_token;
    @SerializedName("data")
    @Expose
    public ArrayList<About> about;


    public class About {
        @SerializedName("history_id")
        @Expose
        private int about_id;
        @SerializedName("history_title")
        @Expose
        private String about_title;
        @SerializedName("history_body")
        @Expose
        private String about_body;
        @SerializedName("is_deleted")
        @Expose
        private String is_deleted;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("modified")
        @Expose
        private String modified;

        public int getAbout_id() {
            return about_id;
        }

        public void setAbout_id(int about_id) {
            this.about_id = about_id;
        }

        public String getAbout_title() {
            return about_title;
        }

        public void setAbout_title(String about_title) {
            this.about_title = about_title;
        }

        public String getAbout_body() {
            return about_body;
        }

        public void setAbout_body(String about_body) {
            this.about_body = about_body;
        }

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
            this.is_deleted = is_deleted;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
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

    public ArrayList<About> getAbout() {
        return about;
    }

    public void setAbout(ArrayList<About> about) {
        this.about = about;
    }
}
