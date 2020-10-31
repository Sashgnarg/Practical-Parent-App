package com.cmpt276.iteration1practicalparent.ConfigureChildren;

//class contains image resource and two texts. These will later on be used to display each item.
public class ConfigureChildrenItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ConfigureChildrenItem(int imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
