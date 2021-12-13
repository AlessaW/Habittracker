package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

/**
 * MoodData is the class which holds all relevant fields and methods of a Mood.
 * MoodObjects are immutable //Todo: Sachen deswegen final machen?
 *
 *
 * Created by Jannika
 */

public class MoodData {
    private static final Logger log = LogManager.getLogger(MoodData.class);

    private String name;
    private String description;
    private int moodID;
    private LocalDateTime timeStamp; //Todo: which time format is good? Sys-Time as a default value on object creation
    private int activityLevel; //Todo: range from -10 to 10 useful?
    private int moodValue; //Todo: range from -10 to 10 useful?

    public MoodData(String name, String description, int moodID, LocalDateTime timeStamp, int activityLevel, int moodValue) {
        this.name = name;
        this.description = description;
        this.moodID = moodID;
        this.timeStamp = timeStamp;
        this.activityLevel = activityLevel;
        this.moodValue = moodValue;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMoodID() {
        return moodID;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getMoodValue() {
        return moodValue;
    }
}