package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.datahandler.DataManager;
import org.jap.model.datahandler.MemoryManager;

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
    private final MemoryManager memoryManager;

    private final List<MoodData> moods;

    public enum Szenario{
        TEST
    }

    // todo: maybe add a init method for initiation of static variable for better control
    private static final MoodManager instance = new MoodManager();

    public MoodManager() {
        this.dataManager = new DataManager();
        MoodData.getIDFromDatabase(dataManager);
        this.moods = dataManager.loadMoods();
    }

    public MoodManager(Szenario s){
        this.memoryManager = new MemoryManager();
        this.moods = memoryManager.loadMoods();
    }

    public static MoodManager getInstance(){
        return instance;
    }

    public ArrayList<MoodData> getMoods() {
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
    private void addMood(MoodData mood){
        moods.add(mood);
        dataManager.saveMood(mood);
    }


    public MoodData createMood(int MoodID, String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue) throws IOException {
        if(MoodID < 0 || MoodData.MIN_ACTIVITYLEVEL > activityLevel || activityLevel > MoodData.MAX_ACTIVITYLEVEL){
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