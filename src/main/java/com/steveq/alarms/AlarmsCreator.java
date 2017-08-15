package com.steveq.alarms;

import com.steveq.communication.models.Section;
import com.steveq.database.Repository;
import com.steveq.database.SectionsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Adam on 2017-08-09.
 */
public class AlarmsCreator {
    private static final long ONE_DAY_MILLIS = 86400000;
    private Timer currentTimer;
    private Set<String> registeredAlarms;
    private Map<Integer, Timer> activeTimers;
    private Repository repository;

    public AlarmsCreator(){
        repository = new SectionsRepository();
        registeredAlarms = new HashSet<>();
        activeTimers = new HashMap<>();
    }
    public void registerAlarmForSection(int duration){

        Map<Integer, Section> sections = repository.getSections();
        for(Section section : sections.values()){
            activeTimers.put(section.getNumber(), new Timer());

            List<String> times = section.getTimes();

            if(section.getActive()){
                for(String time : times){
                    //if(!registeredAlarms.contains(time)) {
                        Long timeStamp = getTimeInMillis(time);
                        System.out.println("TIME IN MILLIS : " + timeStamp);
                        SectionTurnOnTask task = new SectionTurnOnTask(duration);
                        task.setSection(section);
                        activeTimers.get(section.getNumber()).schedule(task, new Date(timeStamp), ONE_DAY_MILLIS);
                    //}
                }
            }
        }
    }

    public void resetTimer(){
        for(Integer key : activeTimers.keySet()){
            activeTimers.get(key).cancel();
            int removedTasks = activeTimers.get(key).purge();

            System.out.println("CANCELED TASKS : " + removedTasks);
        }
    }

    private long getTimeInMillis(String timeString){
        String[] subTimes = timeString.split(":");

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        if(isTomorrow(timeString)){
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(subTimes[0].trim()));
        calendar.set(Calendar.MINUTE, Integer.valueOf(subTimes[1].trim()));
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    private boolean isTomorrow(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            if (sdf.parse(time).before(sdf.parse(sdf.format(new Date())))) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
