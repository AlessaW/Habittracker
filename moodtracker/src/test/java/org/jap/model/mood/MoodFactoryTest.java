package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class MoodFactoryTest {

    private static final Logger log = LogManager.getLogger(MoodFactoryTest.class);

    @Test
    public void testStandardMoodCreation(){
        MoodData mood = MoodFactory.createStandardMood("happy", LocalDateTime.of(2021, Month.JULY, 29, 6, 0));
        Assert.assertNotNull(mood);
        Assert.assertEquals(mood.getName(), "happy");
        Assert.assertEquals(mood.getDescription(), "");
        Assert.assertEquals(mood.getActivityLevel(), 100);
        Assert.assertEquals(mood.getMoodValue(), 100);


    }
}
