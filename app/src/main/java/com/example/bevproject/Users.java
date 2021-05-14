package com.example.bevproject;

import java.io.Serializable;

public class Users implements Serializable {
    private int id;
    private String email, name, password, bio;
    private byte[] img;

    public void setId(int id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public void setName(String name) { this.name = name; }

    public void setPassword(String password) { this.password = password; }

    public void setBio(String bio) { this.bio = bio; }

    public void setImg(byte[] img) { this.img = img; }

    public int getId() { return id; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getPassword() { return password; }

    public String getBio() { return bio; }

    public byte[] getImg() { return img; }
}
