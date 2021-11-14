package org.jap.core.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

public class DBManager
{
    private static final Logger log = LogManager.getLogger(DBManager.class);
    
    DBZugriff db;
    
    public DBManager(String DB) {
        db = new DBZugriff(DB);
        log.info("Datenbank verbunden: " + DB);
    }
    
    public void newMoodTable() {
        db.change("");
    }
    
    public void close() {
        db.close();
    }
}
