package org.jap.model.mood;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.util.MiaUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * FactoryClass creates object moods from json file
 * <p>
 * Created by Jannika
 */

public class MoodFactory {
    private static final Logger log = LogManager.getLogger(MoodFactory.class);

    private static final List<MoodData> standardMoods = new ArrayList<>();


    static {
        loadFromJSONFile();
    }

    /**
     * creates a mood based on saved standard Moods
     * @param type the name of the mood you want to create
     * @param timeStamp the time for the mood
     * @return a mood or null if type does not match any standard mood
     */
    public static MoodData createStandardMood(String type, LocalDateTime timeStamp) {
        Optional<MoodData> mood = standardMoods.stream().filter(m -> m.getName().equals(type.toLowerCase(Locale.ROOT))).findFirst();

        if (mood.isPresent()) {
            MoodData prototype = mood.get();
            log.debug("found standard mood: " + type);
            return new MoodData(prototype.getMoodID(),
                    prototype.getName(),
                    prototype.getDescription(),
                    timeStamp,
                    prototype.getActivityLevel(),
                    prototype.getMoodValue());
        }
        log.warn("couldn't find standard mood: " + type);
        return null;
    }

    /**
     * reloads the standard moods in case they were changed
     */
    public static void reloadStandardMoods() {
        log.info("reloading standard Moods");
        standardMoods.clear();
        loadFromJSONFile();
    }


    private static void loadFromJSONFile() {
        try {
            String content = Files.readString(MiaUtils.GetResourcesDirectory().resolve("standardMoods.json"));
            JSONArray array = new JSONArray(content);
            for (Object element : array) {
                if (element instanceof JSONObject) {
                    JSONObject object = (JSONObject) element;
                    MoodData mood = new MoodData(
                            object.getString("name").toLowerCase(Locale.ROOT),
                            object.getString("description"),
                            LocalDateTime.MIN,
                            object.getInt("activityLevel"),
                            object.getInt("moodValue"));
                    standardMoods.add(mood);
                }
            }
        } catch (IOException e) {
            log.fatal("wasn't able to read file", e);
        } catch (JSONException ex) {
            log.fatal("JSON malformed", ex);
        }
    }
}