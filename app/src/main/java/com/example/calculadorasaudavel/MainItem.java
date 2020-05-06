package com.example.calculadorasaudavel;

class MainItem {

    private final int imgId;
    private final int textId;
    private final int colorValue;


    MainItem(int imgId, int textId, int colorValue) {
        this.imgId = imgId;
        this.textId = textId;
        this.colorValue = colorValue;
    }

    public int getImgId() {
        return imgId;
    }

    public int getTextId() {
        return textId;
    }

    public int getColorValue() {
        return colorValue;
    }
}
