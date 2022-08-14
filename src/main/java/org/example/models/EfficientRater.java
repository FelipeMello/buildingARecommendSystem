package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EfficientRater {
    private String myID;

    private Map<String, Rating> myRatings;

    public Map<String, Rating> getMyRatings() {
        return myRatings;
    }

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item, rating));
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (myRatings.containsKey(item)) {
            return myRatings.get(item).getValue();
        } else {
            return -1;
        }
    }

    public int numRatings() {
        return myRatings.size();
    }

    public List<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<>();
        for (String movieID : myRatings.keySet()) {
            list.add(movieID);
        }
        return list;
    }
}
