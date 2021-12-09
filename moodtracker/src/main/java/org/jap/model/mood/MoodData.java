package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * MoodData is the class which holds all relevant fields and methods of a Mood.
 * MoodObjects are immutable //Todo: Sachen deswegen final machen?
 * Created by Jannika
 */

public class MoodData {
    private static final Logger log = LogManager.getLogger(MoodData.class);

    private String name;
    private String description;
    private int moodID;
    private int timeStamp; //Todo: which time format is good? Sys-Time as a default value on object creation
    private int activityLevel; //Todo: range from -10 to 10 useful?
    private int moodValue; //Todo: range from -10 to 10 useful?


}