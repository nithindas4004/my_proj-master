package com.example.alzon.final_ser.modelclass;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/23/2017.
 */
@IgnoreExtraProperties
public class Upload{

    public String name;
    public String url;
    public String songname;
    public String albumurl;

    public String audiourl;
    public String bannerurl;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setAlbumurl(String albumurl) {
        this.albumurl = albumurl;
    }

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url,String songname,String bannerurl,String audiourl) {
        this.name = name;
        this.albumurl=audiourl;
        this.url= url;
        this.songname=songname;
        this.bannerurl=bannerurl;
        this.audiourl=audiourl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    public String getAlbumurl() {
        return albumurl;
    }
    public String getSongname() {
        return songname;
    }
    public String getBannerurl() {
        return bannerurl;
    }
    public String getAudiourl() {
        return audiourl;
    }


}
