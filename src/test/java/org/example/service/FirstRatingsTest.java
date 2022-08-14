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

    private static final String ratingsShortCsv = "src/main/resources/data/ratings_short.csv";

    private static final String ratingsCsv = "src/main/resources/data/ratings.csv";
    private static final String ratedMoviesFullCsv = "src/main/resources/data/ratedmoviesfull.csv";

    private static final String ratedMoviesShortCsv = "src/main/resources/data/ratedmovies_short.csv";

    @Test
    public void testLoadMovies() throws IOException {
        List<Movie> movies = firstRatings.loadMovies(ratedMoviesShortCsv);
        assertThat(movies.size(), is(5));
        assertThat(movies.get(0).getID(), is("0006414"));

        movies = firstRatings.loadMovies(ratedMoviesFullCsv);
        assertThat(movies.size(), is(3143));
    }

    @Test
    public void testGetDirectorsFromMovies() throws IOException {
        List<Movie> movies = firstRatings.loadMovies(ratedMoviesShortCsv);
        Map<String, Integer> directors =
                firstRatings.getDirectorsFromMovies(movies);
        assertThat(directors.size(), is(5));

        movies = firstRatings.loadMovies(ratedMoviesFullCsv);
        directors = firstRatings.getDirectorsFromMovies(movies);
        assertThat(directors.size(), is(1905));
    }

    @Test
    public void testLoadRaters() {
        List<EfficientRater> raters = firstRatings.loadRaters(
                ratingsShortCsv);
        assertThat(raters.size(), is(5));

        List<EfficientRater> ratersBiggerFile = firstRatings.loadRaters(
                ratingsCsv);
        assertThat(ratersBiggerFile.size(), is(1048));
    }

    @Test
    public void testFindMaxNumberOfRaterByRater() throws IOException {
        List<EfficientRater> efficientRaters = firstRatings.loadRaters(ratingsShortCsv);
        Integer movieRatings = firstRatings.getTotalRatingByMovieId(
                efficientRaters, "1798709");
        assertThat(movieRatings, is(4));
    }

    @Test
    public void testGetMaxNumberOfRatings() {
        List<EfficientRater> efficientRaters = firstRatings.loadRaters(ratingsShortCsv);
        EfficientRater efficientRater = firstRatings.getMaxNumberOfRatings(efficientRaters);
        assertThat(efficientRater.getID(), is("2"));
        assertThat(efficientRater.numRatings(), is(3));

    }

    @Test
    public void testGetTotalNumberOfMoviesRated() {
        List<EfficientRater> efficientRaters = firstRatings.loadRaters(ratingsShortCsv);
        Integer moviesRated = firstRatings.getTotalNumberOfMoviesRated(efficientRaters);
        assertThat(moviesRated, is(4));
    }

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
        List<Movie> movies = firstRatings.loadMovies(ratedMoviesShortCsv);
        List<Movie> comedyMovies = firstRatings.getMoviesByGenre(movies, "Comedy");
        assertThat(comedyMovies.size(), is(1));

        movies = firstRatings.loadMovies(ratedMoviesFullCsv);
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
        List<Movie> movies = firstRatings.loadMovies(ratedMoviesFullCsv);
        List<Movie> moviesByMinutes = firstRatings.getMoviesByMinutes(movies, 150);
        assertThat(moviesByMinutes.size(), is(132));

        movies = firstRatings.loadMovies(ratedMoviesShortCsv);
        moviesByMinutes = firstRatings.getMoviesByMinutes(movies, 150);
        assertThat(moviesByMinutes.size(), is(2));
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
        List<Movie> movies = firstRatings.loadMovies(ratedMoviesFullCsv);
        Map<String, Integer> directors = firstRatings.getDirectorsFromMovies(movies);
        Map<String, Integer> director = firstRatings.getTopDirector(directors);
        assertThat(director.size(), is(1));
        assertThat(director.get("Woody Allen"), is(23));
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
    public void testGetNumRatingsByRaterById() {
        List<EfficientRater> efficientRaters = firstRatings.loadRaters(
                ratingsCsv);
        int numRatersByRaterId = firstRatings.getNumRatingsByRaterById(efficientRaters, "193");
        assertThat(numRatersByRaterId, is(119));
        numRatersByRaterId = firstRatings.getNumRatingsByRaterById(efficientRaters, "735");
        assertThat(numRatersByRaterId, is(314));

        efficientRaters = firstRatings.loadRaters(ratingsShortCsv);
        numRatersByRaterId = firstRatings.getNumRatingsByRaterById(efficientRaters, "2");
        assertThat(numRatersByRaterId, is(3));
    }

    @Test
    public void testXGetTotalRatingByMovieId() {
        List<EfficientRater> movies = firstRatings.loadRaters(ratingsShortCsv);
        Integer totalRatingByMovieId = firstRatings.getTotalRatingByMovieId(movies, "1798709");
        assertThat(totalRatingByMovieId, is(4));

        movies = firstRatings.loadRaters(ratingsCsv);
        totalRatingByMovieId = firstRatings.getTotalRatingByMovieId(movies, "1798709");
        assertThat(totalRatingByMovieId, is(38));

    }
}