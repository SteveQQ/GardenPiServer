package com.steveq.controllers;

import com.steveq.alarms.AlarmsCreator;
import com.steveq.communication.JsonProcessor;
import com.steveq.communication.models.FromClientRequest;
import com.steveq.communication.models.Section;
import com.steveq.communication.models.ToClientResponse;
import com.steveq.database.Repository;
import com.steveq.database.SectionsRepository;
import com.steveq.weather.model.WeatherModel;
import com.steveq.weather.model.WeatherOutputModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017-08-08.
 */
public class UploadSectionsDataController implements Controller{
    private Repository repository;
    AlarmsCreator alarmsCreator;

    public UploadSectionsDataController(){
        repository = new SectionsRepository();
        alarmsCreator = new AlarmsCreator();
    }

    @Override
    public ToClientResponse processRequest(FromClientRequest request) {
        List<Section> affectedSections = new ArrayList<>();
        List<Section> sections = request.getPayload();
        alarmsCreator.resetTimer();
        for(Section section : sections){
            Section sectionFromDB = repository.getSectionById(section.getNumber());
            if(sectionFromDB.getDays() == null){
                System.out.println("CREATE SECTION ON UPLOAD");
                repository.createSection(section);
                repository.createSectionDays(section, section.getDays());
                repository.createSectionTimes(section, section.getTimes());
            } else {
                System.out.println("UPDATE SECTION ON UPLOAD");
                repository.updateSection(section);
                repository.updateSectionDays(section, section.getDays());
                repository.updateSectionTimes(section, section.getTimes());
            }
            affectedSections.add(section);
        }
        alarmsCreator.registerAlarmForSection(Integer.valueOf(request.getDuration()));
        ToClientResponse response = new ToClientResponse();
        response.setMethod(JsonProcessor.Method.UPLOAD.toString());
        response.setSections(affectedSections);
        response.setWeather(new WeatherOutputModel());
        return response;
    }
}
