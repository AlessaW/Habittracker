package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages saving and deleting of MoodData objects
 * Created by Jannika
 */

public class DataManager {
    private static final Logger log = LogManager.getLogger(DataManager.class);
    
    // Variables
    private final SaveFileManager saveFileManager;
    
    // Configuration Constants
    private static final String DEFAULT_FILE_NAME = "UserData";
    
    // Constructor
    /**
     * Default Constructor of a DataManager
     * <br><b>Do not use this for testing purposes!</b>
     */
    public DataManager() {
        this(DEFAULT_FILE_NAME);
    }
    
    /**
     * Constructor of a DataManager
     * <br><b>Use only this for testing purposes!</b>
     * @param fileName the database name to access
     */
    public DataManager(String fileName) {
        saveFileManager = SaveFileManagerFactory.getSaveFileManager(SaveFileManagerFactory.SaveFileManagerType.Sqlite,fileName);
    }
    
    // Methods
    /**
     * Loads all MoodData from the file
     * @return the loaded moods as ArrayList of MoodData
     */
    public List<MoodData>loadMoods() {
        List<MoodData> moods = new ArrayList<>();
        
        for (SimpleMood m : saveFileManager.loadMoods()) {
            moods.add(toMoodData(m));
        }
        
        return moods;
    }
    
    /**
     * Saves multiple moods to the file
     * <br> - note that it overrides moods if the same id already exists in the file
     * @param moods The list of moods to save as ArrayList of MoodData
     */
    public void saveMoods(List<MoodData> moods) {
        List<SimpleMood> simpleMoods = new ArrayList<>();
        
        for (MoodData m : moods) {
            simpleMoods.add(toSimpleMood(m));
        }
        
        saveFileManager.saveMoods(simpleMoods);
    }
    
    /**
     * Saves one mood to the file
     * <br> - note that it overrides moods if the same id already exists in the file
     * @param mood The mood to save as MoodData
     */
    public void saveMood(MoodData mood) {
        saveFileManager.saveMood(toSimpleMood(mood));
    }
    
    /**
     * Deletes a mood from the file
     * @param mood the mood to delete
     */
    public void deleteMood(MoodData mood) {
        saveFileManager.deleteMood(mood.getMoodID());
    }
    
    /**
     * Deletes all moods from the file
     */
    public void deleteAllMoods() {
        saveFileManager.deleteAllMoods();
    }
    
    /**
     * @return the highest id, stored in the file
     */
    public int getMaxID() {
        return saveFileManager.getMaxID();
    }
    
    /**
     * closes the database connection safely - recommended!
     */
    public void close() {
        saveFileManager.close();
    }
    
    /**
     * Converts a MoodData Object to a SimpleMood Object
     * @param m the MoodData Object to convert
     * @return a SimpleMood Object
     */
    private SimpleMood toSimpleMood(MoodData m) {
        return new SimpleMood(m.getMoodID(), m.getName(), m.getDescription(), m.getTimeStamp().format(DateTimeFormatter.ISO_DATE_TIME), m.getMoodValue(), m.getActivityLevel());
    }
    
    /**
     * Converts a SimpleMood Object to a MoodData Object
     * @param m the SimpleMood Object to convert
     * @return a MoodData Object
     */
    private MoodData toMoodData(SimpleMood m) {
        LocalDateTime timestamp = LocalDateTime.parse(m.timestamp(), DateTimeFormatter.ISO_DATE_TIME);
        return new MoodData(m.id(), m.name(), m.description(), timestamp, m.activity(), m.moodValue());
    }
}
