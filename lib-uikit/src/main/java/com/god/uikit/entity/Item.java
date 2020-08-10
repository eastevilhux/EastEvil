package com.god.uikit.entity;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    private @DrawableRes int imageResource;
    private @StringRes int itemResource;
    private String imageUrl;
    private String itemText;
    private int type;

    public Item(@DrawableRes int imageResource,@StringRes int itemResource){
        this.imageResource = imageResource;
        this.itemResource = itemResource;
    }

    public Item(String imageUrl,String itemText){
        this.imageUrl = imageUrl;
        this.itemText = itemText;
    }

    public Item(@DrawableRes int imageResource,String itemText){
        this.imageResource = imageResource;
        this.itemText = itemText;
    }

    public Item(String imageUrl,@StringRes int itemResource){
        this.imageUrl = imageUrl;
        this.itemResource = itemResource;
    }

    public Item(@DrawableRes int imageResource,@StringRes int itemResource,int type){
        this.imageResource = imageResource;
        this.itemResource = itemResource;
        this.type = type;
    }

    public Item(String imageUrl,String itemText,int type){
        this.imageUrl = imageUrl;
        this.itemText = itemText;
        this.type = type;
    }

    public Item(@DrawableRes int imageResource,String itemText,int type){
        this.imageResource = imageResource;
        this.itemText = itemText;
        this.type = type;
    }

    public Item(String imageUrl,@StringRes int itemResource,int type){
        this.imageUrl = imageUrl;
        this.itemResource = itemResource;
        this.type = type;
    }


    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getItemResource() {
        return itemResource;
    }

    public void setItemResource(int itemResource) {
        this.itemResource = itemResource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return imageResource == item.imageResource &&
                itemResource == item.itemResource &&
                Objects.equals(imageUrl, item.imageUrl) &&
                Objects.equals(itemText, item.itemText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageResource, itemResource, imageUrl, itemText);
    }

}
