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

    public List<EfficientRater> loadRaters(String filename) throws IOException {
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

    public int getNumRatingsByRaterById(List<EfficientRater> efficientRaters, String raterId) throws IOException {
        int numRatings = 0;
        for (EfficientRater efficientRater : efficientRaters) {
            if (efficientRater.getID().equals(raterId)) {
                numRatings = efficientRater.numRatings();
            }
        }
        return numRatings;
    }

    public Integer getTotalRatingByMovieId(String filename, String movieId) throws IOException {
        List<EfficientRater> efficientRaters = loadRaters(filename);
        return (int) efficientRaters.stream().filter(efficientRater -> efficientRater.getItemsRated().contains(movieId)).count();
    }

    public Integer getTotalRatingsByMovieId(String filename, String movieId) throws IOException {
        List<EfficientRater> efficientRaters = loadRaters(filename);
        ArrayList<String> moviesRated = new ArrayList<String>();
        int numMovieRated = 0;
        for (EfficientRater efficientRater : efficientRaters) {
            for (String item : efficientRater.getItemsRated()) {
                if (!moviesRated.contains(item)) {
                    moviesRated.add(item);
                    numMovieRated++;
                }
            }
        }
        return numMovieRated;
    }

    public Integer getTotalNumberOfRaters(String filename) throws IOException {

        return loadRaters(filename).size();
    }

    public List<Movie> getMoviesByGenre(List<Movie> movies, String genre) throws IOException {
        List<Movie> moviesByGenre = new ArrayList<>();
        movies.forEach(movie -> {
            if (movie.getGenres().contains(genre)) {
                moviesByGenre.add(movie);
            }
        });
        return moviesByGenre;
    }

    private boolean skipHeader(CSVRecord csvRecord) {
        if (csvRecord.getRecordNumber() == 1) {
            return true;
        }
        return false;
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

    /**
     * TODO REFRACTOR THIS METHOD
     * @param filename
     * @return
     * @throws IOException
     */
    public Map<String, Integer> getDirectorsFromMovies(String filename) throws IOException {
        movies = loadMovies(filename);
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

    public Map<String, Integer> getTopDirector(String filename) throws IOException {
        Map<String, Integer> directors = getDirectorsFromMovies(filename);
        int max = 0;
        String directorName = null;
        for (String director : directors.keySet()) {
            if (max < directors.get(director).intValue()) {
                max = directors.get(director).intValue();
                directorName = director;
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

    public EfficientRater getMaxNumberOfRatings(String filename) throws IOException {
        List<EfficientRater> raters = loadRaters(filename);
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

    public EfficientRater getRaterById(List<EfficientRater> efficientRaters, String raterId){
        for(EfficientRater efficientRater: efficientRaters){
            if(efficientRater.getID().equals(raterId)){
                return  efficientRater;
            }
        }
        return null;
    }
}
