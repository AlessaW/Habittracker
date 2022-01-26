package org.jap.model.mood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private final ObservableList<MoodData> moods;
    private final ObservableList<MoodData> roMoods;

    // todo: maybe add a init method for initiation of static variable for better control
    private static final MoodManager instance = new MoodManager();

    private MoodManager() {
        this.dataManager = new DataManager();
        MoodData.getIDFromDatabase(dataManager);
        this.moods = FXCollections.observableList(dataManager.loadMoods());
        this.roMoods = FXCollections.unmodifiableObservableList(moods);
    }

    public static MoodManager getInstance(){
        return instance;
    }

    public ObservableList<MoodData> getMoods() {
        return roMoods;
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
    
    public void addAllMoods(List<MoodData> list) {
        List<MoodData> newMoods = new ArrayList<>();
        list.forEach(m -> newMoods.add(new MoodData(m.getName(),m.getDescription(),m.getTimeStamp(),m.getActivityLevel(),m.getMoodValue()))); // Create new MoodData Objects to ensure encapsulation
        moods.addAll(newMoods);
        dataManager.saveMoods(newMoods);
    }

    public MoodData createMood(int MoodID, String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue) throws IOException {
        if(MoodID < 0 || MoodData.MIN_ACTIVITYLEVEL > activityLevel || activityLevel > MoodData.MAX_ACTIVITYLEVEL){
            throw new IOException("argument/s invalid");
        }

        MoodData newMood = new MoodData(MoodID, name, description, timeStamp, activityLevel, moodValue);
        moods.add(newMood);
        return newMood;
    }

    public void changeMood(MoodData mood, String name, String description, LocalDateTime timeStamp, int activityLevel, int moodValue){
        MoodData newMood = new MoodData(mood.getMoodID(), name, description, timeStamp, activityLevel, moodValue);
        moods.remove(mood);
        moods.add(newMood);
        dataManager.saveMood(newMood);
    }


    public void deleteMood(MoodData mood){
        moods.remove(mood);
        dataManager.deleteMood(mood);
    }
    
    public void deleteAllMoods() {
        moods.clear();
        dataManager.deleteAllMoods();
    }

    /**
     * closes the dataManager
     */
    public void close(){
        dataManager.close();
    }
}