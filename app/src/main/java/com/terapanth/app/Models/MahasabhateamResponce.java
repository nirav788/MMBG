package com.terapanth.app.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MahasabhateamResponce {

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
    ArrayList<Mahasabhateam> mMahasabhateams;

    public class Mahasabhateam {

        @SerializedName("team_id")
        @Expose
        private String team_id;
        @SerializedName("full_name")
        @Expose
        private String full_name;
        @SerializedName("contact_number")
        @Expose
        private String contact_number;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("anniversary")
        @Expose
        private String anniversary;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("profile_pic")
        @Expose
        private String profile_pic;

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getAnniversary() {
            return anniversary;
        }

        public void setAnniversary(String anniversary) {
            this.anniversary = anniversary;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
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

    public ArrayList<Mahasabhateam> getmMahasabhateams() {
        return mMahasabhateams;
    }

    public void setmMahasabhateams(ArrayList<Mahasabhateam> mMahasabhateams) {
        this.mMahasabhateams = mMahasabhateams;
    }
}
