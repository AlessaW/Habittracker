package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    // Constant names for the database identifiers
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
    /**
     * Initializes the database
     * <br> - creates a table for the MoodData
     */
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
    
    /**
     * Loads all MoodData from the database
     * @return the loaded moods as ArrayList of SimpleMood
     */
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
    
    /**
     * Saves one mood to the database
     * <br> - note that it overrides moods if the same id already exists in the database
     * @param mood The mood to save as SimpleMood
     */
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
    
    /**
     * Saves multiple moods to the database
     * <br> - note that it overrides moods if the same id already exists in the database
     * @param moods The list of moods to save as ArrayList of SimpleMood
     */
    @Override
    public void saveMoods(List<SimpleMood> moods) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(toIdentifier(TABLE_NAME)).append(" VALUES ");
        for (int i=0; i<moods.size();i++) {
            SimpleMood mood = moods.get(i);
            sql.append("(")
                    .append(mood.id()).append(",").append("'")
                    .append(mood.name()).append("',")
                    .append("'").append(mood.description()).append("',")
                    .append("'").append(mood.timestamp()).append("',")
                    .append(mood.moodValue()).append(",")
                    .append(mood.activity()).append(")");
            if (i<moods.size()-1)
                sql.append(",");
            else
                sql.append(";");
        }
        log.trace(sql.toString());
        String e = db.change(sql.toString());
        if (e != null)
            log.debug(e);
    }
    
    /**
     * Deletes a mood from the database
     * @param id the id of the mood to delete
     */
    @Override
    public void deleteMood(int id) {
        String sql = "DELETE FROM "+toIdentifier(TABLE_NAME)+" WHERE "+toIdentifier(ID)+"="+id;
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
    }
    
    /**
     * Deletes all moods from the database
     * <br> this method cleans the MoodData table by deleting and recreating it
     */
    @Override
    public void deleteAllMoods() {
        String sql = "DROP TABLE "+toIdentifier(TABLE_NAME);
        log.trace(sql);
        String e = db.change(sql);
        if (e != null)
            log.debug(e);
        
        initDB();
    }
    
    /**
     * @return the highest id, stored in the database
     */
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
    
    /**
     * closes the database connection safely - recommended!
     */
    @Override
    public void close() {
        db.closeDB();
    }
    
    /**
     * @param mood the mood to check for
     * @return true if the mood already exists otherwise false
     */
    private boolean moodExists(SimpleMood mood) {
        return moodExists(mood.id());
    }
    
    /**
     * @param id the id of the mood to check for
     * @return true if the mood already exists otherwise false
     */
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
    
    /**
     * inserts a new mood in the database
     * @param mood the mood as SimpleMood
     */
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
    
    /**
     * Updates an existing mood, identified by its id, with new values
     * @param mood the mood as SimpleMood
     */
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
    
    /**
     * Adds \" before and after the given String to prepare the identifier as SQLite command identifier
     * @param identifier the identifier name
     * @return the identifier in SQLite command format
     */
    private static String toIdentifier(String identifier) {
        return "\""+identifier+"\"";
    }
}
