package com.steveq.database;

import com.steveq.communication.models.Section;

import java.util.List;
import java.util.Map;

/**
 * Created by Adam on 2017-08-07.
 */
public interface Repository {
    void open();
    void close();
    boolean createSection(Section section);
    Map<Integer, Section> getSections();
    Section getSectionById(Integer id);
    boolean updateSection(Section section);
    boolean deleteSection(Integer id);
    boolean createSectionDays(Section section, List<String> days);
    List<String> getDaysForSection(Integer id);
    void updateSectionDays(Section section, List<String> days);
    boolean deleteSectionDays(Integer id);
    boolean deleteSectionDayEntries(Integer id, List<String> days);
    boolean createSectionTimes(Section section, List<String> times);
    List<String> getTimesForSection(Integer id);
    void updateSectionTimes(Section section, List<String> times);
    boolean deleteSectionTimes(Integer id);
    boolean deleteSectionTimeEntries(Integer id, List<String> times);
}
