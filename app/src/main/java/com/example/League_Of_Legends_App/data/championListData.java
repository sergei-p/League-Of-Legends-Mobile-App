package com.example.League_Of_Legends_App.data;
// data class to store name of champion as well as img url for border icon
public class championListData {
    private String name;
    private String img_url;
    private String redirect_url;

    public championListData(String name, String img_url, String redirect_url) {
        this.name = name;
        this.img_url = img_url;
        this.redirect_url = redirect_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

}