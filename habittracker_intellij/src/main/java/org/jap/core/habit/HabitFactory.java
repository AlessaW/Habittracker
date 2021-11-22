package org.jap.core.habit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 * produces all kinds of Habits
 */
public class HabitFactory {
    private static final Logger log = LogManager.getLogger(HabitFactory.class);
    
    // Variables
    
    // Methods
    public static IHabit createHabit(HabitType type, String name, String description){
        switch (type){
            case genericHabit: return new GenericHabit(name,description);
            // Uncomment if implemented:
//            case repeatedHabit: return new RepeatedHabit(name,description);
//            case multiHabit: return new MultiHabit(name,description);
            default:{
                throw new IllegalArgumentException("Type '" + type + "' is currently not supported!");
            }
        }
    }
}
