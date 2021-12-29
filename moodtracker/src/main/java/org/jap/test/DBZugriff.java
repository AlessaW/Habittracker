package org.jap.test;

//Alle Klassen zum Zugriff auf die JDBC-Schnittstelle liegen im folgendem Paket:
import java.sql.*;

public class DBZugriff {
    //Variablen für den Verbindungsaufbau

    Connection db;  // Verbindungsobjekt
    String mDB; // Variable für den Treiber und den Pfad zur DB
    Statement stmtSQL; // Objekt für die Ausführung von SQL-Anweisungen
    String dbname;

    public DBZugriff(String datenbankname) {
        dbname = datenbankname;
        oeffneDB();
    }

    public final void oeffneDB() {
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

    public ResultSet lesen(String pSQL) {
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

    public String aendern(String pSQL) {
        try {
            //SQL UPDATE, INSERT INTO, DELETE
            stmtSQL.executeUpdate(pSQL);
        } catch (SQLException err) {
            System.err.println("Aendern fehlgeschlagen");
            return err.toString();
        }
        return null;
    }

    public void schliesseDB() {
        try {
            stmtSQL.close();
            db.close();
        } catch (SQLException err) {
            System.err.println("Schliessen fehlgeschlagen");
        }
    }
}
