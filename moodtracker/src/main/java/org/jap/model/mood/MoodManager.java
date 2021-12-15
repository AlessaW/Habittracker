package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Handles the Collection of MoodData Objects
 * When a field of a MoodData object is changed, it creates a new MoodData Object out of the new input and the data from the previous object
 *
 * Created by Jannika
 */

public class MoodManager {
    private static final Logger log = LogManager.getLogger(MoodManager.class);

    ArrayList<MoodData> moods;

    public MoodManager() {
        this.moods = new ArrayList<MoodData>();
    }

    public Collection getMoods() {
        return moods;
    }

    public void createMood(String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue){
        MoodData result = new MoodData(name, description, timeStamp, activityLevel, moodValue);
        moods.add(result);
    }

    public void modifyMood(MoodData mood, String attribute, String newValue){

        int index = moods.indexOf(mood);
        MoodData newMood = null;

        switch(attribute){
            case "name": newMood = newMood = new MoodData(mood.getMoodID(), newValue, mood.getDescription(), mood.getTimeStamp(), mood.getActivityLevel(), mood.getMoodValue());
            case "description" : newMood = new MoodData(mood.getMoodID(), mood.getName(), newValue, mood.getTimeStamp(), mood.getActivityLevel(), mood.getMoodValue());
            case "timeStamp" :
                //todo: parse String to LocalDateTime
                //newMood = new MoodData(mood.getMoodID(), mood.getName(), mood.getDescription(), newValue , mood.getActivityLevel(), mood.getMoodValue());
            case "activityLevel" : newMood = new MoodData(mood.getMoodID(), mood.getName(), mood.getDescription(), mood.getTimeStamp(), Integer.parseInt(newValue), mood.getMoodValue());
            case "moodValue" : newMood = new MoodData(mood.getMoodID(), mood.getName(), mood.getDescription(), mood.getTimeStamp(), mood.getActivityLevel(), Integer.parseInt(newValue));
        }
        moods.remove(index);
        if(newMood != null){
            moods.add(newMood);
        }

    }
}