package org.example.models;


import edu.duke.FileResource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
        List<EfficientRater> raters = new ArrayList<EfficientRater>();
        List<String> raterIDs = new ArrayList<String>();
        for (CSVRecord csvRecord : fr.getCSVParser()) {
            String raterId = csvRecord.get("rater_id");
            String movieId = csvRecord.get("movie_id");
            double rating = Double.parseDouble(csvRecord.get("rating"));
            String time = csvRecord.get("time");

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

    public Integer getTotalRatingByMovieId(String filename, String movieId) throws IOException {
        List<EfficientRater> efficientRaters = loadRaters(filename);
        return (int) efficientRaters.stream().filter(efficientRater -> efficientRater.getItemsRated().contains(movieId)).count();
    }

    public Integer totalMoviesRated(String filename, String movieId) throws IOException {
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

    public List<Movie> getMoviesByGenre(String filename, String genre) throws IOException {
        movies = new ArrayList<>();
        csvRecords = getCsvRecords(filename);
        for (CSVRecord csvRecord : csvRecords) {
            if (skipHeader(csvRecord)) continue;
            if (!csvRecord.get(4).isEmpty() && csvRecord.get(4).contains(genre)) {
                movies.add(buildMovie(csvRecord));
            }
        }
        return movies;
    }

    private boolean skipHeader(CSVRecord csvRecord) {
        if (csvRecord.getRecordNumber() == 1) {
            return true;
        }
        return false;
    }

    public List<Movie> getMoviesByMinutes(String filename, Integer minutes) throws IOException {
        movies = new ArrayList<>();
        csvRecords = getCsvRecords(filename);
        for (CSVRecord csvRecord : csvRecords) {
            if (skipHeader(csvRecord)) continue;
            if (Integer.parseInt(csvRecord.get(6)) > minutes) {
                movies.add(buildMovie(csvRecord));
            }
        }
        return movies;
    }

    public Map<String, Integer> getMoviesByDirectors(String filename) throws IOException {
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
        Map<String, Integer> directors = getMoviesByDirectors(filename);
        int max = 0;
        String directorName = null;
        for (String director : directors.keySet()) {
            if (max < directors.get(director).intValue()) {
                max = directors.get(director).intValue();
                directorName = director;
            }
            ;
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

//    public Rating getMaxNumberOfRatingsByAnyRating(String filename) throws IOException {
//        List<EfficientRater> raters = loadRaters(filename);
//        double tempValue = 0;
//        int maxIndex = 0;
//        for (int i = 0; i < raters.size(); i++) {
//            if (raters.get(i).getValue() > tempValue) {
//                tempValue = raters.get(i).getValue();
//                maxIndex = i;
//            }
//        }
//        return raters.get(maxIndex);
//    }
}
