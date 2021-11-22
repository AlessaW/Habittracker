package org.jap.core.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages ALL the moods
 */
public class MoodManager
{
    private static final Logger log = LogManager.getLogger(MoodManager.class);
    
    private final List<Mood> moods = new ArrayList<>();
    
    /**
     * @param mood the new Mood
     */
    public void add(Mood mood){
        moods.add(mood);
    }
    
    /**
     * @return a copy of the Moods
     */
    public List<Mood> getListCopy() {
        return new ArrayList<>(moods);
    }
}
