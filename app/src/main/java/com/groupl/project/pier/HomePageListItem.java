package com.groupl.project.pier;



public class HomePageListItem {

    private String spendingType;
    private String spendingMoney;
    private String imgURL;

    public HomePageListItem(String spendingType, String spendingMoney, String imgURL) {
        this.spendingType = spendingType;
        this.spendingMoney = spendingMoney;
        this.imgURL = imgURL;
    }


    public String getSpendingType() {
        return spendingType;
    }

    public String getSpendingMoney() {
        return spendingMoney;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setSpendingType(String spendingType) {
        this.spendingType = spendingType;
    }

    public void setSpendingMoney(String spendingMoney) {
        this.spendingMoney = spendingMoney;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
