package com.groupl.project.pier;

/**
 * Created by alexandra on 27/02/2018.
 */

public class FeedbackTagListItem {
    private String trafficLight;
    private String feedbackTag;
    private String feedbackMessage;

    public FeedbackTagListItem(String trafficLight, String feedbackTag, String feedbackMessage) {
        this.trafficLight = trafficLight;
        this.feedbackTag = feedbackTag;
        this.feedbackMessage = feedbackMessage;
    }

    public String getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(String trafficLight) {
        this.trafficLight = trafficLight;
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
