package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Manages saving and deleting of MoodData objects
 * Created by Jannika
 */

public class DataManager {
    private static final Logger log = LogManager.getLogger(DataManager.class);
    
    // Variables
    private final SaveFileManager saveFileManager;
    
    public DataManager() {
        String fileName = "UserData";
        saveFileManager = SaveFileManagerFactory.getSaveFileManager(SaveFileManagerFactory.SaveFileManagerType.Sqlite,fileName);
    }
    
    public ArrayList<MoodData>loadMoods() {
        ArrayList<MoodData> moods = new ArrayList<>();
        
        for (SimpleMood m : saveFileManager.loadMoods()) {
            moods.add(toMoodData(m));
        }
        
        return moods;
    }
    
    public void saveMoods(ArrayList<MoodData> moods) {
        ArrayList<SimpleMood> simpleMoods = new ArrayList<>();
        
        for (MoodData m : moods) {
            simpleMoods.add(toSimpleMood(m));
        }
        
        saveFileManager.saveMoods(simpleMoods);
    }
    
    public void saveMood(MoodData mood) {
        saveFileManager.saveMood(toSimpleMood(mood));
    }
    
    public void deleteMood(MoodData mood) {
        saveFileManager.deleteMood(mood.getMoodID());
    }
    
    public void deleteAllMoods() {
        saveFileManager.deleteAllMoods();
    }
    
    public int getMaxID() {
        return saveFileManager.getMaxID();
    }
    
    public void close() {
        saveFileManager.close();
    }
    
    private SimpleMood toSimpleMood(MoodData m) {
        return new SimpleMood(m.getMoodID(), m.getName(), m.getDescription(), m.getTimeStamp().format(DateTimeFormatter.ISO_DATE_TIME), m.getMoodValue(), m.getActivityLevel());
    }
    
    private MoodData toMoodData(SimpleMood m) {
        LocalDateTime timestamp = LocalDateTime.parse(m.timestamp(), DateTimeFormatter.ISO_DATE_TIME);
        return new MoodData(m.id(), m.name(), m.description(), timestamp, m.activity(), m.moodValue());
    }
}
