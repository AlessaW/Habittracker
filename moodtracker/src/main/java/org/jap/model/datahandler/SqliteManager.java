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
    
    private static final String TABLE_NAME = "MoodData";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TIMESTAMP = "timestamp";
    private static final String MOOD_VALUE = "moodValue";
    private static final String ACTIVITY = "activity";
    
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
        String sql =
                "CREATE TABLE "+toIdentifier(TABLE_NAME)+"("+
                    toIdentifier(ID)            +" INTEGER PRIMARY KEY,"+
                    toIdentifier(NAME)          +" TEXT NOT NULL,"+
                    toIdentifier(DESCRIPTION)   +" TEXT NOT NULL,"+
                    toIdentifier(TIMESTAMP)     +" TEXT NOT NULL,"+
                    toIdentifier(MOOD_VALUE)    +" INTEGER NOT NULL,"+
                    toIdentifier(ACTIVITY)      +" INTEGER NOT NULL"+
                ")";
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.trace(e);
    }
    
    @Override
    public ArrayList<SimpleMood> loadMoods() {
        ArrayList<SimpleMood> list = new ArrayList<>();
        
        String sql = "SELECT * FROM "+toIdentifier(TABLE_NAME);
        log.trace(sql);
        ResultSet rs = db.read(sql);
        try {
            while (rs.next())
            {
                SimpleMood m = new SimpleMood(
                    rs.getInt(ID),
                    rs.getString(NAME),
                    rs.getString(DESCRIPTION),
                    rs.getString(TIMESTAMP),
                    rs.getInt(MOOD_VALUE),
                    rs.getInt(ACTIVITY)
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
        String sql = "DELETE FROM "+toIdentifier(TABLE_NAME)+" WHERE "+toIdentifier(ID)+"="+id;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
    }
    
    @Override
    public void deleteAllMoods() {
        String sql = "DROP TABLE "+toIdentifier(TABLE_NAME);
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
            String sql = "SELECT MAX("+toIdentifier(ID)+") FROM "+toIdentifier(TABLE_NAME);
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
            String sql = "SELECT COUNT(*) FROM "+toIdentifier(TABLE_NAME)+" WHERE "+toIdentifier(ID)+"="+id;
            log.trace(sql);
            count = db.read(sql).getInt(1);
        } catch (SQLException e) {
            log.trace(e.getMessage());
        }
        return count > 0;
    }
    
    private void newMood(SimpleMood mood) {
        String sql = "INSERT INTO "+toIdentifier(TABLE_NAME)+" VALUES (" +
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
        String sql = "UPDATE "+toIdentifier(TABLE_NAME)+" SET" +
            toIdentifier(NAME)          +"='" +mood.name()          +"',"+
            toIdentifier(DESCRIPTION)   +"='" +mood.description()   +"',"+
            toIdentifier(TIMESTAMP)     +"='" +mood.timestamp()     +"',"+
            toIdentifier(MOOD_VALUE)    +"="  +mood.moodValue()     +","+
            toIdentifier(ACTIVITY)      +"="  +mood.activity()      +" "+
            
            "WHERE "+toIdentifier(ID)   +"="  +mood.id()
        ;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
    }
    
    private static String toIdentifier(String identifier) {
        return "\""+identifier+"\"";
    }
}
