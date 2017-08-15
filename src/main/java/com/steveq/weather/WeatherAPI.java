package com.steveq.weather;

import com.steveq.weather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Adam on 2017-08-13.
 */
public interface WeatherAPI {
    @GET("forecast/{id}/{lonlan}")
    Call<WeatherModel> getWeather(@Path("id") String apiKey, @Path("lonlan") String lonlanString);
}
