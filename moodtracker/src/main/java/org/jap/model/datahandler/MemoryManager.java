package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * DummyClass for MoodManager to test MoodData without using the Database.
 *
 * Created by Jannika
 */

public class MemoryManager extends DataManager{
    private static final Logger log = LogManager.getLogger(MemoryManager.class);

    // Variables
    private ArrayList<MoodData> moodDataList;
    // Configuration Constants
    private static final String DEFAULT_FILE_NAME = "UserData";

    // Constructor
    /**
     * Default Constructor of a DataManager
     * <br><b>Do not use this for testing purposes!</b>
     */
    public MemoryManager() {
        //Todo: Konstruktor machen
    }

    // Methods
    /**
     * Loads all MoodData from the file
     * @return the loaded moods as ArrayList of MoodData
     */
    public ArrayList<MoodData> loadMoods() {
/*        ArrayList<MoodData> moods = new ArrayList<>();

        for (SimpleMood m : saveFileManager.loadMoods()) {
            moods.add(toMoodData(m));
        }

        return moods;*/
        return moodDataList;
    }


    /**
     * Saves one mood to the file
     * <br> - note that it overrides moods if the same id already exists in the file
     * @param mood The mood to save as MoodData
     */
    public void saveMood(MoodData mood) {
        moodDataList.add(mood);
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
