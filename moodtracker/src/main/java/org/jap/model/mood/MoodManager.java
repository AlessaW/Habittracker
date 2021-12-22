package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

    public ArrayList<MoodData> getMoods() {
        return moods;
    }

    /**
     *
     * @param name
     * @param description
     * @param timeStamp
     * @param activityLevel
     * @param moodValue
     */
    public void createMood(String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue){
        MoodData result = new MoodData(name, description, timeStamp, activityLevel, moodValue);
        moods.add(result);
    }

    /**
     * changes the value of a given attribute of a given mood
     * the new value is given as a String, a LocalDateTime value has to be given in the pattern: "yyyy-MM-dd HH:mm"
     * returns true if mood was changed successfully, false if not
     * @param mood
     * @param attribute
     * @param newValue
     * @return
     */
    public boolean modifyMood(MoodData mood, String attribute, String newValue){

        int index = moods.indexOf(mood);
        MoodData newMood = null;

        switch(attribute){
            case "name": newMood = new MoodData(mood.getMoodID(), newValue, mood.getDescription(), mood.getTimeStamp(), mood.getActivityLevel(), mood.getMoodValue());
            case "description" : newMood = new MoodData(mood.getMoodID(), mood.getName(), newValue, mood.getTimeStamp(), mood.getActivityLevel(), mood.getMoodValue());
            case "timeStamp" :
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(newValue, formatter);
                newMood = new MoodData(mood.getMoodID(), mood.getName(), mood.getDescription(), dateTime , mood.getActivityLevel(), mood.getMoodValue());
            case "activityLevel" : newMood = new MoodData(mood.getMoodID(), mood.getName(), mood.getDescription(), mood.getTimeStamp(), Integer.parseInt(newValue), mood.getMoodValue());
            case "moodValue" : newMood = new MoodData(mood.getMoodID(), mood.getName(), mood.getDescription(), mood.getTimeStamp(), mood.getActivityLevel(), Integer.parseInt(newValue));
        }
        if(newMood != null){
            moods.remove(index);
            moods.add(newMood);
            return true;
        }
        return false;
    }

    public void deleteMood(MoodData mood){
        moods.remove(mood);
    }
}