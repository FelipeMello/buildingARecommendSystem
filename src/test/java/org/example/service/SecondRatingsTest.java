package org.example.service;

import junit.framework.TestCase;
import org.example.models.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SecondRatingsTest extends TestCase {

    SecondRatings secondRatings;

    @BeforeEach
    public void setUp() throws IOException {
        secondRatings = new SecondRatings("src/main/resources/data/ratedmovies_short.csv","src/main/resources/data/ratings_short.csv");
    }

    @Test
    void getMovieSize() {
        System.out.println(secondRatings.getMovieSize());
        assertThat(secondRatings.getMovieSize(), is(5));
    }

    @Test
    void getRaterSize() {
        System.out.println(secondRatings.getRaterSize());
        assertThat(secondRatings.getRaterSize(), is(5));
    }

    @Test
    void getAverageRatings(){
        List<Rating> ratings = secondRatings.getAverageRatings(10);
        assertThat(ratings.size(), is(3));

    }
}