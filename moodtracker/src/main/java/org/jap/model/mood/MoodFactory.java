package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * FactoryClass creates object moods from json file
 *
 * Created by Jannika
 *
 */

public class MoodFactory {
    private static final Logger log = LogManager.getLogger(MoodFactory.class);

    private static ArrayList<MoodData> standardMoods;

    public static MoodData createStandardMood(String type){

        return null;
    }

    private void loadFromJSONFile(){

    }
}