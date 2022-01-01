package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 *
 */
public class SaveFileManagerFactory {
    private static final Logger log = LogManager.getLogger(SaveFileManagerFactory.class);
    
    public enum SaveFileManagerType {
        Sqlite,
//        Json
    }
    
    public static SaveFileManager getSaveFileManager(SaveFileManagerType type, String fileName) {
        switch (type) {
            case Sqlite -> {return new SqliteManager(fileName);}
//            case Sqlite -> {return new JsonManager(fileName);} // Possible Extension
        }
        return null;
    }
}
