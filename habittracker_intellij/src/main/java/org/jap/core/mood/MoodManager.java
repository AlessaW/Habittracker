package org.jap.core.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages ALL the moods
 */
public class MoodManager
{
    private static final Logger log = LogManager.getLogger(MoodManager.class);

    /**
     * MoodFactory Method
     *
     * TODO add different Standard Moods
     */
    public static Mood createStandardMood(String name){
        switch(name){
            case "happy":
                return new Mood(name, "", new Vector2d(100, 100), Color.YELLOW);

                
            default: throw new RuntimeException("Not a Standard Mood");
        }
    }
    
    
    private final List<MoodPoint> moods = new ArrayList<>();

    /**
     * @param mood the new Mood
     */
    public void add(MoodPoint mood){
        moods.add(mood);
    }

    /**
     * @return a copy of the Moods
     */
    public List<MoodPoint> getListCopy() {
        return new ArrayList<>(moods);
    }


    public void setMood(){

    }

    public void askMood(){

    }

    public void openMoodStats(){

    }

    
}
