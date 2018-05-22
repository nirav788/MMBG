package com.terapanth.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PravachanResponce {

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
    ArrayList<Pravachan> mPravachans;

    public class Pravachan {

        @SerializedName("pravachan_id")
        @Expose
        private String pravachan_id;
        @SerializedName("pravachan_title")
        @Expose
        private String pravachan_title;
        @SerializedName("pravachan_body")
        @Expose
        private String pravachan_body;
        @SerializedName("show_fornt")
        @Expose
        private String show_fornt;
        @SerializedName("pravachan_image")
        @Expose
        private String pravachan_image;
        @SerializedName("is_deleted")
        @Expose
        private String is_deleted;

        public String getPravachan_id() {
            return pravachan_id;
        }

        public void setPravachan_id(String pravachan_id) {
            this.pravachan_id = pravachan_id;
        }

        public String getPravachan_title() {
            return pravachan_title;
        }

        public void setPravachan_title(String pravachan_title) {
            this.pravachan_title = pravachan_title;
        }

        public String getPravachan_body() {
            return pravachan_body;
        }

        public void setPravachan_body(String pravachan_body) {
            this.pravachan_body = pravachan_body;
        }

        public String getShow_fornt() {
            return show_fornt;
        }

        public void setShow_fornt(String show_fornt) {
            this.show_fornt = show_fornt;
        }

        public String getPravachan_image() {
            return pravachan_image;
        }

        public void setPravachan_image(String pravachan_image) {
            this.pravachan_image = pravachan_image;
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

    public ArrayList<Pravachan> getmPravachans() {
        return mPravachans;
    }

    public void setmPravachans(ArrayList<Pravachan> mPravachans) {
        this.mPravachans = mPravachans;
    }
}
