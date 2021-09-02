package com.example.app_control.Merch;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class MerchConstructor implements Serializable
{

    @Exclude
    private String key;
    private String name;
    private String position;
    //private String price;
    private String image;
    public MerchConstructor(){}
    public MerchConstructor(String name, String position, String image)
    {
        this.name = name;
        this.position = position;
        //this.price = price;
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    /*public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }*/
    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
}