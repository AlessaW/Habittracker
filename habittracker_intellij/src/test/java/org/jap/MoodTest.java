package org.jap;

import org.jap.core.mood.Mood;
import org.junit.Assert;
import org.junit.Test;

import javax.vecmath.Vector2d;
import java.awt.*;

/**
 * <p>TODO documentation</p>
 *
 * @author Alessa
 */
public class MoodTest {

    @Test
    public void TestMoodCreation() {
        Mood testMood = new Mood("myMood", "", new Vector2d(100,100));
        Assert.assertEquals(testMood.getColor(), new Color(0x00FF00));
    }
}
