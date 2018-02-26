package com.groupl.project.pier;

/**
 * Created by alexandra on 14/02/2018.
 */

public class DayOfTheMonthListItem {
    private String brandName;
    private String tagName;
    private String dayOfTheMonth;
    private String month;
    private String AmountOfTheTransaction;

    public DayOfTheMonthListItem(String tagName, String brandName,String AmountOfTheTransaction, String dayOfTheMonth, String month) {
        this.tagName = tagName;
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

    public String getTagName() {
        return tagName;
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

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
