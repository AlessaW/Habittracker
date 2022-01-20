package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;

import java.time.LocalDateTime;
import java.util.List;

/*
    Created by nika
*/

public class DataListProvider implements Runnable {
    public List<MoodData> moodDataList;

    public enum StatTimeModus{
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    @Override
    public void run() {

        switch (StatTimeModus) {
            case StatTimeModus.DAY -> provideDayList();
            case StatTimeModus.WEEK -> provideWeekList();
            case StatTimeModus.MONTH -> provideMonthList();
            case StatTimeModus.YEAR -> provideYearList();
        }
    }

    private void provideDayList() {
        //Todo: Erstellung Liste für days
        //reduzieren auf stündlich -> Ansicht unten stündlich
        // Moodvalues nicht zusammenrechnen, sondern als einzelne Punkte lassen
    }

    private void provideWeekList() {
        //Todo: Erstellung Liste für weeks
        //Zusammenrechnung Moodvalues
        //stream machen?
        // reduzieren auf einzelnen Datenpunkt pro Tag
        // nur einen Tag zurückgeben
        for (MoodData m:moodDataList) {
            moodDataList.
           //LocalDateTime timeStamp zu LocalDate
            //if Localdate t1.equals(t2){
                //add all moodValues
                //add all activationlevels
            //make new List mit allen neuen Werten
            //return neue Liste
        }
    }

    private void provideMonthList() {
    }

    private void provideYearList() {
    }
}

}

