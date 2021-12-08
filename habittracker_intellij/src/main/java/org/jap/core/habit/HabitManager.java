package org.jap.core.habit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages ALL the habits
 */
public class HabitManager
{
    private static final Logger log = LogManager.getLogger(HabitManager.class);
    
    // Variables
    private final List<IHabit> habits = new ArrayList<>();
    
    // Methods
    /**
     * @param habit the new Habit
     */
    public void add(IHabit habit){
        habits.add(habit);
    }
    
    /**
     * @return a copy of the Habits
     */
    public List<IHabit> getListCopy() {
        return new ArrayList<>(habits);
    }
}
