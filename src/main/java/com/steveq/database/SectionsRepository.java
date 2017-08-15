package com.steveq.database;

import com.steveq.communication.models.Section;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adam on 2017-08-07.
 */
public class SectionsRepository implements Repository {
    private SectionsSQLiteHelper dbHelper;

    public SectionsRepository(){
        dbHelper = SectionsSQLiteHelper.getInstance();
    }

    @Override
    public void open() {
        dbHelper.connect();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public boolean createSection(Section section) {
        int sectionState = section.getActive() ? 1 : 0;
        String query = "INSERT INTO " + SectionsContract.SectionsEntry.TABLE_NAME +
                        " (" + SectionsContract.SectionsEntry.COLUMN_SECTION_NUM + "," +
                        SectionsContract.SectionsEntry.COLUMN_ACTIVE + ") " +
                        "VALUES(" + section.getNumber() + "," + sectionState + ");";
        open();
        try {
            dbHelper.execSQL(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        close();
        return true;
    }

    @Override
    public Map<Integer, Section> getSections() {
        String query = "SELECT * FROM " + SectionsContract.SectionsEntry.TABLE_NAME + " ;";
        Map<Integer, Section> sections = new HashMap<>();
        open();
        try {
            ResultSet rs = dbHelper.execQuery(query);
            while(rs.next()){
                Section section = new Section();
                section.setNumber(rs.getInt(SectionsContract.SectionsEntry.COLUMN_SECTION_NUM));
                int activeStatus = rs.getInt(SectionsContract.SectionsEntry.COLUMN_ACTIVE);
                section.setActive(activeStatus != 0);
                sections.put(section.getNumber(), section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();

        for(int id : sections.keySet()){
            sections.get(id).setTimes(getTimesForSection(id));
            sections.get(id).setDays(getDaysForSection(id));
        }

        return sections;
    }

    @Override
    public Section getSectionById(Integer id) throws IllegalStateException {
        Map<Integer, Section> sections = new HashMap<>();
        String query = "SELECT section_number, active FROM " + SectionsContract.SectionsEntry.TABLE_NAME +
                        " WHERE " + SectionsContract.SectionsEntry.COLUMN_SECTION_NUM + " = " + id + " ;";
        open();
        try {
            System.out.println("SECTION BY ID QUERY : " + query);
            ResultSet rs = dbHelper.execQuery(query);
            System.out.println("Result SET : " + rs);
            while(rs.next()){
                System.out.println("RESULT SET HI!");
                Section section = new Section();
                section.setNumber(rs.getInt(SectionsContract.SectionsEntry.COLUMN_SECTION_NUM));
                int activeStatus = rs.getInt(SectionsContract.SectionsEntry.COLUMN_ACTIVE);
                section.setActive(activeStatus != 0);
                sections.put(section.getNumber(), section);
                System.out.println("SECTIONS BY ID : " + id + " : " + section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();

        for(int i : sections.keySet()){
            sections.get(i).setDays(getDaysForSection(i));
            sections.get(i).setTimes(getTimesForSection(i));
        }

        System.out.println("SECTIONS : " + sections);

        if(sections.size() > 1){
            throw new IllegalStateException("Obly one section should be returned from particular ID");
        } else if (sections.size() == 1){
            Section returnSection = sections.values().iterator().next();
            System.out.println("RETURN SECTION : " + returnSection);
            return returnSection;
        } else {
            Section section = new Section();
            section.setNumber(id);
            return section;
        }
    }

    @Override
    public boolean updateSection(Section section) {
        int sectionState = section.getActive() ? 1 : 0;
        String query = "UPDATE " + SectionsContract.SectionsEntry.TABLE_NAME + " SET " +
                        SectionsContract.SectionsEntry.COLUMN_ACTIVE + " = " + sectionState +
                        " WHERE " + SectionsContract.SectionsEntry.COLUMN_SECTION_NUM + " = " + section.getNumber();

        open();
        try {
            dbHelper.execUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return true;
    }

    @Override
    public boolean deleteSection(Integer id) {
        String query = "DELETE FROM " + SectionsContract.SectionsEntry.TABLE_NAME + " WHERE " +
                        SectionsContract.SectionsEntry.COLUMN_SECTION_NUM + " = " + id + " ;";
        open();
        try {
            dbHelper.execUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return false;
    }

    @Override
    public boolean createSectionDays(Section section, List<String> days) {
        if(!days.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for(String day : days){
                builder.append("('");
                builder.append(section.getNumber());
                builder.append("','");
                builder.append(day);
                builder.append("'),");
            }
                builder.deleteCharAt(builder.length() - 1);
            String query = "INSERT INTO " + SectionsContract.SectionsDaysEntry.TABLE_NAME +
                    " (" + SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + "," +
                    SectionsContract.SectionsDaysEntry.COLUMN_DAY + ") " +
                    "VALUES " + builder.toString() + " ;";
            open();
            try {
                dbHelper.execSQL(query);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            close();
            return true;
        }
        return false;
    }

    @Override
    public List<String> getDaysForSection(Integer id) {
        List<String> days = new ArrayList<>();
        String query = "SELECT * FROM " + SectionsContract.SectionsDaysEntry.TABLE_NAME +
                        " WHERE " + SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + " = " + id;
        open();
        try {
            ResultSet rs = dbHelper.execQuery(query);
            while(rs.next()){
                try {
                    days.add(rs.getString(SectionsContract.SectionsDaysEntry.COLUMN_DAY));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return days;
    }

    private class UpdatePackage{
        private List<String> toAdd;
        private List<String> toDelete;

        public UpdatePackage(List<String> toAdd, List<String> toDelete) {
            this.toAdd = toAdd;
            this.toDelete = toDelete;
        }

        public List<String> getToAdd() {
            return toAdd;
        }

        public void setToAdd(List<String> toAdd) {
            this.toAdd = toAdd;
        }

        public List<String> getToDelete() {
            return toDelete;
        }

        public void setToDelete(List<String> toDelete) {
            this.toDelete = toDelete;
        }
    }

    private UpdatePackage getUpdatePackage(List<String> oldDays, List<String> daysToUpdate){
        List<String> newDays = new ArrayList<>();
        for(String dayToUpdate : daysToUpdate){
            int i = 0;
            for(String oldDay : oldDays){
                System.err.println("DAY TO UPDATE : " + dayToUpdate);
                System.err.println("OLD DAY : " + oldDay);
                if(dayToUpdate.equals(oldDay)){
                    oldDays.remove(i);
                    i = -1;
                    break;
                }
                i++;
            }
            if(i != -1){
                newDays.add(dayToUpdate);
            }
        }
        return new UpdatePackage(newDays, oldDays);
    }

    @Override
    public void updateSectionDays(Section section, List<String> days) {
        List<String> currentDays = getDaysForSection(section.getNumber());
        UpdatePackage updatePackage = getUpdatePackage(currentDays, days);

        System.out.println("DAYS TO ADD : " + updatePackage.getToAdd());
        System.out.println("DAYS TO DELETE : " + updatePackage.getToDelete());

        deleteSectionDayEntries(section.getNumber(), updatePackage.getToDelete());
        createSectionDays(section, updatePackage.getToAdd());
    }

    @Override
    public boolean deleteSectionDays(Integer id) {
        String query = "DELETE FROM " + SectionsContract.SectionsDaysEntry.TABLE_NAME + " WHERE " +
                SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + " = " + id + " ;";
        open();
        try {
            dbHelper.execUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return true;
    }

    @Override
    public boolean deleteSectionDayEntries(Integer id, List<String> days) {
        open();

        for(String day : days){
            String query = "DELETE FROM " + SectionsContract.SectionsDaysEntry.TABLE_NAME + " WHERE " +
                    SectionsContract.SectionsDaysEntry.COLUMN_SECTION_ID + " = " + id + " AND " +
                    SectionsContract.SectionsDaysEntry.COLUMN_DAY + " = \"" + day + "\" ;";

            try {
                dbHelper.execUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close();
        return true;
    }

    @Override
    public boolean createSectionTimes(Section section, List<String> times) {
        if(!times.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for(String time : times){
                builder.append("('");
                builder.append(section.getNumber());
                builder.append("','");
                builder.append(time);
                builder.append("'),");
            }
                builder.deleteCharAt(builder.length() - 1);
            String query = "INSERT INTO " + SectionsContract.SectionsTimesEntry.TABLE_NAME +
                    " (" + SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + "," +
                    SectionsContract.SectionsTimesEntry.COLUMN_TIME + ") " +
                    "VALUES " + builder.toString() + " ;";
            open();
            try {
                dbHelper.execSQL(query);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            close();
            return true;
        }
        return false;
    }

    @Override
    public List<String> getTimesForSection(Integer id) {
        List<String> days = new ArrayList<>();
        String query = "SELECT * FROM " + SectionsContract.SectionsTimesEntry.TABLE_NAME +
                " WHERE " + SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + " = " + id;
        open();
        try {
            ResultSet rs = dbHelper.execQuery(query);
            while(rs.next()){
                try {
                    days.add(rs.getString(SectionsContract.SectionsTimesEntry.COLUMN_TIME));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return days;
    }

    @Override
    public void updateSectionTimes(Section section, List<String> times) {
        List<String> currentTimes = getTimesForSection(section.getNumber());
        UpdatePackage updatePackage = getUpdatePackage(currentTimes, times);

        System.out.println("DAYS TO ADD : " + updatePackage.getToAdd());
        System.out.println("DAYS TO DELETE : " + updatePackage.getToDelete());

        deleteSectionTimeEntries(section.getNumber(), updatePackage.getToDelete());
        createSectionTimes(section, updatePackage.getToAdd());
    }

    @Override
    public boolean deleteSectionTimes(Integer id) {
        String query = "DELETE FROM " + SectionsContract.SectionsTimesEntry.TABLE_NAME + " WHERE " +
                SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + " = " + id + " ;";
        open();
        try {
            dbHelper.execUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return true;
    }

    @Override
    public boolean deleteSectionTimeEntries(Integer id, List<String> times) {
        open();

        for(String time : times){
            String query = "DELETE FROM " + SectionsContract.SectionsTimesEntry.TABLE_NAME + " WHERE " +
                    SectionsContract.SectionsTimesEntry.COLUMN_SECTION_ID + " = " + id + " AND " +
                    SectionsContract.SectionsTimesEntry.COLUMN_TIME + " = '" + time + "' ;";

            try {
                dbHelper.execUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close();
        return true;
    }
}
