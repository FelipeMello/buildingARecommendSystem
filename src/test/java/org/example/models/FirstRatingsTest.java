package org.example.models;

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
        List<Movie> movies =
                firstRatings.loadMovies
                        ("src/main/resources/data/ratedmovies_short.csv");
        assertThat(movies.size(), is(5));
        assertThat(movies.get(0).getID(), is("0006414"));
    }

    @Test
    public void testgetMoviesByMinutes() throws IOException {
        List<Movie> movies =
                firstRatings.getMoviesByMinutes(
                        "src/main/resources/data/ratedmovies_short.csv", 150);
        assertThat(movies.size(), is(2));
    }

    @Test
    public void testLoadMoviesWithratedmoviesfull() throws IOException {
        List<Movie> movies =
                firstRatings.getMoviesByGenre(
                        "src/main/resources/data/ratedmovies_short.csv", "Comedy");
        assertThat(movies.size(), is(1));
    }

    @Test
    public void getMoviesByDirectors() throws IOException {
        Map<String, Integer> directors =
                firstRatings.getMoviesByDirectors(
                        "src/main/resources/data/ratedmovies_short.csv");
        assertThat(directors.size(), is(5));
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
        Integer movieRatings = firstRatings.totalMoviesRated(
                "src/main/resources/data/ratings_short.csv", "1798709");
        assertThat(movieRatings, is(4));
    }

    @Test
    public void testLoadRatersRatingSize() throws IOException {
        List<EfficientRater> raters = firstRatings.loadRaters(
                "src/main/resources/data/ratings_short.csv");
        assertThat(raters.size(), is(5));
        assertThat(raters.get(1).getItemsRated().size(), is(3));
    }

    @Test
    public void testGetMoviesRated() throws IOException {
        Integer moviesRated = firstRatings.getTotalNumberOfMoviesRated("src/main/resources/data/ratings_short.csv");
        assertThat(moviesRated, is(4));
    }

    @Test
    public void testGetTotalRatingByMovieId() throws IOException {
        Integer totalRatingByMovieId = firstRatings.getTotalRatingByMovieId(
                "src/main/resources/data/ratings_short.csv", "1798709");
        assertThat(totalRatingByMovieId, is(4));
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
        List<Movie> moviesByGenre =
                firstRatings.getMoviesByGenre(
                        "src/main/resources/data/ratedmoviesfull.csv", "Comedy");
        assertThat(moviesByGenre.size(), is(960));
    }

    /**
     * Question 3
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * How many of these movies run longer than 150 minutes?
     */
    @Test
    public void testGetMoviesByMinutes() throws IOException {
        List<Movie> movies = firstRatings.getMoviesByMinutes("src/main/resources/data/ratedmoviesfull.csv", 150);
        assertThat(movies.size(), is(132));
    }

    /**
     * Question 4
     * In the class FirstRatings, run the method testLoadMovies on the file ratedmoviesfull.csv. Note there are 3143 movies in this file.
     * <p>
     * What is the maximum number of films directed by one director?
     */
    @Test
    public void testGetMoviesByDirectors() throws IOException {
        Map<String, Integer> directors =
                firstRatings.getMoviesByDirectors(
                        "src/main/resources/data/ratedmoviesfull.csv");
        assertThat(directors.size(), is(1905));

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
                "src/main/resources/data/ratings.csv", "1798709");
        assertThat(totalRatingByMovieId, is(38));
    }

    @Test
    public void testGGetMoviesRated() throws IOException {
        Integer moviesRated = firstRatings.getTotalNumberOfMoviesRated("src/main/resources/data/ratings.csv");
        assertThat(moviesRated, is(3143));
    }


}