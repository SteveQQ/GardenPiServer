package com.steveq.controllers;

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
 * Created by Adam on 2017-08-09.
 */
public class DownloadSectionsDataController implements Controller {
    private Repository repository;

    public DownloadSectionsDataController(){
        repository = new SectionsRepository();
    }

    @Override
    public ToClientResponse processRequest(FromClientRequest request) {
        List<Section> requestedSections = request.getPayload();
        List<Integer> sectionsNums = new ArrayList<>();
        for(Section section : requestedSections){
            sectionsNums.add(section.getNumber());
        }

        List<Section> downloadedSections = new ArrayList<>();

        for(Integer i : sectionsNums){
            downloadedSections.add(repository.getSectionById(i));
        }

        ToClientResponse response = new ToClientResponse();
        response.setMethod(JsonProcessor.Method.DOWNLOAD.toString());
        response.setSections(downloadedSections);
        response.setWeather(new WeatherOutputModel());

        return response;
    }
}
