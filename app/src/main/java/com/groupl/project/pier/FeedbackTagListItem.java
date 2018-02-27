package com.groupl.project.pier;

/**
 * Created by alexandra on 27/02/2018.
 */

public class FeedbackTagListItem {
    private String colorRed;
    private String colorYellow;
    private String colorGreen;
    private String feedbackTag;
    private String feedbackMessage;

    public FeedbackTagListItem(String colorRed, String colorYellow, String colorGreen, String feedbackTag, String feedbackMessage) {
        this.colorRed = colorRed;
        this.colorYellow = colorYellow;
        this.colorGreen = colorGreen;
        this.feedbackTag = feedbackTag;
        this.feedbackMessage = feedbackMessage;
    }

    public String getColorRed() {
        return colorRed;
    }

    public void setColorRed(String colorRed) {
        this.colorRed = colorRed;
    }

    public String getColorYellow() {
        return colorYellow;
    }

    public void setColorYellow(String colorYellow) {
        this.colorYellow = colorYellow;
    }

    public String getColorGreen() {
        return colorGreen;
    }

    public void setColorGreen(String colorGreen) {
        this.colorGreen = colorGreen;
    }

    public String getFeedbackTag() {
        return feedbackTag;
    }

    public void setFeedbackTag(String feedbackTag) {
        this.feedbackTag = feedbackTag;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
}
