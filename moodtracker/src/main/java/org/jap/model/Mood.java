package org.jap.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by nika
*/

public class Mood {
    private static final Logger log = LogManager.getLogger(Mood.class);

    private String name;
    private String description;

    /**
     * @param name         The name of the mood
     * @param description  The description of the mood
     *
     */
    public Mood(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Mood(Mood mood){
        this.name =mood.getName();
        this.description = mood.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
