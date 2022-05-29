package controllers;

import java.util.List;

import models.Reading;
import models.Station;
import play.mvc.Controller;
import play.Logger;

public class StationCtrl extends Controller {

  public static void index(Long id)
  {
    Station station = Station.findById(id);
    Logger.info ("Station id = " + id);
    render("station.html", station);
  }

  public static void addReading(Long id, int code, double temperature, double windSpeed, double windDirection, int pressure)
  {
    Reading reading = new Reading(code,temperature,windSpeed,windDirection,pressure);
    Station station = Station.findById(id);
    station.readings.add(reading);
    station.save();
    redirect ("/stations/" + id);
  }
}
