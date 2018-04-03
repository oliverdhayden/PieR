package com.groupl.project.pier;

public class statement {
    // Day,Month,Year,Description,Category,Value,Balance
    int id;
    String Day;
    String Month;
    String Year;
    String Description;
    String Category;
    String Value;
    String Balance;

    public statement() {

    }

    public statement(int id, String Day, String Month, String Year, String Description, String Category, String Value, String Balance) {
        this.id = id;
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;
        this.Description = Description;
        this.Category = Category;
        this.Value = Value;
        this.Balance = Balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
