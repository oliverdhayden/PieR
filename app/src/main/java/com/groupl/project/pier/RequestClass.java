package com.groupl.project.pier;

/**
 * Created by alexandra on 13/03/2018.
 */

public class RequestClass {
    String firstName;
    String lastName;
    String bucket_name;
    String key;

    public String getBucket_name() {
        return bucket_name;
    }

    public void setBucket_name(String bucket_name) {
        this.bucket_name = bucket_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RequestClass(String firstName, String lastName, String bucket_name, String key) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bucket_name = bucket_name;
        this.key = key;
    }

    public RequestClass() {
    }
}
