package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 * A Factory for the saveFileManager interface
 */
public class SaveFileManagerFactory {
    private static final Logger log = LogManager.getLogger(SaveFileManagerFactory.class);
    
    public enum SaveFileManagerType {
        Sqlite,
//        Json
    }
    
    /**
     * @param type     the type of saveFileManager to return
     * @param fileName the name of the saveFile to open (the saveFileManager appends its file extension e.g. ".db")
     * @return an instance of the selected saveFileManager
     */
    public static SaveFileManager getSaveFileManager(SaveFileManagerType type, String fileName) {
        switch (type) {
            case Sqlite -> {return new SqliteManager(fileName);}
//            case Sqlite -> {return new JsonManager(fileName);} // Possible Extension
        }
        return null;
    }
}
