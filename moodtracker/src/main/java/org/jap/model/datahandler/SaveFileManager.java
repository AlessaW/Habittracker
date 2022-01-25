package org.jap.model.datahandler;

import java.util.ArrayList;
import java.util.List;

interface SaveFileManager {
    /**
     * Loads all MoodData from the file
     * @return the loaded moods as ArrayList of SimpleMood
     */
    List<SimpleMood> loadMoods();
    /**
     * Saves multiple moods to the file
     * <br> - note that it overrides moods if the same id already exists in the file
     * @param moods The list of moods to save as ArrayList of SimpleMood
     */
    void saveMoods(List<SimpleMood> moods);
    /**
     * Saves one mood to the file
     * <br> - note that it overrides moods if the same id already exists in the file
     * @param mood The mood to save as SimpleMood
     */
    void saveMood(SimpleMood mood);
    
    /**
     * Deletes a mood from the file
     * @param id the id of the mood to delete
     */
    void deleteMood(int id);
    /**
     * Deletes all moods from the file
     */
    void deleteAllMoods();
    /**
     * @return the highest id, stored in the file
     */
    int getMaxID();
    /**
     * closes the file connection safely - recommended!
     */
    void close();
}
