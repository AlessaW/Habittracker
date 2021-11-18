package org.jap.core.mood;

import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
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

    public Mood(String name, String description, Vector2d moodLocation) throws IOException {
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
    private void calculateColor() throws IOException {


        File file = new File("imagefile.png");
        BufferedImage image = ImageIO.read(file);
        // Getting pixel color by position x and y
        double imagecolor = image.getRGB((int)moodLocation.getX(), (int) moodLocation.getY());
        //this.color = new Color(imagecolor);


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
