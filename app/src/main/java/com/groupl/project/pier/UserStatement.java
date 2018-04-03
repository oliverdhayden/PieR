package com.groupl.project.pier;

import java.util.ArrayList;

/**
 * Created by Ollie on 31/03/2018.
 */

public class UserStatement {
    String test = "hello";
    String name = "ollie";
    int tests = 2222;
    ArrayList<String> lines;
    ArrayList<String> categories;

    
    UserStatement(){
        this.test = "hello world";
    }

    public UserStatement(ArrayList<String> lines, ArrayList<String> categories) {
        this.lines = lines;
        this.categories = categories;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }


}
