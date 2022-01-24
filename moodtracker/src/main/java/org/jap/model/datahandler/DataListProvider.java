package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/*
    Created by nika
*/

public class DataListProvider implements Runnable {
    private static final Logger log = LogManager.getLogger(DataListProvider.class);
    public List<MoodData> moodDataList;
    private StatTimeModus timeModus;

    public DataListProvider(StatTimeModus timeModus) {
        this.timeModus = timeModus;
    }

    public enum StatTimeModus{
        DAY,
        WEEK,
        MONTH,
        YEAR
    }


    @Override
    public void run() {

        switch (timeModus) {
            case DAY -> provideDayList();
            case WEEK -> provideWeekList();
        //    case MONTH -> provideMonthList();
        //    case YEAR -> provideYearList();
        }
    }

    private void provideDayList() {
        //Todo: Erstellung Liste für days
        //reduzieren auf stündlich -> Ansicht unten stündlich
        // Moodvalues nicht zusammenrechnen, sondern als einzelne Punkte lassen
    }

    private ArrayList provideWeekList() {
        //Todo: Erstellung Liste für weeks

        int moodValueAgg = 0;
        int actLevelAgg = 0;
        int moodCounter = 0;
        int actCounter = 0;

        ArrayList<MoodData> weeklyList = new ArrayList<>();


        for (MoodData m : moodDataList) {
            LocalDate localDate = m.getTimeStamp().toLocalDate();

            ListIterator<MoodData> it = moodDataList.listIterator();

            while (it.hasNext()) {
                while(localDate.isEqual(it.next().getTimeStamp().toLocalDate())) {
                    moodValueAgg = m.getMoodValue() + it.next().getMoodValue();
                    log.debug("Moodvalue added weekly List" + moodValueAgg);
                    actLevelAgg = m.getActivityLevel() + it.next().getActivityLevel();
                    log.debug("ActivityValue added weekly List" + actLevelAgg);
                    moodCounter++;
                    log.debug(moodCounter);
                    actCounter++;
                    log.debug(actCounter);
                }
                weeklyList.add(new MoodData("", "", localDate.atStartOfDay(), (actLevelAgg/actCounter), (moodValueAgg/moodCounter)));
            }
        }
        return weeklyList;
    }

    private void provideMonthList(){
    }

    private void provideYearList(){
    }
}


