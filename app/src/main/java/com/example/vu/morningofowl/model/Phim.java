package com.example.vu.morningofowl.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Phim implements Serializable{
        private String tenPhim;
        private String linkPhim;
        private String posterPhim;
        private String theloaiPhim;
        private String dienvienPhim;
        private String soluotXem;
        private float ratingStar;

    public Phim(String tenPhim, String linkPhim, String posterPhim, String theloaiPhim, String dienvienPhim, String soluotXem, float ratingStar) {
        this.tenPhim = tenPhim;
        this.linkPhim = linkPhim;
        this.posterPhim = posterPhim;
        this.theloaiPhim = theloaiPhim;
        this.dienvienPhim = dienvienPhim;
        this.soluotXem = soluotXem;
        this.ratingStar = ratingStar;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getLinkPhim() {
        return linkPhim;
    }

    public void setLinkPhim(String linkPhim) {
        this.linkPhim = linkPhim;
    }

    public String getPosterPhim() {
        return posterPhim;
    }

    public void setPosterPhim(String posterPhim) {
        this.posterPhim = posterPhim;
    }

    public String getTheloaiPhim() {
        return theloaiPhim;
    }

    public void setTheloaiPhim(String theloaiPhim) {
        this.theloaiPhim = theloaiPhim;
    }

    public String getDienvienPhim() {
        return dienvienPhim;
    }

    public void setDienvienPhim(String dienvienPhim) {
        this.dienvienPhim = dienvienPhim;
    }

    public String getSoluotXem() {
        return soluotXem;
    }

    public void setSoluotXem(String soluotXem) {
        this.soluotXem = soluotXem;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }
}
