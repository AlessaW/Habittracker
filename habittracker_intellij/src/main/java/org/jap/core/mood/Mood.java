package org.jap.core.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Mood Object with all its glory and variables
 */
public class Mood
{
    private static final Logger log = LogManager.getLogger(Mood.class);
    
    // Variables
    private String name;
    private String description;
    private Vector2d moodLocation;
    private Color color;
    
    // Constructor
    /**
     * Calculates the color from the location
     * @param name         The name of the mood
     * @param description  The description of the mood
     * @param moodLocation XY-Coordinates in the moodDiagram
     */
    public Mood(String name, String description, Vector2d moodLocation) {
        this.name = name;
        this.description = description;
        this.moodLocation = moodLocation;
        this.color = calculateColor(moodLocation);
    }
    
    /**
     * @param name         The name of the mood
     * @param description  The description of the mood
     * @param moodLocation XY-Coordinates in the moodDiagram
     * @param color        The Color the mood should have
     */
    public Mood(String name, String description, Vector2d moodLocation, Color color) {
        this.name = name;
        this.description = description;
        this.moodLocation = moodLocation;
        this.color = color;
    }

    public Mood(Mood mood){
        this.name =mood.getName();
        this.description = mood.getDescription();
        this.moodLocation = mood.getMoodLocation();
        this.color = mood.getColor();
    }
    
    // Methods
    /**
     * calculating color from image coordinates
     */
    private Color calculateColor(Vector2d diagramLocation) {
        try {
            File file = new File("images/moodDiagram.png");
            BufferedImage image = ImageIO.read(file);
            // Getting pixel color by position x and y
//        int blue = imagecolor & 0xff;
//        int green = (imagecolor & 0xff00) >> 8;
//        int red = (imagecolor & 0xff0000) >> 16;
//        this.color = new java.awt.Color(red, green, blue);
            int imageColor = image.getRGB((int) diagramLocation.getX(), (int) diagramLocation.getY());
            return new Color(imageColor);
        } catch(IOException e){
            e.printStackTrace();
        }
        return Color.BLACK;
    }
    
    
    private void setName(String name) {
        this.name = name;
    }
    
    private void setDescription(String description) {
        this.description = description;
    }
    
    private void setMoodLocation(Vector2d moodLocation) {
        this.moodLocation = moodLocation;
    }
    
    private void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Vector2d getMoodLocation() {
        return new Vector2d(moodLocation);
    }

    public Color getColor() {
        return color;
    }
}
