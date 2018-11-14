package com.wess58.artissans.models;

public class News {

    private String mAccessionYear;
    private String mTechnique;
    private String mCopyright;
    private String mUrl;
    private String mImage;

    public News(String accessionyear, String technique, String copyright, String url, String image) {

            this.mAccessionYear = accessionyear;
            this.mTechnique = technique;
            this.mCopyright = copyright;
            this.mUrl = url;
            this.mImage = image;

    }

    public String getmAccessionYear() {
        return mAccessionYear;
    }

    public String getmTechnique() {
        return mTechnique;
    }

    public String getmCopyright() {
        return mCopyright;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmImage() {
        return mImage;
    }
}
