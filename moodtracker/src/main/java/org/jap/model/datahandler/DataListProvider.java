package org.jap.model.datahandler;

import javafx.scene.control.cell.MapValueFactory;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public enum StatTimeModus {
        DAY,
        WEEK,
        MONTH,
        YEAR
    }

    private static final Logger log = LogManager.getLogger(DataListProvider.class);
    private MoodManager moodManager;
    private ArrayList<MoodData> moodDataList;  //Todo: muss noch auf MoodDataList zugreifen von MoodManager , immer nur holen wenn man sie braucht
    private StatTimeModus timeModus;


    public DataListProvider(StatTimeModus timeModus) {
        this.timeModus = timeModus;
    }//Todo: ist das richtig hier?

    public DataListProvider(StatTimeModus timeModus, MoodManager moodManager) {
        this.timeModus = timeModus;
        this.moodManager = moodManager;
        this.moodDataList = moodManager.getMoods();
    }//Todo: ist das richtig hier?


    private void generateDayList() {
        //Todo: Erstellung Liste für days
        //reduzieren auf stündlich -> Ansicht unten stündlich
        // Moodvalues nicht zusammenrechnen, sondern als einzelne Punkte lassen
    }

    private void generateWeekList() {
           var groupedByDay =
                moodDataList
                        .stream()
                        .parallel()
                        .sorted(Comparator.comparing(MoodData::getTimeStamp))
                        .collect(Collectors.groupingBy(it -> it.getTimeStamp().toLocalDate().atStartOfDay()))
                        .entrySet()
                        .stream()
                        .map((entry) -> {
                            var it = entry.getValue();
                            var avgMood = it.stream().mapToInt(MoodData::getMoodValue).average().getAsDouble();
                            var avgActivation = it.stream().mapToInt(MoodData::getActivityLevel).average().getAsDouble();
                            return new Pair<>(entry.getKey(), new Pair<>(avgMood, avgActivation));
                        });

        generatedList = new ArrayList<>(groupedByDay.toList());

    }

    private void provideMonthList() {
    }

    private void provideYearList() {
    }

    public ArrayList<MoodData> getList() {

        return reverseList(generatedList);
    }

    private ArrayList reverseList(ArrayList list){
        for (int k = 0, j = list.size() - 1; k < j; k++)
        {
            list.add(k, list.remove(j));
        }
        return list;
    }
}


