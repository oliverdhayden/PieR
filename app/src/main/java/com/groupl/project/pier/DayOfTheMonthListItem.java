package com.groupl.project.pier;



public class DayOfTheMonthListItem {
    private String brandName;
    private String imgUrl;
    private String dayOfTheMonth;
    private String month;
    private String AmountOfTheTransaction;

    public DayOfTheMonthListItem(String imgUrl, String brandName,String AmountOfTheTransaction, String dayOfTheMonth, String month) {
        this.imgUrl = imgUrl;
        this.brandName = brandName;
        this.dayOfTheMonth = dayOfTheMonth;
        this.AmountOfTheTransaction = AmountOfTheTransaction;
        this.month = month;
    }

    public String getAmountOfTheTransaction() {
        return AmountOfTheTransaction;
    }

    public void setAmountOfTheTransaction(String amountOfTheTransaction) {
        AmountOfTheTransaction = amountOfTheTransaction;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getDayOfTheMonth() {
        return dayOfTheMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setDayOfTheMonth(String dayOfTheMonth) {
        this.dayOfTheMonth = dayOfTheMonth;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
