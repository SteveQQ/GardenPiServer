package com.steveq.communication.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2017-08-03.
 */
public class FromClientRequest {
    private String method;
    private List<Section> payload;
    private String coords;
    private String duration;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Section> getPayload() {
        return payload;
    }

    public void setPayload(List<Section> payload) {
        this.payload = payload;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
