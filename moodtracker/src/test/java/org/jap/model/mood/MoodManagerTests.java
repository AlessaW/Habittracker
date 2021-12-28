package org.jap.model.mood;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

/**
 * <p>TODO documentation</p>
 *
 * @author Alessa
 */
public class MoodManagerTests {

    @Test
    public void testMoodCreation(){
        MoodManager.getInstance().createMood("confused", "really don't understand anything right now", LocalDateTime.of(2021, Month.DECEMBER, 27, 10,40), 10, -10);
        Optional<MoodData> prototype = MoodManager.getInstance().getMoods().stream().filter(m -> m.getName().equals("confused")).findFirst();

        Assert.assertTrue(prototype.isPresent());
        MoodData mood = prototype.get();
        Assert.assertEquals(mood.getName(), "confused");
        Assert.assertEquals(mood.getDescription(), "really don't understand anything right now");
        Assert.assertEquals(mood.getTimeStamp(), LocalDateTime.of(2021, Month.DECEMBER, 27, 10,40));
        Assert.assertEquals(mood.getActivityLevel(), 10);
        Assert.assertEquals(mood.getMoodValue(), -10);
    }
}
