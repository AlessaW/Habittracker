package org.jap.model.datahandler;

import javafx.scene.control.cell.MapValueFactory;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.util.DataListProviderException;

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
    private ArrayList generatedMoodList;
    private ArrayList<Pair<LocalDateTime, Double>> generatedActivityList;


    @Override
    public ArrayList call() throws Exception {

        switch (timeModus) {
            case DAY -> generateDayList();
            case WEEK -> {
                generateWeekList();
                generateMoodList();
                generateActivityList();
            }
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
                     //  .sorted(Comparator.comparing(MoodData::getTimeStamp))
                        .parallel()
                        .collect(Collectors.groupingBy(it -> it.getTimeStamp().toLocalDate().atStartOfDay()))
                        .entrySet()
                        .stream()
                        .map((entry) -> {
                            var it = entry.getValue();
                            var avgMood = it.stream().mapToInt(MoodData::getMoodValue).average().getAsDouble();
                            var avgActivation = it.stream().mapToInt(MoodData::getActivityLevel).average().getAsDouble();
                            return new Pair<>(entry.getKey(), new Pair<>(avgMood, avgActivation));
                        }).sorted(Comparator.comparing(Pair::getKey));
           log.debug("groupedByDay generated");

        generatedList = new ArrayList<>(groupedByDay.sorted(Comparator.comparing(Pair::getKey)).toList());

    }

    private void generateMoodList() {

    /*    List<MoodData> moods = MoodManager.getInstance().getMoods().stream()
                .sorted(Comparator.comparing(MoodData::getTimeStamp))
//                .limit(500) // maybe we should add a limit...
                .toList();
        log.debug("moods generated");*/


        var groupedByDay =
                //moods
        moodDataList
                        .stream()
                        //  .sorted(Comparator.comparing(MoodData::getTimeStamp))
                        .parallel()
                        .collect(Collectors.groupingBy(it -> it.getTimeStamp().toLocalDate().atStartOfDay()))
                        .entrySet()
                        .stream()
                        .map((entry) -> {
                            var it = entry.getValue();
                            var avgMood = it.stream().mapToInt(MoodData::getMoodValue).average().getAsDouble();
                            return new Pair<>(entry.getKey(), avgMood);
                        }).sorted(Comparator.comparing(Pair::getKey));
        log.debug("groupedByDay generated");

        generatedMoodList = new ArrayList<>(groupedByDay.toList());
    }

    private void generateActivityList() {

        List<MoodData> moods = MoodManager.getInstance().getMoods().stream()
                .sorted(Comparator.comparing(MoodData::getTimeStamp))
//                .limit(500) // maybe we should add a limit...
                .toList();
        log.debug("moods generated");


        var groupedByDay =
                moods
                        .stream()
                        //  .sorted(Comparator.comparing(MoodData::getTimeStamp))
                        .parallel()
                        .collect(Collectors.groupingBy(it -> it.getTimeStamp().toLocalDate().atStartOfDay()))
                        .entrySet()
                        .stream()
                        .map((entry) -> {
                            var it = entry.getValue();
                            var avgAct = it.stream().mapToInt(MoodData::getActivityLevel).average().getAsDouble();
                            return new Pair<>(entry.getKey(), avgAct);
                        });
        log.debug("groupedByDay generated");

        generatedActivityList = new ArrayList<>(groupedByDay.toList());
    }

    private void provideMonthList() {
    }

    private void provideYearList() {
    }

    public ArrayList<MoodData> getList() throws DataListProviderException{
        //return reverseList(generatedList);
        if(null == generatedList){
            throw new DataListProviderException("GeneratedList is null");
        }
        for (Object p: generatedList
             ) {
            System.out.println(p.toString());

        }
        return generatedList;
    }

    private ArrayList reverseList(ArrayList list){
        for (int k = 0, j = list.size() - 1; k < j; k++)
        {
            list.add(k, list.remove(j));
        }
        return list;
    }

    public ArrayList getGeneratedMoodList() {
        return generatedMoodList;
    }

    public ArrayList getGeneratedActivityList(){
        return generatedActivityList;
    }
}


