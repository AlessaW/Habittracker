package org.jap.test;

import java.sql.*;

public class DBverwaltung
{
    DBZugriff db;

    DBverwaltung(String DB)
    {
        db = new DBZugriff(DB);
        System.out.println("Datenbank verbunden: " + DB);
    }
    
    void neueTabellen()
    {
        db.aendern("""
                CREATE TABLE "Test" (
                    "id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,
                    "name" TEXT NOT NULL ,
                    "description" TEXT NOT NULL ,
                    "timestamp" DATE,
                    "mood_" INTEGER NOT NULL  DEFAULT 0,
                    "activity" TEXT,
                    "moodValue" TEXT
                )
            """
        );
    }
    
//    public String updateV(Vokabel update)
//    {
//        String sql="UPDATE Vokabelliste SET Level=\""    +update.Level +
//                                        "\", LLTime=\""    +update.LLTime +
//                                        "\" WHERE ID=\""  +update.ID + "\"";
//        //System.out.println(sql);
//        return db.aendern(sql);
//    }
//
//    public String speichernF(Fach neu)
//    {
//        String sql="INSERT INTO Liste (Fach,Available,Key) VALUES (\""+
//                                        neu.Fach+"\",\""+
//                                        neu.Available+"\",\""+
//                                        neu.Key+"\")";
//        System.out.println(sql);
//        return db.aendern(sql);
//    }
//
//    public String speichernV(String sql)
//    {
//        System.out.println(sql);
//        return db.aendern(sql);
//    }
//
//    public String updateSetting(Fach update)
//    {
//        String sql="UPDATE Liste SET Available=\""    +update.Available +
//                                        "\", Key=\"" + update.Key +
//                                        "\" WHERE ID=\""  +update.ID + "\"";
//        System.out.println(sql);
//        return db.aendern(sql);
//    }
//
//    public String updateF(Fach update)
//    {
//        String sql="UPDATE Liste SET Available=\""    +update.Available +
//                                        "\" WHERE Key=\""  +update.Key + "\"";
//        System.out.println(sql);
//        return db.aendern(sql);
//    }
//
//    public ArrayList<Vokabel> getAlleVokabeln()
//    {
//        ArrayList<Vokabel> liste = new ArrayList<Vokabel>();
//        try
//        {
//            String sql = "Select * FROM Vokabelliste;";
//            //System.out.println("Vokabeln-SQL: "+sql); //Zur Kontrolle
//            ResultSet rs = db.lesen(sql);
//            while (rs.next())
//            {
//                Vokabel einWert = new Vokabel(  rs.getInt("ID"),
//                                                rs.getString("Frage"),
//                                                rs.getString("Antwort"),
//                                                rs.getByte("Level"),
//                                                rs.getLong("LLTime"),
//                                                rs.getString("Kategorie"),
//                                                rs.getString("Beschreibung"),
//                                                rs.getString("Verknuepfung"));
//                liste.add(einWert);
//                System.out.println(einWert.toString());
//            }
//            return liste;
//        }
//
//        catch (SQLException e)
//        {
//            return liste;
//        }
//    }
//
//    public ArrayList<Fach> getAlleFaecher()
//    {
//        ArrayList<Fach> liste = new ArrayList<Fach>();
//        try
//        {
//            String sql = "Select * FROM Liste;";
//            //System.out.println("Fächer-SQL: "+sql); //Zur Kontrolle
//            ResultSet rs = db.lesen(sql);
//            while (rs.next())
//            {
//                Fach einWert = new Fach(    rs.getInt("ID"),
//                                            rs.getString("Fach"),
//                                            rs.getByte("Available"),
//                                            rs.getString("Key"));
//                liste.add(einWert);
//                //System.out.println(einWert.toString());
//            }
//            return liste;
//        }
//
//        catch (SQLException e)
//        {
//            return liste;
//        }
//    }
}
