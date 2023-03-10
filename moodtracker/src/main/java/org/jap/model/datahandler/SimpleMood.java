package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 * A Mood Object to be for saving and loading the user data
 * <br> - stores the mood data in a simple format
 * <br> - used by saveFileManagers
 */
record SimpleMood(int id, String name, String description, String timestamp, int moodValue, int activity) {
    private static final Logger log = LogManager.getLogger(SimpleMood.class);
    
    /**
     * @return A String of SimpleMood in a human-readable format
     */
    @Override
    public String toString() {
        return "DBMood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", moodValue=" + moodValue +
                ", activity=" + activity +
                '}';
    }
}
