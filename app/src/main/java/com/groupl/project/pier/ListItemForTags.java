package com.groupl.project.pier;



public class ListItemForTags {
    private String transactionAmount;
    private String transactionBrand;
    private String transactionDate;
    private String tagMessage;

    public ListItemForTags(String transactionAmount, String transactionBrand, String transactionDate, String tagMessage) {
        this.transactionAmount = transactionAmount;
        this.transactionBrand = transactionBrand;
        this.transactionDate = transactionDate;
        this.tagMessage = tagMessage;
    }


    public String getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionBrand() {
        return transactionBrand;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionBrand(String transactionBrand) {
        this.transactionBrand = transactionBrand;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTagMessage() {
        return tagMessage;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }
}
