package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Callable;

/*
    Created by nika
*/

public class DataListProvider implements Callable<ArrayList> {

    private ArrayList generatedList;


    @Override
    public ArrayList call() throws Exception {

        switch (timeModus) {
            case DAY -> generateDayList();
            case WEEK -> generateWeekList();
            //    case MONTH -> provideMonthList();
            //    case YEAR -> provideYearList();
        }
        return generatedList;
    }

    public enum StatTimeModus{
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    private static final Logger log = LogManager.getLogger(DataListProvider.class);
    private MoodManager moodManager;
    private ArrayList<MoodData> moodDataList = moodManager.getInstance().getMoods(); //Todo: muss noch auf MoodDataList zugreifen von MoodManager , immer nur holen wenn man sie braucht
    private StatTimeModus timeModus;

    private ArrayList<MoodData> weeklyList;

    public DataListProvider(StatTimeModus timeModus) {
        this.timeModus = timeModus;
    }//Todo: ist das richtig hier?

    public DataListProvider(StatTimeModus timeModus, MoodManager.Szenario s) {
        this.timeModus = timeModus;
        this.moodManager = new MoodManager(MoodManager.Szenario.TEST);
    }//Todo: ist das richtig hier?


    private void generateDayList() {
        //Todo: Erstellung Liste für days
        //reduzieren auf stündlich -> Ansicht unten stündlich
        // Moodvalues nicht zusammenrechnen, sondern als einzelne Punkte lassen
    }

    private void generateWeekList() {
        //Todo: Erstellung Liste für weeks

        int moodValueAgg = 0;
        int actLevelAgg = 0;
        int moodCounter = 0;
        int actCounter = 0;

        weeklyList = new ArrayList<MoodData>();


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
        generatedList = weeklyList;
    }

    private void provideMonthList(){
    }

    private void provideYearList(){
    }

    public ArrayList<MoodData> getList(){
        return generatedList; //Todo: was wird da übergeben? vllt new Array wl.add()
    }
}


