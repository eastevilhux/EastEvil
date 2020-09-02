package com.god.uikit.entity;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.io.Serializable;
import java.util.Objects;

public class Item implements Serializable {
    /**
     * 资源类型
     */
    public static final int TEXT_TYPE_RESOURCE = 0x01;
    /**
     * 字符串类型
     */
    public static final int TEXT_TYPE_STRING = 0x02;

    /**
     * 图片资源类型
     */
    public static final int IMAGE_TYPE_RESOURCE = 0x01;

    /**
     * url类型
     */
    public static final int IMAGE_TYPE_URL = 0x02;

    private int id;
    private @DrawableRes int imageResource;
    private @StringRes int itemResource;
    private String imageUrl;
    private String itemText;
    private int type;
    private boolean select;
    private int textType;
    private int imageType;
    private boolean haveIcon;
    private int tag;

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

    public Item(Bulder bulder){
        this.imageResource = bulder.imageResource;
        this.itemResource = bulder.itemResource;
        this.imageUrl = bulder.imageUrl;
        this.itemText = bulder.itemText;
        this.select = bulder.select;
        this.textType = bulder.textType;
        this.imageType = bulder.type;
        this.haveIcon = bulder.haveIcon;
        this.id = bulder.id;
        this.tag = bulder.tag;
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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isHaveIcon() {
        return haveIcon;
    }

    public void setHaveIcon(boolean haveIcon) {
        this.haveIcon = haveIcon;
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

    public static class Bulder{
        private @DrawableRes int imageResource;
        private @StringRes int itemResource;
        private String imageUrl;
        private String itemText;
        private int type;
        private boolean select;
        private int textType;
        private int imageType;
        private boolean haveIcon;
        private int id;
        private int tag;

        public Bulder text(@StringRes int textRes){
            this.itemResource = textRes;
            this.textType = TEXT_TYPE_RESOURCE;
            return this;
        }

        public Bulder text(String itemText){
            this.itemText = itemText;
            this.textType = TEXT_TYPE_STRING;
            return this;
        }

        public Bulder image(@DrawableRes int imageResource){
            this.imageResource = imageResource;
            this.imageType = IMAGE_TYPE_RESOURCE;
            return this;
        }

        public Bulder image(String imageUrl){
            this.imageUrl = imageUrl;
            this.imageType = IMAGE_TYPE_URL;
            return this;
        }

        public Bulder select(boolean select){
            this.select = select;
            return this;
        }

        public Bulder id(int id){
            this.id = id;
            return this;
        }

        public Bulder tag(int tag){
            this.tag = tag;
            return this;
        }

        public Bulder buildItem(int id,int tag){
            this.id = id;
            this.tag = tag;
            return this;
        }

        public Bulder haveIcon(boolean haveIcon){
            this.haveIcon = haveIcon;
            return this;
        }

        public Item bulder(){
            return new Item(this);
        }
    }

}
