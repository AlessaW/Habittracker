package org.jap.model.datahandler;

import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO documentation</p>
 *
 * @author Alessa
 */
public class DbTest {

    @Test
    public void savingMoodsTest(){

        DataManager data = new DataManager();
        data.deleteAllMoods();

        LocalDateTime time = LocalDateTime.now();

        // Example Data
        data.saveMood(MoodManager.getInstance().createMood("hij", "hvc", time, 15, 21));
        data.saveMood(MoodManager.getInstance().createMood("ntr", "oih", time, 13, 18));

        List<MoodData> moods = data.loadMoods();

        MoodData mood = moods.get(0);
        Assert.assertEquals("hij", mood.getName());
        Assert.assertEquals("hvc", mood.getDescription());
        Assert.assertEquals(time, mood.getTimeStamp());
        Assert.assertEquals(15, mood.getActivityLevel());
        Assert.assertEquals(21, mood.getMoodValue());

        ArrayList<MoodData> exampleList = new ArrayList<MoodData>();
        exampleList.add(MoodManager.getInstance().createMood("exampleMood", "", time, 5, -1));
        exampleList.add(MoodManager.getInstance().createMood("exampleMood2", "", time, 2, 1));
        exampleList.add(MoodManager.getInstance().createMood("exampleMood3", "", time, -3, -5));

        data.saveMoods(exampleList);

        moods = data.loadMoods();
        Assert.assertEquals(moods.size(), 5);


    }

    @Test
    public void testDeletingMoods(){
        DataManager data = new DataManager();
        data.deleteAllMoods();

        Assert.assertEquals(data.loadMoods().size(), 0);

        LocalDateTime time = LocalDateTime.now();

        MoodData mood = MoodManager.getInstance().createMood("hij", "hvc", time, 15, 21);

        data.saveMood(mood);

        Assert.assertEquals(data.loadMoods().size(), 1);
        data.deleteMood(mood);
        Assert.assertEquals(data.loadMoods().size(), 0);
    }
}
