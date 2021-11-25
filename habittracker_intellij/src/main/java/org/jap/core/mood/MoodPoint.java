package org.jap.core.mood;

import java.time.LocalDateTime;

/**
 * <p>TODO documentation</p>
 *
 * @author Alessa
 */
public class MoodPoint {

    private Mood mood;
    private LocalDateTime time;

    public MoodPoint(Mood mood, LocalDateTime time){
        this.mood = mood;
        this.time = time;
    }

    public Mood getMood() {
        return new Mood(this.mood);
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
