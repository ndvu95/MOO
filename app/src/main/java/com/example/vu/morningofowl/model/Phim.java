package com.example.vu.morningofowl.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Phim implements Serializable{
        private String tenPhim;
        private String linkPhim;
        private String posterPhim;
        private String theloaiPhim;
        private String motaPhim;
        private String dienvienPhim;
        private int soluotXem;


    public Phim(String tenPhim, String linkPhim, String posterPhim, String theloaiPhim, String motaPhim, String dienvienPhim, int soluotXem) {
        this.tenPhim = tenPhim;
        this.linkPhim = linkPhim;
        this.posterPhim = posterPhim;
        this.theloaiPhim = theloaiPhim;
        this.motaPhim = motaPhim;
        this.dienvienPhim = dienvienPhim;
        this.soluotXem = soluotXem;
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

    public String getMotaPhim() {
        return motaPhim;
    }

    public void setMotaPhim(String motaPhim) {
        this.motaPhim = motaPhim;
    }

    public String getDienvienPhim() {
        return dienvienPhim;
    }

    public void setDienvienPhim(String dienvienPhim) {
        this.dienvienPhim = dienvienPhim;
    }

    public int getSoluotXem() {
        return soluotXem;
    }

    public void setSoluotXem(int soluotXem) {
        this.soluotXem = soluotXem;
    }
}
