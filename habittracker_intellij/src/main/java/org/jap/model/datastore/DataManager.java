package org.jap.model.datastore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.*;
//Todo: Interface Verknüpfung: wie macht man das?
//Todo: DataManager besser in Controller-Package?
//Todo: warum datastore und DataManager?
//Todo: DataManager als Ebene zwischen Model und Speicherung, damit man es auf verschiedene Arten speichern kann -> Dann muss man nur DataManager anpassen

/**
 * Class which manages Habit and Mood Objects
 * Storage, Update, Deletion
 * <p>
 * Created by Jannika
 */


public class DataManager {
    private static final Logger log = LogManager.getLogger(DataManager.class);


    /**
     * Manages Habit data
     */
    public void saveHabit(Habit habit) {
    } //todo: implement

    public void updateHabit(Habit habit) {
    } //Todo: makes a new object habit -> overwrites the old one

    public void deleteHabit(Habit habit) {
    }
    /**
     * Manages Mood data
     */
    public void saveMood(Mood mood) {
    }

    public void updateMood(Mood mood) {
    } //Todo: makes a new object Mood -> overwrites the old one

    public void deleteMood(Mood mood) {
    }
}