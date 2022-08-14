package org.example.service;


import edu.duke.FileResource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.models.EfficientRater;
import org.example.models.Movie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FirstRatings {
    List<Movie> movies;
    List<CSVRecord> csvRecords;

    public List<Movie> loadMovies(String filename) throws IOException {
        movies = new ArrayList<>();
        csvRecords = getCsvRecords(filename);
        for (CSVRecord csvRecord : csvRecords) {
            if (skipHeader(csvRecord)) continue;
            movies.add(buildMovie(csvRecord));
        }
        return movies;
    }
    public List<EfficientRater> loadRaters(String filename) {
        FileResource fr = new FileResource(filename);
        List<EfficientRater> raters = new ArrayList<>();
        List<String> raterIDs = new ArrayList<>();
        for (CSVRecord csvRecord : fr.getCSVParser()) {
            String raterId = csvRecord.get("rater_id");
            String movieId = csvRecord.get("movie_id");
            double rating = Double.parseDouble(csvRecord.get("rating"));
            if (raterIDs.contains(raterId)) {
                Iterator<EfficientRater> raterIterator = raters.iterator();
                while (raterIterator.hasNext()) {
                    EfficientRater r = raterIterator.next();
                    if (r.getID().equals(raterId)) {
                        r.addRating(movieId, rating);
                        break;
                    }
                }
            } else {
                EfficientRater newRater = new EfficientRater(raterId);
                newRater.addRating(movieId, rating);
                raters.add(newRater);
                raterIDs.add(raterId);
            }
        }
        return raters;
    }
    public int getNumRatingsByRaterById(List<EfficientRater> efficientRaters, String raterId) {
        int numRatings = 0;
        for (EfficientRater efficientRater : efficientRaters) {
            if (efficientRater.getID().equals(raterId)) {
                numRatings = efficientRater.numRatings();
            }
        }
        return numRatings;
    }
    public Integer getTotalRatingByMovieId(List<EfficientRater> efficientRaters, String movieId) {
        return (int) efficientRaters.stream().filter(efficientRater -> efficientRater.getItemsRated().contains(movieId)).count();
    }
    public List<Movie> getMoviesByGenre(List<Movie> movies, String genre) {
        List<Movie> moviesByGenre = new ArrayList<>();
        movies.forEach(movie -> {
            if (movie.getGenres().contains(genre)) {
                moviesByGenre.add(movie);
            }
        });
        return moviesByGenre;
    }
    private boolean skipHeader(CSVRecord csvRecord) {
        return csvRecord.getRecordNumber() == 1;
    }
    public List<Movie> getMoviesByMinutes(List<Movie> movies, Integer minutes) {
        List<Movie> moviesByMinutes = new ArrayList<>();
        movies.forEach(movie -> {
            if (movie.getMinutes() > minutes) {
                moviesByMinutes.add(movie);
            }
        });
        return moviesByMinutes;
    }
    public Map<String, Integer> getDirectorsFromMovies(List<Movie> movies) {
        Map<String, Integer> directors = new HashMap<>();
        movies.forEach(movie -> {
            String director = movie.getDirector();
            if (null != directors.get(director)) {
                directors.put(director, directors.get(director).intValue() + 1);
            } else {
                directors.put(director, +1);
            }
        });
        return directors;
    }
    public Map<String, Integer> getTopDirector(Map<String, Integer> directors) {
        int max = 0;
        String directorName = null;
        for (Map.Entry<String, Integer> director : directors.entrySet()) {
            if (max < director.getValue()) {
                max = director.getValue();
                directorName = director.getKey();
            }
        }
        Map<String, Integer> director = new HashMap<>();
        director.put(directorName, max);
        return director;
    }
    public Movie buildMovie(CSVRecord csvRecord) {
        return new Movie(
                csvRecord.get(0).trim(),
                csvRecord.get(1).trim(),
                csvRecord.get(2).trim(),
                csvRecord.get(4).trim(),
                csvRecord.get(5).trim(),
                csvRecord.get(3).trim(),
                csvRecord.get(7).trim(),
                Integer.parseInt(csvRecord.get(6)));
    }
    private List<CSVRecord> getCsvRecords(String filename) throws IOException {
        CSVParser parser = CSVParser.parse(new File(filename),
                StandardCharsets.UTF_8, CSVFormat.DEFAULT);
        return parser.getRecords();
    }
    public EfficientRater getMaxNumberOfRatings(List<EfficientRater> raters) {
        int max = 0;
        String raterId = null;
        for (EfficientRater efficientRater : raters) {
            if (max < efficientRater.numRatings()) {
                max = efficientRater.numRatings();
                raterId = efficientRater.getID();
            }
        }
        return getRaterById(raters, raterId);

    }
    public EfficientRater getRaterById(List<EfficientRater> efficientRaters, String raterId) {
        for (EfficientRater efficientRater : efficientRaters) {
            if (efficientRater.getID().equals(raterId)) {
                return efficientRater;
            }
        }
        return null;
    }
    public Integer getTotalNumberOfMoviesRated(List<EfficientRater> efficientRaters) {
        List<String> ratedMovies = new ArrayList<>();
        efficientRaters.forEach(efficientRater -> efficientRater.getItemsRated().forEach(itemRated -> {
            if (!ratedMovies.contains(itemRated)) {
                ratedMovies.add(itemRated);
            }
        }));
        return ratedMovies.size();
    }
}
