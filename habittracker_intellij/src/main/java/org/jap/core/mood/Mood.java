package org.jap.core.mood;

import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.vecmath.Vector2d;

public class Mood
{
    private static final Logger log = LogManager.getLogger(Mood.class);
    String name = "";
    String description = "";
    Vector2d moodLocation = new Vector2d(0,0);
    Color color;

    public Mood(String name, String description, Vector2d moodLocation) {
        this.name = name;
        this.description = description;
        this.moodLocation = moodLocation;
        calculateColor();
    }

    private void calculateColor() {
    }
}
