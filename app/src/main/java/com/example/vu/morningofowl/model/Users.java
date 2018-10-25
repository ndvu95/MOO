package com.example.vu.morningofowl.model;

public class Users {
    public String HoTen;
    public String Email;
    public String SDT;
    public String Image;

    public Users() {
    }

    public Users(String hoTen, String email, String SDT, String image) {
        HoTen = hoTen;
        Email = email;
        this.SDT = SDT;
        Image = image;
    }
}
