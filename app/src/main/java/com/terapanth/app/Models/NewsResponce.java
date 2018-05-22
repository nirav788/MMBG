package com.terapanth.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsResponce {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("page_token")
    @Expose
    private int page_token;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    ArrayList<News> mNews;

    public class News {

        @SerializedName("news_id")
        @Expose
        private String news_id;
        @SerializedName("news_body")
        @Expose
        private String news_body;
        @SerializedName("news_date")
        @Expose
        private String news_date;
        @SerializedName("show_fornt")
        @Expose
        private String show_fornt;
        @SerializedName("news_image")
        @Expose
        private String news_image;
        @SerializedName("is_deleted")
        @Expose
        private String is_deleted;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getNews_body() {
            return news_body;
        }

        public void setNews_body(String news_body) {
            this.news_body = news_body;
        }

        public String getNews_date() {
            return news_date;
        }

        public void setNews_date(String news_date) {
            this.news_date = news_date;
        }

        public String getShow_fornt() {
            return show_fornt;
        }

        public void setShow_fornt(String show_fornt) {
            this.show_fornt = show_fornt;
        }

        public String getNews_image() {
            return news_image;
        }

        public void setNews_image(String news_image) {
            this.news_image = news_image;
        }

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
            this.is_deleted = is_deleted;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPage_token() {
        return page_token;
    }

    public void setPage_token(int page_token) {
        this.page_token = page_token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<News> getmNews() {
        return mNews;
    }

    public void setmNews(ArrayList<News> mNews) {
        this.mNews = mNews;
    }
}
