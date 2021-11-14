package org.jap.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.core.data.DBManager;

/*
    Created by Peter
*/

public class sqltest {
    private static final Logger log = LogManager.getLogger(sqltest.class);
    
    private static DBManager db = new DBManager("saves/testDB.sqlite");
    
    public static void main(String[] args) {
        db.newMoodTable();
    }
}
