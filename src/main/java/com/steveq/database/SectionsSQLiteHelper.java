package com.steveq.database;

import com.steveq.utils.FileUtils;

import java.sql.*;

/**
 * Created by Adam on 2017-08-07.
 */
public class SectionsSQLiteHelper {
    private static final String URL = "jdbc:sqlite:/home/pi/Documents/GardenPiServer/alarms_db.db";
    private Connection conn;
    private Statement stmt;
    private static SectionsSQLiteHelper instance;


    //CREATE TABLES
    private final String CREATE_SECTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            SectionsContract.SectionsEntry.TABLE_NAME + " (" +
            SectionsContract.SectionsEntry.COLUMN_SECTION_NUM + " INTEGER PRIMARY KEY, " +
            SectionsContract.SectionsEntry.COLUMN_ACTIVE + " INTEGER )";

    private final String CREATE_SECTIONS_DAYS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            SectionsContract.SectionsDaysEntry.TABLE_NAME+ " (" +
            SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + " INTEGER, " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " TEXT CHECK(" +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(0) + "' OR " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(1) + "' OR " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(2) + "' OR " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(3) + "' OR " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(4) + "' OR " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(5) + "' OR " +
            SectionsContract.SectionsDaysEntry.COLUMN_DAY + " == '" + FileUtils.getDaysProperties().get(6) + "' ), " +
            "FOREIGN KEY(" + SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + ") REFERENCES " + SectionsContract.SectionsEntry.TABLE_NAME + "(" + SectionsContract.SectionsEntry.COLUMN_SECTION_NUM+ ")," +
            "PRIMARY KEY(" + SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + "," + SectionsContract.SectionsDaysEntry.COLUMN_DAY + "))";

    private final String CREATE_SECTIONS_TIMES_TABLE = "CREATE TABLE IF NOT EXISTS " +
            SectionsContract.SectionsTimesEntry.TABLE_NAME+ " (" +
            SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + " INTEGER, " +
            SectionsContract.SectionsTimesEntry.COLUMN_TIME + " TEXT, " +
            "FOREIGN KEY(" + SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + ") REFERENCES " + SectionsContract.SectionsEntry.TABLE_NAME + "(" + SectionsContract.SectionsEntry.COLUMN_SECTION_NUM+ ")," +
            "PRIMARY KEY(" + SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + "," + SectionsContract.SectionsTimesEntry.COLUMN_TIME + "))";


    private SectionsSQLiteHelper(){
    }

    public static SectionsSQLiteHelper getInstance(){
        if(instance == null){
            instance = new SectionsSQLiteHelper();
        }
        return instance;
    }

    public Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            execSQL(CREATE_SECTIONS_TABLE);
            execSQL(CREATE_SECTIONS_DAYS_TABLE);
            execSQL(CREATE_SECTIONS_TIMES_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void close(){
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execSQL(String statement) throws SQLException{
        if(conn != null && !conn.isClosed()){
            try {
                stmt.execute(statement);
                conn.commit();
            } catch (SQLException e) {
                System.out.println("WRONG EXEC SQL : ");
                e.printStackTrace();
            }
        }
    }

    public ResultSet execQuery(String statement) throws SQLException{
        ResultSet rs = null;
        if(conn != null && !conn.isClosed()){
            try {
                rs = stmt.executeQuery(statement);
            } catch (SQLException e) {
                System.out.println("WRONG EXEC QUERY : ");
                e.printStackTrace();
            }
        }
        return rs;
    }

    public void execUpdate(String statement) throws SQLException{
        if(conn != null && !conn.isClosed()){
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(statement);
                stmt.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
