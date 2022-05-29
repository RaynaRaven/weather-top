package controllers;
import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = member.stations;
    render("dashboard.html", stations);
  }

public static void addStation (String name,double latitude, double longitude)
  {
    Logger.info("Adding a Station");
    Member member = Accounts.getLoggedInMember();
    Station station = new Station (name,latitude,longitude);
    member.stations.add(station);
    Logger.info ("Adding a new Station called " + name + " to " + member.firstname);
    member.save();
    redirect ("/dashboard");
  }
}
