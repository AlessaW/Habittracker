package org.jap.model.datahandler;

import javafx.util.Pair;
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
import java.util.OptionalDouble;

/*
    Created by nika
*/

public class DataListProviderTest {
    private class MockMoodManager extends MoodManager{
        ArrayList<MoodData> moodDataList = new ArrayList<MoodData>();

        @Override
        public ArrayList<MoodData> getMoods(){

            moodDataList.add(new MoodData("Happy", "desc", LocalDateTime.of(2021, Month.DECEMBER, 27, 10,40), 5, 5));
            moodDataList.add(new MoodData("sad", "desc", LocalDateTime.of(2021, Month.DECEMBER, 28, 11, 40), 0, 0));
            moodDataList.add(new MoodData("conent", "desc", LocalDateTime.of(2021, Month.DECEMBER, 29, 11, 40), 3, 3));
            return moodDataList;
        }

        @Override
        protected void addMood(MoodData mood){
            moodDataList.add(mood);
        }

        public void deleteAll(){
            moodDataList.clear();
        }
    }

    private static final Logger log = LogManager.getLogger(DataListProviderTest.class);
    MockMoodManager mockMoodManager = new MockMoodManager();


    DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK);



    //Todo: Test Excetptions wenn Thread nicht anläuft

    @Test
    public void testWeekSingleDays() throws Exception{

            ArrayList expectedResult = new ArrayList<>();
            Pair pair1 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
            Pair pair2 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
            Pair pair3 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 29).atStartOfDay(), new Pair<>(3.0, 3.0));
            expectedResult.add(0, pair1);
            expectedResult.add(1, pair2);
            expectedResult.add(2, pair3);


        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, mockMoodManager);
        week.call();

        //Todo: Moods hinzufügen, damit ich berechnen kann, ob der richtige Wert rauskommt

        Assert.assertEquals(expectedResult, week.getList());
        //sum testen
        //Anzahl testen
    }

    @Test
    public void testWeekMultipleEntriesPerDay() throws Exception {
        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, mockMoodManager);

        mockMoodManager.moodDataList.clear();


        mockMoodManager.createMood("Happy", "desc", LocalDateTime.of(2021, Month.JUNE, 27, 10,40), 5, 5);
        mockMoodManager.createMood("sad", "desc", LocalDateTime.of(2021, Month.JUNE, 27, 11, 40), 5, 5);
        mockMoodManager.createMood("content", "desc", LocalDateTime.of(2021, Month.JUNE, 27, 11, 47), 5, 5);
        mockMoodManager.createMood("sad", "desc", LocalDateTime.of(2021, Month.JUNE, 28, 11, 40), 0, 0);
        mockMoodManager.createMood("content", "desc", LocalDateTime.of(2021, Month.JUNE, 29, 11, 47), 3, 3);


        week.call();

        ArrayList expectedResult = new ArrayList<>();
        Pair pair1 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair2 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
        Pair pair3 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 29).atStartOfDay(), new Pair<>(3.0, 3.0));
        expectedResult.add(0, pair1);
        expectedResult.add(1, pair2);
        expectedResult.add(2, pair3);

        Assert.assertEquals(expectedResult, week.getList());
    }

    @Test
    public void testMixedEntries() throws Exception {
        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, mockMoodManager);
        mockMoodManager.deleteAll();


        mockMoodManager.createMood("Happy", "desc", LocalDateTime.of(2021, Month.DECEMBER, 29, 10,40), 5, 5);
        mockMoodManager.createMood("sad", "desc", LocalDateTime.of(2021, Month.DECEMBER, 27, 11, 40), 5, 5);
        mockMoodManager.createMood("hi", "", LocalDateTime.of(1997, Month.MAY, 23, 11, 34), 2, 3);
        mockMoodManager.createMood("Test", "desc", LocalDateTime.of(2022, Month.JANUARY, 1, 11, 40), 5, 5);
        mockMoodManager.createMood("content", "desc", LocalDateTime.of(2021, Month.DECEMBER, 28, 11, 47), 5, 5);

        week.call();

        log.debug(mockMoodManager.getMoods());


        ArrayList expectedResult = new ArrayList<>();

        Pair pair1 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair2 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
        Pair pair3 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 29).atStartOfDay(), new Pair<>(3.0, 3.0));
        Pair pair4 = new Pair<LocalDateTime, Pair>(LocalDate.of(2022, Month.JANUARY, 1).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair5 = new Pair<LocalDateTime, Pair>(LocalDate.of(1997, Month.MAY, 23).atStartOfDay(), new Pair<>(2,3));
        expectedResult.add(0, pair5);
        expectedResult.add(1, pair1);
        expectedResult.add(2, pair2);
        expectedResult.add(3, pair3);
        expectedResult.add(4, pair4);

        Assert.assertEquals(expectedResult, week.getList());
    }


    //Test weekly
    //single days
    //multiple data entries on one day
    //fail: konnte nich ausgeführt werden
    //keine Data vorhanden

}
