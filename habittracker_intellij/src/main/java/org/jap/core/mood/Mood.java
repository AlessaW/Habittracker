package org.jap.core.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.vecmath.Vector2d;

public class Mood
{
    private static final Logger log = LogManager.getLogger(Mood.class);
    String name = "";
    String description = "";
    Vector2d moodLocation = new Vector2d(0,0);
}
