package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.datahandler.DataManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the List of MoodData Objects
 * When a field of a MoodData object is changed, it creates a new MoodData Object out of the new input and the data from the previous object
 *
 * Singleton Class
 *
 * Created by Jannika
 */

public class MoodManager {
    private static final Logger log = LogManager.getLogger(MoodManager.class);

    private DataManager dataManager;

    private List<MoodData> moods;

    // todo: maybe add a init method for initiation of static variable for better control
    private static MoodManager instance = new MoodManager();

    private MoodManager() {
        this.moods = new ArrayList<MoodData>();
        this.dataManager = new DataManager();
        moods = dataManager.loadMoods();
    }

    public static MoodManager getInstance(){
        return instance;
    }

    public List<MoodData> getMoods() {
        return new ArrayList<MoodData>(moods);
    }

    /**
     *
     * @param name
     * @param description
     * @param timeStamp
     * @param activityLevel
     * @param moodValue the positivity or negativity of a mood
     */
    public void createMood(String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue){
        MoodData result = new MoodData(name, description, timeStamp, activityLevel, moodValue);
        addMood(result);
    }

    /**
     * extra Method to add moods which were created with MoodFactory
     * /todo: or should moodFactory add moods directly to MoodManager?
     * @param mood
     */
    public void addMood(MoodData mood){
        moods.add(mood);
        dataManager.saveMood(mood);
    }


    public void changeName(MoodData mood, String name){
        MoodData newMood = new MoodData(mood.getMoodID(), name,mood.getDescription(),mood.getTimeStamp(), mood.getActivityLevel(), mood.getMoodValue());
        deleteMood(mood);
        addMood(newMood);
    }

    public void changeDescription(MoodData mood, String description){
        MoodData newMood = new MoodData(mood.getMoodID(), mood.getName(), description,mood.getTimeStamp(), mood.getActivityLevel(), mood.getMoodValue());
        deleteMood(mood);
        addMood(newMood);
    }
    public void changeTimeStamp(MoodData mood, LocalDateTime timeStamp){
        MoodData newMood = new MoodData(mood.getMoodID(), mood.getName(),mood.getDescription(), timeStamp, mood.getActivityLevel(), mood.getMoodValue());
        deleteMood(mood);
        addMood(newMood);
    }
    public void changeActivityLevel(MoodData mood, int activityLevel){
        MoodData newMood = new MoodData(mood.getMoodID(), mood.getName(),mood.getDescription(),mood.getTimeStamp(), activityLevel, mood.getMoodValue());
        deleteMood(mood);
        addMood(newMood);
    }
    public void changeMoodValue(MoodData mood, int moodValue){
        MoodData newMood = new MoodData(mood.getMoodID(), mood.getName(),mood.getDescription(),mood.getTimeStamp(), mood.getActivityLevel(), moodValue);
        deleteMood(mood);
        addMood(newMood);
    }

    public void deleteMood(MoodData mood){
        moods.remove(mood);
        dataManager.deleteMood(mood);
    }

    /**
     * closes the dataManager
     */
    public void close(){
        dataManager.close();
    }
}