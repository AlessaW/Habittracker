package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
    Created by Peter
*/

/**
 * Manages the Database Content using SqliteAccess
 * written to specificly match our projects requirements
 */
class SqliteManager implements SaveFileManager {
    private static final Logger log = LogManager.getLogger(SqliteManager.class);
    
    // Variables
    private final SqliteAccess db;
    
    // Constructor
    /**
     * @param DB database name to connect to / to create
     */
    public SqliteManager(String DB) {
        db = new SqliteAccess(DB);
        initDB();
        log.debug("Datenbank verbunden: " + DB);
    }
    
    // Methods
    private void initDB() {
        // create table for saved MoodData
        String sql = """
                CREATE TABLE "MoodData" (
                    "id" INTEGER PRIMARY KEY,
                    "name" TEXT NOT NULL,
                    "description" TEXT NOT NULL,
                    "timestamp" INTEGER NOT NULL,
                    "moodValue" INTEGER NOT NULL,
                    "activity" INTEGER NOT NULL
                )
            """
        ;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.trace(e);
    }
    
    @Override
    public ArrayList<SimpleMood> loadMoods() {
        ArrayList<SimpleMood> list = new ArrayList<>();
        
        String sql = "SELECT * FROM \"MoodData\"";
        log.trace(sql);
        ResultSet rs = db.read(sql);
        try {
            while (rs.next())
            {
                SimpleMood m = new SimpleMood(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("timestamp"),
                    rs.getInt("moodValue"),
                    rs.getInt("activity")
                );
                log.trace(m.toString());
                list.add(m);
            }
        } catch (SQLException ignored) {}
        return list;
    }
    
    @Override
    public void saveMoods(ArrayList<SimpleMood> moods) {
        for (SimpleMood mood : moods) {
            saveMood(mood);
        }
    }
    
    @Override
    public void saveMood(SimpleMood mood) {
        boolean exists = moodExists(mood);
        log.trace("id: "+mood.id()+" exists: "+exists);
        if (exists) {
            updateMood(mood);
        } else {
            newMood(mood);
        }
    }
    
    @Override
    public void deleteMood(int id) {
        String sql = "DELETE FROM \"MoodData\" WHERE \"id\"="+id;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
    }
    
    @Override
    public void deleteAllMoods() {
        String sql = "DROP TABLE \"MoodData\"";
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
        
        initDB();
    }
    
    @Override
    public int getMaxID() {
        int maxID = 0;
        try {
            String sql = "SELECT MAX(\"id\") FROM \"MoodData\"";
            log.trace(sql);
            maxID = db.read(sql).getInt(1);
        } catch (SQLException e) {
            log.trace(e.getMessage());
        }
        return maxID;
    }
    
    @Override
    public void close() {
        db.closeDB();
    }
    
    private boolean moodExists(SimpleMood mood) {
        return moodExists(mood.id());
    }
    
    private boolean moodExists(int id) {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM \"MoodData\" WHERE \"id\" = "+id;
            log.trace(sql);
            count = db.read(sql).getInt(1);
        } catch (SQLException e) {
            log.trace(e.getMessage());
        }
        return count > 0;
    }
    
    private void newMood(SimpleMood mood) {
        String sql = "INSERT INTO \"MoodData\" VALUES (" +
                mood.id()+","+
                "'"+mood.name()+"',"+
                "'"+mood.description()+"',"+
                "'"+mood.timestamp()+"',"+
                mood.moodValue()+","+
                mood.activity()+
            ")"
        ;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
    }
    
    private void updateMood(SimpleMood mood) {
        String sql = "UPDATE Vokabelliste SET" +
            "\"name\"='"        +mood.name()+"',"+
            "\"description\"='" +mood.description()+"',"+
            "\"timestamp\"='"    +mood.timestamp()+"',"+
            "\"moodValue\"="    +mood.moodValue()+","+
            "\"activity\"="     +mood.activity()+" "+
            
            "WHERE \"id\"="     +mood.id()
        ;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
    }
}
