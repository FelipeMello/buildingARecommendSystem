package org.example.service;

import org.example.models.EfficientRater;
import org.example.models.Movie;
import org.example.models.Rating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SecondRatings {

    private List<Movie> myMovies;
    private List<EfficientRater> myRaters;

    FirstRatings firstRatings;

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
        firstRatings = new FirstRatings();
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
    public List<Rating> getMoviesByMinimalRaters(int minimalRaters) {
        return getAllRatings().stream()
                .filter(rating -> minimalRaters <= rating.getValue())
                .collect(Collectors.toList());
    }

    private List<Rating> getAllRatings() {
        List<Rating> list = new ArrayList<>();
        for (EfficientRater efficientRater : myRaters)
            efficientRater.getMyRatings().forEach((key, value) -> {
                Rating rating = new Rating(key, value.getValue());
                list.add(rating);
            });
        return list;
    }

    /**
     * In the SecondRatings class, write a private helper method named getAverageByID that has two parameters:
     * a String named id representing a movie ID and an integer named minimalRaters.
     * This method returns a double representing the average movie rating for this ID if there are at least minimalRaters ratings.
     * If there are not minimalRaters ratings, then it returns 0.0.
     *
     * @param movieId
     * @param minimalRaters
     * @return
     */
    public double getAverageByID(String movieId, int minimalRaters) {
        List<Rating> moviesByAverageRating = getMoviesByMinimalRaters(minimalRaters);
        List<Rating> averageRatingByMovieId = moviesByAverageRating.stream()
                .filter(rating -> movieId.equals(rating.getItem()))
                .collect(Collectors.toList());
        if (averageRatingByMovieId.isEmpty()) {
            return 0.0;
        }
        return averageRatingByMovieId.stream().mapToDouble(Rating::getValue).average().getAsDouble();
    }

    /**
     * TODO
     * In the SecondRatings class, write a public method named getAverageRatings,
     * which has one int parameter named minimalRaters.
     * This method should find the average rating for every movie that has been rated by at least minimalRaters raters.
     * Store each such rating in a Rating object in which the movie ID and the average rating are used in creating the Rating object.
     * The method getAverageRatings should return an ArrayList of all the Rating objects for movies that have at least the minimal number of
     * raters supplying a rating. For example, if minimalRaters has the value 10, then this method returns rating information (the movie ID and its average rating)
     * for each movie that has at least 10 ratings. You should consider calling the private getAverageByID method.
     *
     * @param minimalRaters
     * @return
     */
    public List<Rating> getAverageRatings(int minimalRaters) {
        List<Rating> ratingByAverageMin = new ArrayList<>();
            //todo
        return null;
    }

}
