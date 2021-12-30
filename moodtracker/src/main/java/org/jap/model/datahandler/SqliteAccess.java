package org.jap.model.datahandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/*
    Created by Peter
*/

/**
 * Class to Access a SQLite Database
 * written to be independent of database tables and table content
 */
class SqliteAccess {
    private static final Logger log = LogManager.getLogger(SqliteAccess.class);
    
    // Variables
    private Connection db;
    private final Statement stmtSQL;
    private final String dbname;
    
    // Constructor
    /**
     * Opens a connection to a database and creates one if the file doesn't exist
     * @param databaseName the name of the database file (".db" file-ending will be appended if missing)
     */
    public SqliteAccess(String databaseName) {
        if(databaseName.endsWith(".db"))
            dbname = databaseName;
        else
            dbname = databaseName + ".db";
        
        stmtSQL = openDB();
    }
    
    // Methods
    /**
     * opens the DB connection
     */
    private Statement openDB() {
        try {
            //loads driver class
            Class.forName("org.sqlite.JDBC");
            //connect to the db
            String mDB = "jdbc:sqlite:" + dbname;
            db = DriverManager.getConnection(mDB, "", "");
            //run sql-statement
            return db.createStatement();
        } catch (ClassNotFoundException err) {
            log.error("Treiberklasse konnte nicht geladen werden!");
        } catch (SQLException err) {
            log.error("Datenbank konnte nicht geöffnet werden!");
        }
        return null;
    }
    
    /**
     * @param pSQL the sql expression to read tuple from the database (e.g. SELECT)
     * @return the requested tuples
     */
    public ResultSet read(String pSQL) {
        ResultSet rs;
        try {
            //SQL: SELECT
            rs = stmtSQL.executeQuery(pSQL);
            return rs;
        } catch (SQLException err) {
            log.error("Lesen fehlgeschlagen");
            return null;
        }
    }
    
    /**
     * @param pSQL the sql expression to read tuple from the database (e.g. SELECT)
     * @return null if no error, otherwise the SQL-error as String
     */
    public String change(String pSQL) {
        try {
            //SQL: UPDATE, INSERT INTO, DELETE
            stmtSQL.executeUpdate(pSQL);
        } catch (SQLException err) {
            log.trace("Ändern fehlgeschlagen");
            return err.toString();
        }
        return null;
    }
    
    /**
     * Closes the Connection safely
     * it is recommended using this before deleting the last reference to an instance of this class
     */
    public void closeDB() {
        try {
            stmtSQL.close();
            db.close();
        } catch (SQLException err) {
            log.error("Schliessen fehlgeschlagen");
        }
        log.debug("Verbindung zu Datenbank '"+dbname+"' geschlossen!");
    }
}
