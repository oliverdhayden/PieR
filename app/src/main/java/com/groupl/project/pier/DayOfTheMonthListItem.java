package com.groupl.project.pier;

/**
 * Created by alexandra on 14/02/2018.
 */

public class DayOfTheMonthListItem {
    private String tagName;
    private String unknown;
    private String dayOfTheMonth;
    private String month;

    public DayOfTheMonthListItem(String tagName, String unknown, String dayOfTheMonth, String month) {
        this.tagName = tagName;
        this.unknown = unknown;
        this.dayOfTheMonth = dayOfTheMonth;
        this.month = month;
    }

    public String getTagName() {
        return tagName;
    }

    public String getUnknown() {
        return unknown;
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

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

    public void setDayOfTheMonth(String dayOfTheMonth) {
        this.dayOfTheMonth = dayOfTheMonth;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
