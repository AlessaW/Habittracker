package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;

import java.time.LocalDateTime;
import java.util.ArrayList;

/*
    Created by Peter
*/
public class dbTest {
    private static final Logger log = LogManager.getLogger(dbTest.class);
    
    private static DataManager data;
    
    public static void main(String[] args) {
        data = new DataManager();
        data.deleteAllMoods();
        
        debugList();
        
        // Example Data
        data.saveMood(new MoodData("hij","hvc", LocalDateTime.now(),15,21));
        data.saveMood(new MoodData("ntr","oih", LocalDateTime.now(),13,18));
        data.saveMood(new MoodData("csd","def", LocalDateTime.now(),11,22));
        data.saveMood(new MoodData("gdf","jgh", LocalDateTime.now(),5,7));
        data.saveMood(new MoodData("ghj","dfg", LocalDateTime.now(),19,14));
        data.saveMood(new MoodData(1,"abc","jdg", LocalDateTime.now(),10,(int) (Math.random()*21)));
        data.saveMood(new MoodData(2,"asd","ztn", LocalDateTime.now(),12,(int) (Math.random()*21)));
        data.saveMood(new MoodData(9,"asd","fgh", LocalDateTime.now(),20,(int) (Math.random()*21)));
        
        debugList();
    }
    
    private static void debugList() {
        ArrayList<MoodData> moods = data.loadMoods();
        if (moods.size() == 0)
            log.debug("no moods saved");
        for (MoodData m : moods) {
            log.debug(m.toString());
        }
    }
}
