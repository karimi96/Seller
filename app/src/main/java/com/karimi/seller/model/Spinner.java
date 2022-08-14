package com.karimi.seller.model;

public class Spinner {
    int id;
    String name;
    String content;

    public Spinner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Spinner(int id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
