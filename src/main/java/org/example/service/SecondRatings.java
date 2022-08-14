package org.example.service;

import org.example.models.EfficientRater;
import org.example.models.Movie;
import org.example.models.Rating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondRatings {

    private List<Movie> myMovies;
    private List<EfficientRater> myRaters;

    public SecondRatings() throws IOException {
        //default constructor
        this("src/main/resources/data/ratedmoviesfull.csv", "src/main/resources/data/ratings.csv");
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
        myRaters = firstRatings.loadRaters(ratingsFile);
    }

    public int getMovieSize() {
        return myMovies.size();
    }

    public int getRaterSize() {
        return myRaters.size();
    }
    /**
     * write a public method named getAverageRatings, which has one int parameter named minimalRaters.
     * This method should find the average rating for every movie that has been rated by at least minimalRaters raters.
     * Store each such rating in a Rating object in which the movie ID and the average rating are used in creating the Rating object.
     * The method getAverageRatings should return an ArrayList of all the Rating objects for movies that have at least the minimal number of raters supplying a rating.
     * For example, if minimalRaters has the value 10, then this method returns rating information (the movie ID and its average rating) for each movie that has at least
     * 10 ratings.
     * You should consider calling the private getAverageByID method.
     */
    public List<Rating> getAverageRatings(int minRater) {
        List<Rating> moviesByMinRater = new ArrayList<>();
        double min = minRater;
        myRaters.forEach(efficientRater -> efficientRater.getMyRatings().entrySet().forEach(rating -> {
            if (min <= rating.getValue().getValue()) {
                Rating ratingTemp = new Rating(rating.getKey(), rating.getValue().getValue());
                moviesByMinRater.add(ratingTemp);
            }
        }));
        return moviesByMinRater;
    }

}
