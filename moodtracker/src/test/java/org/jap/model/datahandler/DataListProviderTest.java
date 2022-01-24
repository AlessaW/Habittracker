package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/*
    Created by nika
*/

public class DataListProviderTest {
    private static final Logger log = LogManager.getLogger(DataListProviderTest.class);

    DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK);

    //Todo: Test Excetptions wenn Thread nicht anläuft


    //give "fake" moodDatalist


    @Test
    public void testWeekSingleDays() {
        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK);
        week.run();

        List<MoodData> moodDataList = new ArrayList<>();
        ArrayList<MoodData> expectedResult = new ArrayList<>();
        MoodManager moodManager = new MoodManager();

        moodDataList.add(moodManager.createMood("Happy", "desc", LocalDateTime.of(2021, Month.DECEMBER, 27, 10,40), 5, 5));
        expectedResult.add(moodManager.createMood("", "", LocalDate.of(2021, Month.DECEMBER, 27).atStartOfDay(), 5, 5));
         //Todo: Moods hinzufügen, damit ich berechnen kann, ob der richtige Wert rauskommt

        Assert.assertEquals(expectedResult, week.getWeeklyList());
        //sum testen
        //Anzahl testen
    }

    @Test
    public void testWeekMultipleEntriesPerDay(){

    }

    @Test
    public void testMixedEntries(){

    }


    //Test weekly
    //single days
    //multiple data entries on one day
    //fail: konnte nich ausgeführt werden
    //keine Data vorhanden

}
