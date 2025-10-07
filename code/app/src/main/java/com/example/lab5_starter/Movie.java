package com.example.lab5_starter;

import java.io.Serializable;

// Movie object
public class Movie implements Serializable {

    // attributes
    private String title;
    private String genre;
    private String year;

    // *** Firestore needs a public no-arg constructor ***
    public Movie() { }

    // constructor
    public Movie(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    // setters
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
