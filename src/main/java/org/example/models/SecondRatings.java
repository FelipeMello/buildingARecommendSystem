package org.example.models;

import java.io.IOException;
import java.util.List;

public class SecondRatings {

    private List<Movie> myMovies;
    private List<Rater> myRaters;

    public SecondRatings() {
        // default constructor
//        this("ratedmoviesfull.csv", "ratings.csv");
    }
    /**
     * Write an additional SecondRatings constructor that has two String parameters named moviefile and ratingsfile.
     * The constructor should create a FirstRatings object and then call the loadMovies and loadRaters methods in
     * FirstRatings to read in all the movie and ratings data
     * and store them in the two private ArrayList variables of the SecondRatings class, myMovies and myRaters.
     */

    public SecondRatings(String movieFile, String ratingsFile) throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(movieFile);
//        myRaters = firstRatings.loadRaters(ratingsFile);
    }

}
