package org.jap.model.datahandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.util.DataListProviderException;
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
    private class MockMoodManager extends MoodManager {
        ObservableList<MoodData> moodDataList = FXCollections.observableList(new ArrayList<>());

        @Override
        public ObservableList<MoodData> getMoods() {
            moodDataList.clear();
            moodDataList.add(new MoodData("Happy", "desc", LocalDateTime.of(2021, Month.DECEMBER, 27, 10, 40), 5, 5));
            moodDataList.add(new MoodData("sad", "desc", LocalDateTime.of(2021, Month.DECEMBER, 28, 11, 40), 0, 0));
            moodDataList.add(new MoodData("conent", "desc", LocalDateTime.of(2021, Month.DECEMBER, 29, 11, 40), 3, 3));

            moodDataList.add(new MoodData("Test", "desc", LocalDateTime.of(2022, Month.JANUARY, 1, 11, 40), 5, 5));
            moodDataList.add(new MoodData("sad", "desc", LocalDateTime.of(2021, Month.JUNE, 28, 11, 40), 0, 0));
            moodDataList.add(new MoodData("content", "desc", LocalDateTime.of(2021, Month.JUNE, 29, 11, 47), 3, 3));

            moodDataList.add(new MoodData("Happy", "desc", LocalDateTime.of(2021, Month.JUNE, 27, 10, 40), 5, 5));
            moodDataList.add(new MoodData("sad", "desc", LocalDateTime.of(2021, Month.JUNE, 27, 11, 40), 5, 5));
            moodDataList.add(new MoodData("content", "desc", LocalDateTime.of(2021, Month.JUNE, 27, 11, 47), 5, 5));

            for (MoodData m : moodDataList
            ) {
                System.out.println(m.getTimeStamp());
            }

            return moodDataList;
        }

  /*      @Override
        protected void addMood(MoodData mood){
            moodDataList.add(mood);
        }

        public void deleteAll(){
            moodDataList.clear();
        }*/
    }

    private static final Logger log = LogManager.getLogger(DataListProviderTest.class);

    //Todo: Test Excetptions wenn Thread nicht anläuft

    @Test
    public void testWeekMoodList_singleDays() throws Exception {
        //Todo: MoodList testen
    }

    @Test
    public void testWeekActivityList_singleDays() throws Exception {
        //Todo: ActivityList testen
    }

    @Test
    public void testWeekSingleDays() throws Exception, DataListProviderException {
        MockMoodManager mockMoodManager = new MockMoodManager();

        ArrayList expectedResult = new ArrayList<>();
        Pair pair4 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair5 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
        Pair pair6 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 29).atStartOfDay(), new Pair<>(3.0, 3.0));

        Pair pair1 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair2 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
        Pair pair3 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 29).atStartOfDay(), new Pair<>(3.0, 3.0));

        Pair pair7 = new Pair<LocalDateTime, Pair>(LocalDate.of(2022, Month.JANUARY, 1).atStartOfDay(), new Pair(5.0, 5.0));

        expectedResult.add(0, pair1);
        expectedResult.add(1, pair2);
        expectedResult.add(2, pair3);
        expectedResult.add(3, pair4);
        expectedResult.add(4, pair5);
        expectedResult.add(5, pair6);
        expectedResult.add(6, pair7);


        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, mockMoodManager);
        week.call();

        Assert.assertEquals(expectedResult, week.getList());
    }

    @Test
    public void testWeekMultipleEntriesPerDay() throws Exception, DataListProviderException {
     /*   MockMoodManager mockMoodManager = new MockMoodManager();

        ArrayList expectedResult = new ArrayList<>();

        expectedResult.add(0, pair1);
        expectedResult.add(1, pair2);
        expectedResult.add(2, pair3);

        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, mockMoodManager);
        week.call();

        Assert.assertEquals(expectedResult, week.getList());*/
    }

    @Test
    public void testMixedEntries() throws Exception, DataListProviderException {
        MockMoodManager mockMoodManager = new MockMoodManager();
        DataListProvider week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, mockMoodManager);

        week.call();

        ArrayList expectedResult = new ArrayList<>();
        Pair pair4 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair5 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
        Pair pair6 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.DECEMBER, 29).atStartOfDay(), new Pair<>(3.0, 3.0));

        Pair pair1 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 27).atStartOfDay(), new Pair<>(5.0, 5.0));
        Pair pair2 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 28).atStartOfDay(), new Pair<>(0.0, 0.0));
        Pair pair3 = new Pair<LocalDateTime, Pair>(LocalDate.of(2021, Month.JUNE, 29).atStartOfDay(), new Pair<>(3.0, 3.0));

        Pair pair7 = new Pair<LocalDateTime, Pair>(LocalDate.of(2022, Month.JANUARY, 1).atStartOfDay(), new Pair(5.0, 5.0));

        expectedResult.add(0, pair1);
        expectedResult.add(1, pair2);
        expectedResult.add(2, pair3);
        expectedResult.add(3, pair4);
        expectedResult.add(4, pair5);
        expectedResult.add(5, pair6);
        expectedResult.add(6, pair7);

        Assert.assertEquals(expectedResult, week.getList());
    }

    //Todo: fail: konnte nich ausgeführt werden
    //Todo: keine Data vorhanden

}
