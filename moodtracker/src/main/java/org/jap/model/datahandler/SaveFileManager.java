package org.jap.model.datahandler;

import java.util.ArrayList;

interface SaveFileManager {
    ArrayList<SimpleMood> loadMoods();
    void saveMoods(ArrayList<SimpleMood> moods);
    void saveMood(SimpleMood mood);
    void deleteMood(int id);
    void deleteAllMoods();
    int getMaxID();
    void close();
}
