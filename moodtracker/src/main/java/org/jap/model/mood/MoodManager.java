package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.datahandler.DataManager;

import java.io.IOException;
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

    private final DataManager dataManager;

    private final List<MoodData> moods;
    private boolean isLoaded = false;

    // todo: maybe add a init method for initiation of static variable for better control
    private static final MoodManager instance = new MoodManager();

    private MoodManager() {
        this.moods = new ArrayList<MoodData>();
        this.dataManager = new DataManager();
    }

    public static MoodManager getInstance(){
        return instance;
    }

    public List<MoodData> getMoods() {
        if (!isLoaded){
            dataManager.loadMoods();
            isLoaded = true;
        }
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
    public MoodData createMood(String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue){
        MoodData result = new MoodData(name, description, timeStamp, activityLevel, moodValue);
        addMood(result);
        return result;
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


    public MoodData createMood(int MoodID, String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue) throws IOException {
        if(MoodID < 0) {
            log.error("id < 0: "+MoodID);
            throw new IOException("argument/s invalid");
        }
        else if(MoodData.MIN_ACTIVITYLEVEL > activityLevel) {
            log.error("MoodData.MIN_ACTIVITYLEVEL > activityLevel: "+activityLevel);
            throw new IOException("argument/s invalid");
        }
        else if(activityLevel > MoodData.MAX_ACTIVITYLEVEL) {
            log.error("activityLevel > MoodData.MAX_ACTIVITYLEVEL: "+activityLevel);
            throw new IOException("argument/s invalid");
        }

        MoodData newMood = new MoodData(MoodID, name, description, timeStamp, activityLevel, moodValue);
        moods.add(newMood);
        return newMood;
    }

    public MoodData changeMood(MoodData mood, String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue){
        MoodData newMood = new MoodData(mood.getMoodID(), name, description, timeStamp, activityLevel, moodValue);
        deleteMood(mood);
        addMood(newMood);
        return newMood;
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