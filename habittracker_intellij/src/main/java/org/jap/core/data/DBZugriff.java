package org.jap.core.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBZugriff {
    private static final Logger log = LogManager.getLogger(DBZugriff.class);
    
    //Variablen für den Verbindungsaufbau

    Connection db;  // Verbindungsobjekt
    String mDB; // Variable für den Treiber und den Pfad zur DB
    Statement stmtSQL; // Objekt für die Ausführung von SQL-Anweisungen
    String dbname;

    public DBZugriff(String datenbankname) {
        dbname = datenbankname;
        connect();
    }

    public final void connect() {
        try {
            //Laden der Treiberklasse
            Class.forName("org.sqlite.JDBC");
            //Verbindung zur Datenbank aufnehmen
            mDB = "jdbc:sqlite:" + dbname;
            db = DriverManager.getConnection(mDB, "", "");
            //Anweisungsobjekt zum Ausführen von SQL-Statements
            stmtSQL = db.createStatement();
        } catch (ClassNotFoundException err) {
            System.err.println("Treiberklasse konnte nicht geladen werden!");
        } catch (SQLException err) {
            System.err.println("Datenbank konnte nicht geoeffnet werden!");
        }
    }

    public ResultSet read(String pSQL) {
        ResultSet rs;
        try {
            rs = stmtSQL.executeQuery(pSQL);
            return rs;
        } catch (SQLException err) {
            System.err.println("Lesen fehlgeschlagen");
            rs = null;
            return rs;
        }
    }

    public void change(String pSQL) {
        try {
            //SQL UPDATE, INSERT INTO, DELETE
            stmtSQL.executeUpdate(pSQL);
        } catch (SQLException err) {
            System.err.println("Aendern fehlgeschlagen");
        }
    }

    public void close() {
        try {
            stmtSQL.close();
            db.close();
        } catch (SQLException err) {
            System.err.println("Schliessen fehlgeschlagen");
        }
    }
}
