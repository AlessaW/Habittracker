package org.jap.core.habit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericHabit implements IHabit
{
    private static final Logger log = LogManager.getLogger(GenericHabit.class);
    
    // Variables
    private final String name;
    private final String description;
    
    //Constructor
    public GenericHabit(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    // Methods
    /**
     * @return the name of the Habit
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * @return the description of the habit
     */
    @Override
    public String getDescription() {
        return description;
    }
}
