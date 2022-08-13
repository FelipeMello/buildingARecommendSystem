package org.example.service;

import org.example.models.EfficientRater;
import org.example.models.Movie;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FirstRatingsTest {

    FirstRatings firstRatings = new FirstRatings();

    @Test
    public void testLoadMovies() throws IOException {
        List<Movie> movies = firstRatings.loadMovies("src/main/resources/data/ratedmovies_short.csv");
        assertThat(movies.size(), is(5));
        assertThat(movies.get(0).getID(), is("0006414"));

        movies = firstRatings.loadMovies("src/main/resources/data/ratedmoviesfull.csv");
        assertThat(movies.size(), is(3143));
    }

    @Test
    public void testGetMoviesByMinutesShort() throws IOException {

    }

    @Test
    public void testGetDirectorsFromMovies() throws IOException {
        Map<String, Integer> directors =
                firstRatings.getDirectorsFromMovies(
                        "src/main/resources/data/ratedmovies_short.csv");
        assertThat(directors.size(), is(5));

        directors = firstRatings.getDirectorsFromMovies(
                "src/main/resources/data/ratedmoviesfull.csv");
        assertThat(directors.size(), is(1905));
    }

    @Test
    public void testLoadRaters() throws IOException {
        List<EfficientRater> raters = firstRatings.loadRaters(
                "src/main/resources/data/ratings_short.csv");
        assertThat(raters.size(), is(5));

        List<EfficientRater> ratersBiggerFile = firstRatings.loadRaters(
                "src/main/resources/data/ratings.csv");
        assertThat(ratersBiggerFile.size(), is(1048));
    }

    //https://github.com/abhishekrana8651/StepOne-Recommedation-System-/blob/f00a68d5caa5afb88add510aff3eedb68d970ee1/FirstRatings.java
    @Test
    public void testFindMaxNumberOfRaterByRater() throws IOException {
        Integer movieRatings = firstRatings.getTotalRatingsByMovieId(
                "src/main/resources/data/ratings_short.csv", "1798709");
        assertThat(movieRatings, is(4));
    }

    @Test
    public void testGetNumRatingsByRaterById() throws IOException {
        List<EfficientRater> efficientRaters = firstRatings.loadRaters("src/main/resources/data/ratings_short.csv");
        int numRatingByRaterId = firstRatings.getNumRatingsByRaterById(efficientRaters, "2");
        assertThat(numRatingByRaterId, is(3));
    }

    @Test
    public void testGetMaxNumberOfRatings() throws IOException {
        EfficientRater efficientRater = firstRatings.getMaxNumberOfRatings("src/main/resources/data/ratings_short.csv");
        assertThat(efficientRater.getID(), is("2"));
        assertThat(efficientRater.numRatings(), is(3));

    }

//    @Test
//    public void testGetMoviesRated() throws IOException {
//        Integer moviesRated = firstRatings.getTotalNumberOfMoviesRated("src/main/resources/data/ratings_short.csv");
//        assertThat(moviesRated, is(4));
//    }

    /**
     * Question 2
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * How many of the movies include the Comedy genre?
     *
     * @throws IOException
     */
    @Test
    public void testGetMoviesByGenre() throws IOException {
        List<Movie> movies = firstRatings.loadMovies("src/main/resources/data/ratedmovies_short.csv");
        List<Movie> comedyMovies = firstRatings.getMoviesByGenre(movies, "Comedy");
        assertThat(comedyMovies.size(), is(1));

        movies = firstRatings.loadMovies("src/main/resources/data/ratedmoviesfull.csv");
        comedyMovies = firstRatings.getMoviesByGenre(movies, "Comedy");
        assertThat(comedyMovies.size(), is(960));
    }

    /**
     * Question 3
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * How many of these movies run longer than 150 minutes?
     */
    @Test
    public void testGetMoviesByMinutes() throws IOException {
        List<Movie> movies = firstRatings.loadMovies("src/main/resources/data/ratedmoviesfull.csv");
        List<Movie> moviesByMinutes = firstRatings.getMoviesByMinutes(movies, 150);
        assertThat(moviesByMinutes.size(), is(132));

        movies = firstRatings.loadMovies("src/main/resources/data/ratedmovies_short.csv");
        moviesByMinutes = firstRatings.getMoviesByMinutes(movies, 150);
        assertThat(moviesByMinutes.size(), is(2));
    }

    /**
     * Question 4
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * What is the maximum number of films directed by one director?
     */
    @Test
    public void testGetMoviesByDirectors() throws IOException {


    }

    /**
     * Question 4
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * What is the maximum number of films directed by one director?
     * <p>
     * Question 5
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * What is the name of the director who directed more films than any other director? (There is only one.)
     *
     * @throws IOException
     */
    @Test
    public void testGetTopDirector() throws IOException {
        Map<String, Integer> directors =
                firstRatings.getTopDirector(
                        "src/main/resources/data/ratedmoviesfull.csv");
        assertThat(directors.size(), is(1));
        assertThat(directors.get("Woody Allen"), is(23));
    }

    /**
     * Question 6
     * In the class FirstRatings, run the method testLoadRaters on the file ratings.csv. Note there are 1048 raters in this file.
     * <p>
     * How many ratings does the rater number 193 have?
     *
     * @throws IOException
     */
    @Test
    public void testgetTotalRatingByMovieId() throws IOException {
        List<EfficientRater> totalRatingByMovieId = firstRatings.loadRaters(
                "src/main/resources/data/ratings.csv");
        assertThat(totalRatingByMovieId.get(192).getItemsRated().size(), is(119));
    }

//    /**
//     * Question 6
//     * In the class FirstRatings, run the method testLoadRaters on the file ratings.csv. Note there are 1048 raters in this file.
//     * <p>
//     * How many ratings does the rater number 193 have?
//     *
//     * @throws IOException
//     */
//    @Test
//    public void testgetTotalRatingByMovieIdZX() throws IOException {
//        Rating maxRating = firstRatings.getMaxNumberOfRatingsByAnyRating("src/main/resources/data/ratings.csv");
//        assertThat(maxRating.getItem(), is("314"));
//        assertThat(maxRating.getValue(), is(735));
//    }

    @Test
    public void testXGetTotalRatingByMovieId() throws IOException {
        Integer totalRatingByMovieId = firstRatings.getTotalRatingByMovieId(
                "src/main/resources/data/ratings_short.csv", "1798709");
        assertThat(totalRatingByMovieId, is(4));

        totalRatingByMovieId = firstRatings.getTotalRatingByMovieId(
                "src/main/resources/data/ratings.csv", "1798709");
        assertThat(totalRatingByMovieId, is(38));

    }

//    @Test
//    public void testGGetMoviesRated() throws IOException {
//        Integer moviesRated = firstRatings.getTotalNumberOfMoviesRated("src/main/resources/data/ratings.csv");
//        assertThat(moviesRated, is(3143));
//    }


}