package com.terapanth.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrekshadhyanResponce {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<Prekshadhyan> mPrekshadhyan;
    @SerializedName("page_token")
    @Expose
    private int page_token;

    public class Prekshadhyan {

        @SerializedName("prekshadyan_id")
        @Expose
        private String prekshadyan_id;
        @SerializedName("prekshadyan_title")
        @Expose
        private String prekshadyan_title;
        @SerializedName("prekshadyan_body")
        @Expose
        private String prekshadyan_body;
        @SerializedName("prekshadyan_date")
        @Expose
        private String prekshadyan_date;
        @SerializedName("show_fornt")
        @Expose
        private String show_fornt;
        @SerializedName("prekshadyan_image")
        @Expose
        private String prekshadyan_image;
        @SerializedName("is_deleted")
        @Expose
        private String is_deleted;

        public String getPrekshadyan_id() {
            return prekshadyan_id;
        }

        public void setPrekshadyan_id(String prekshadyan_id) {
            this.prekshadyan_id = prekshadyan_id;
        }

        public String getPrekshadyan_title() {
            return prekshadyan_title;
        }

        public void setPrekshadyan_title(String prekshadyan_title) {
            this.prekshadyan_title = prekshadyan_title;
        }

        public String getPrekshadyan_body() {
            return prekshadyan_body;
        }

        public void setPrekshadyan_body(String prekshadyan_body) {
            this.prekshadyan_body = prekshadyan_body;
        }

        public String getPrekshadyan_date() {
            return prekshadyan_date;
        }

        public void setPrekshadyan_date(String prekshadyan_date) {
            this.prekshadyan_date = prekshadyan_date;
        }

        public String getShow_fornt() {
            return show_fornt;
        }

        public void setShow_fornt(String show_fornt) {
            this.show_fornt = show_fornt;
        }

        public String getPrekshadyan_image() {
            return prekshadyan_image;
        }

        public void setPrekshadyan_image(String prekshadyan_image) {
            this.prekshadyan_image = prekshadyan_image;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Prekshadhyan> getmPrekshadhyan() {
        return mPrekshadhyan;
    }

    public void setmPrekshadhyan(ArrayList<Prekshadhyan> mPrekshadhyan) {
        this.mPrekshadhyan = mPrekshadhyan;
    }

    public int getPage_token() {
        return page_token;
    }

    public void setPage_token(int page_token) {
        this.page_token = page_token;
    }
}
