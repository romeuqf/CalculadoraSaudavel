package com.example.calculadorasaudavel;


class MainItem {

    private final int imgId;
    private final int textId;
    private final int colorValue;
    private final int id;


    MainItem(int id, int imgId, int textId, int colorValue) {
        this.id = id;
        this.imgId = imgId;
        this.textId = textId;
        this.colorValue = colorValue;
    }

    int getImgId() {
        return imgId;
    }

    int getTextId() {
        return textId;
    }

    int getColorValue() {
        return colorValue;
    }

    int getId() {
        return id;
    }
}
