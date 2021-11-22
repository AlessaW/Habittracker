package org.jap.core.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public Mood(String name, String description, Vector2d moodLocation, Color color) {
        this.name = name;
        this.description = description;
        this.moodLocation = moodLocation;
        this.color = color;
    }

    /**
     * calculating color from image coordinates
     * @throws IOException
     */
    private void calculateColor() {

        try {
            File file = new File("../../../../main/resources/images/colorImage.png");
            BufferedImage image = ImageIO.read(file);
            // Getting pixel color by position x and y
            int imagecolor = image.getRGB((int) moodLocation.getX(), (int) moodLocation.getY());
            int blue = imagecolor & 0xff;
            int green = (imagecolor & 0xff00) >> 8;
            int red = (imagecolor & 0xff0000) >> 16;
            this.color = new Color(red, green, blue);
        } catch(IOException e){
            e.printStackTrace();
        }

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
}
