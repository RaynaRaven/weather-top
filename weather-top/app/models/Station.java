package models;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class Station extends Model
{
  public String name;
  public double latitude;
  public double longitude;
  @OneToMany(cascade = CascadeType.ALL)
  public List<Reading> readings = new ArrayList<Reading>();
  public String weatherCode;
  public double tempCelsius;
  public double tempFahrenheit;
  public int beaufort;
  public int pressure;
  public Reading latestReading;
  public String windCompass;
  public double windChill;
  public String weatherIcon;
  public double minTemp;
  public double maxTemp;
  public double minWind;
  public double maxWind;
  public double minPressure;
  public double maxPressure;
  public String trendTemp;
  public String trendWind;
  public String trendPressure;


  /**
   * Constructor for Station object, latitude and longitude are rounded to three decimal places.
   * @param name describes the station name
   * @param latitude describes geographical location of station between north and South Pole, expressed in decimal degrees
   * @param longitude describes the geographical location of a station east or west of greenwich meridian, expressed in decimal degrees
   */
  public Station(String name, double latitude, double longitude) {
    this.name = name;
    latitude = roundAvoid(latitude, 3);
    this.latitude = latitude;
    longitude = roundAvoid(longitude,3);
    this.longitude= longitude;
  }

  public static double roundAvoid(double value, int places) {
    double scale = Math.pow(10, places);
    return Math.round(value * scale) / scale;
  }
  /*
   * Getter methods for Station fields.
   */
  public String getName() {return name;}
  public List<Reading> getReadings() {
    return readings;
  }
  public String getWeatherCode() {
    this.weatherCode = weatherCode();
    return weatherCode;
  }
  public double getTempCelsius() {
    this.tempCelsius = tempCelsius();
    return tempCelsius;
  }
  public double getTempFahrenheit() {
    this.tempFahrenheit = tempFahrenheit();
    return tempFahrenheit;
  }
  public int getBeaufort() {
    this.beaufort = beaufort();
    return beaufort;
  }
  public String getWindCompass() {
    this.windCompass = windCompass();
    return windCompass;
  }
  public double getWindChill() {
    this.windChill = windChill();
    return windChill;
  }
  public int getPressure() {
    this.pressure = pressure();
    return pressure;
  }

  public String getWeatherIcon(){
    this.weatherIcon = weatherIcon();
    return weatherIcon;
  }

  public double getMinTemp() {
    this.minTemp = minTemp();
    return minTemp;
  }

  public double getMaxTemp() {
   this.maxTemp = maxTemp();
   return maxTemp;
  }


  public double getMinWind() {
    this.minWind = minWind();
    return minWind;
  }

  public double getMaxWind() {
    this.maxWind = maxWind();
    return maxWind;
  }

  public double getMinPressure() {
    this.minPressure = minPressure();
    return minPressure;
  }

  public double getMaxPressure() {
    this.maxPressure = maxPressure();
    return maxPressure;
  }

  public String getTrendTemp() {
    this.trendTemp = trendTemp();
    return trendTemp;
  }

  public String getTrendWind() {
    this.trendWind = trendWind();
    return trendWind;
  }

  public String getTrendPressure() {
    this.trendPressure = trendPressure();
    return trendPressure;
  }

  public String trendTemp(){
    String trend = "null";
    if (readings.size() >=3){
     int last = readings.size()-1;
     int secondLast = readings.size()-2;
     int thirdLast = readings.size()-3;

     Reading r3 = readings.get(last);
     Reading r2 = readings.get(secondLast);
     Reading r1 = readings.get(thirdLast);

     if ((r3.getTemperature()> r2.getTemperature())&&(r2.getTemperature()> r1.getTemperature())){
       trend = "large white white arrow alternate circle up outline icon";
     }
     else if ((r3.getTemperature() < r2.getTemperature())&&(r2.getTemperature() < r1.getTemperature())){
       trend = "large white white arrow alternate circle down outline icon";
     }
     else trend = "large white white arrow alternate circle right outline icon";
    }
    return trend;
  }

  public String trendWind(){
    String trend = "null";
    if (readings.size() >=3){
      int last = readings.size()-1;
      int secondLast = readings.size()-2;
      int thirdLast = readings.size()-3;

      Reading r3 = readings.get(last);
      Reading r2 = readings.get(secondLast);
      Reading r1 = readings.get(thirdLast);

      if ((r3.getWindSpeed()> r2.getWindSpeed())&&(r2.getWindSpeed()> r1.getWindSpeed())){
        trend = " large white arrow alternate circle up outline icon";
      }
      else if ((r3.getWindSpeed() < r2.getWindSpeed())&&(r2.getWindSpeed() < r1.getWindSpeed())){
        trend = "large white arrow alternate circle down outline icon";
      }
      else trend = "large white arrow alternate circle right outline icon";
    }
    return trend;
  }

  public String trendPressure(){
    String trend = "null";
    if (readings.size() >=3) {
      int last = readings.size()-1;
      int secondLast = readings.size()-2;
      int thirdLast = readings.size()-3;

      Reading r3 = readings.get(last);
      Reading r2 = readings.get(secondLast);
      Reading r1 = readings.get(thirdLast);

      if ((r3.getPressure()> r2.getPressure())&&(r2.getPressure()> r1.getPressure())){
        trend = "large white white arrow alternate circle up outline icon";
      }
      else if ((r3.getPressure() < r2.getPressure())&&(r2.getPressure() < r1.getPressure())){
        trend = "large white arrow alternate circle down outline icon";
      }
      else trend = "large white arrow alternate circle right outline icon";
    }
    return trend;
  }

  public double minTemp(){
    if (readings.size() !=0){
      Reading minReading = readings.get(0);
      double minTemp = minReading.getTemperature();
      for (Reading reading: readings){
        if (reading.getTemperature() < minReading.getTemperature())
          minTemp = reading.getTemperature();
      }return minTemp;
    }else
    return 0.0;
  }

  public double maxTemp(){
    if (readings.size() !=0){
      Reading maxReading = readings.get(0);
      double maxTemp = maxReading.getTemperature();
      for (Reading reading: readings){
        if (reading.getTemperature() >maxReading.getTemperature())
          maxTemp = reading.getTemperature();
      }return maxTemp;
    }else
      return 0.0;
  }

  public double minWind(){
    if (readings.size() !=0){
      Reading minReading = readings.get(0);
      double minWind = minReading.getWindSpeed();
      for (Reading reading: readings){
        if (reading.getWindSpeed() < minReading.getWindSpeed())
          minWind= reading.getWindSpeed();
      }return minWind;
    }else
      return 0.0;
  }

  public double maxWind(){
    if (readings.size() !=0){
      Reading maxReading = readings.get(0);
      double maxWind = maxReading.getWindSpeed();
      for (Reading reading: readings){
        if (reading.getWindSpeed() >maxReading.getWindSpeed())
          maxWind = reading.getWindSpeed();
      }return maxWind;
    }else
      return 0.0;
  }

  public double minPressure(){
    if (readings.size() !=0){
      Reading minReading = readings.get(0);
      double minPressure = minReading.getPressure();
      for (Reading reading: readings){
        if (reading.getPressure() < minReading.getPressure())
          minPressure= reading.getPressure();
      }return minPressure;
    }else
      return 0.0;
  }

  public double maxPressure(){
    if (readings.size() !=0){
      Reading maxReading = readings.get(0);
      double maxPressure = maxReading.getPressure();
      for (Reading reading: readings){
        if (reading.getPressure() >maxReading.getPressure())
          maxPressure = reading.getPressure();
      }return maxPressure;
    }else
      return 0.0;
  }

  public Reading getLatestReading() {
    if (readings.size() !=0){
      int lastReadingIndex = readings.size()-1;
      latestReading = readings.get(lastReadingIndex);
    }
    return latestReading;
  }
  /**
   * weatherCode() is a method that converts the code from the
   * latest reading to a string outputting weatherType (e.g. rain,
   * sunny, partly cloudy etc.) It will utilise a switch statement to
   * read in the data and output the string.
   * @return
   */
  public String weatherCode(){
    String weatherCode = "weather description";
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      int code = reading.getCode();
      switch (code){
        case 100:
          weatherCode = "Clear";
          break;
        case 200:
          weatherCode = "Partial clouds";
          break;
        case 300:
          weatherCode = "Cloudy";
          break;
        case 400:
          weatherCode = "Light Showers";
          break;
        case 500:
          weatherCode = "Heavy Showers";
          break;
        case 600:
          weatherCode = "Rain";
          break;
        case 700:
          weatherCode = "Snow";
          break;
        case 800:
          weatherCode = "Thunder";
          break;
        default:
          weatherCode = "null";
      }
    }
    return weatherCode;
  }
  /**
   * tempCelsius() is a method that takes the temperature value from the
   * latest reading and returns it as a double.
   * @return temperature from the latest reading or 0.0
   */
  public double tempCelsius()
  {
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      return reading.getTemperature();
    } else
      return 0.0;
  }
  /**
   * tempFahrenheit() is a method that converts the temperature value
   * from the latest reading to Fahrenheit and returns it as an int variable.
   * @return fahrenheit conversion value or 0.0
   */
  public double tempFahrenheit()
  {
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      return reading.getTemperature() * 9/5 + 32;
    } else {
      double fahrenheit = 0.0;
      return fahrenheit;
    }
  }
  /**
   * beaufort() is a method that converts the windSpeed value
   * from the latest reading to the corresponding beaufort and
   * returns it as an int variable.
   * @return
   */
  public int beaufort() {
    int beaufort = -1;
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      double windSpeed = reading.getWindSpeed();
      if ((windSpeed>=0)&&(windSpeed<=1))
        beaufort = 0;
      else if ((windSpeed>1)&&(windSpeed<6))
        beaufort = 1;
      else if ((windSpeed>=6)&&(windSpeed<12))
        beaufort = 2;
      else if ((windSpeed>=12)&&(windSpeed<20))
        beaufort = 3;
      else if ((windSpeed>=20)&&(windSpeed<29))
        beaufort = 4;
      else if ((windSpeed>=29)&&(windSpeed<39))
        beaufort = 5;
      else if ((windSpeed>=39)&&(windSpeed<50))
        beaufort = 6;
      else if ((windSpeed>=50)&&(windSpeed<62))
        beaufort = 7;
      else if ((windSpeed>=62)&&(windSpeed<75))
        beaufort = 8;
      else if ((windSpeed>=75)&&(windSpeed<89))
        beaufort = 9;
      else if ((windSpeed>=89)&&(windSpeed<103))
        beaufort = 10;
      else if ((windSpeed>=103)&&(windSpeed<117))
        beaufort = 11;
      }
      return beaufort;
    }
  private String windCompass() {
    String direction = "direction";
    if(readings.size() !=0) {
      Reading reading = getLatestReading();
      double degrees = reading.getWindDirection();

      if (((degrees > 348.75) && (degrees <= 360))||
        ((degrees >=0) && (degrees <= 11.25)))
        direction = "North";
      else if ((degrees >11.25) && (degrees <= 33.75))
        direction = "North North East";
      else if ((degrees >33.75) && (degrees <=56.25 ))
        direction = "North East";
      else if ((degrees >56.25) && (degrees <=78.75 ))
        direction = "East North East";
      else if ((degrees >78.75) && (degrees <=101.25 ))
        direction = "East";
      else if ((degrees >101.25) && (degrees <=123.75))
        direction = "East South East";
      else if ((degrees >123.75) && (degrees <=146.25 ))
        direction = "South East";
      else if ((degrees >146.25) && (degrees <=168.75 ))
        direction = "South South East";
      else if ((degrees >168.75) && (degrees <= 191.25))
        direction = "South";
      else if ((degrees >191.25) && (degrees <=213.75 ))
        direction = "South South West";
      else if ((degrees >213.75) && (degrees <= 236.25))
        direction = "South West";
      else if ((degrees >236.25) && (degrees<=258.75))
        direction = "West South West";
      else if ((degrees >258.75) && (degrees <= 281.25))
        direction = "West";
      else if ((degrees >281.25) && (degrees <= 303.75))
        direction = "West North West";
      else if ((degrees >303.75) && (degrees <= 326.25))
        direction = "North West";
      else if ((degrees >326.25) && (degrees <= 348.75))
        direction = "North North West";
    } else return "error";
    return direction;
  }

  public double windChill()
  {
    double windChillCalc = 0.0;
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      double tempCalcA = 0.6215*reading.getTemperature();
      double tempCalcB = 0.3965*reading.getTemperature();
      double velCalc = Math.pow(reading.getWindDirection(),0.16);
      windChillCalc = 13.12 + tempCalcA - 11.37*velCalc + tempCalcB*velCalc;
      windChillCalc =(Math.round(windChillCalc*100.0)/100.0);
    }
    return windChillCalc;
  }

  public int pressure()
  {
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      return reading.getPressure();
    } else
      return 0;
  }

  public String weatherIcon(){
    String weatherIcon = "null";
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      int code = reading.getCode();
      switch (code){
        case 100:
          weatherIcon = "large circular colored white sun icon";
          break;
        case 200:
          weatherIcon = "large circular colored white cloud sun icon";
          break;
        case 300:
          weatherIcon = "large circular colored white cloud icon";
          break;
        case 400:
          weatherIcon = "large circular colored white cloud sun rain icon";
          break;
        case 500:
          weatherIcon = "large circular colored white cloud showers heavy icon";
          break;
        case 600:
          weatherIcon = "large circular colored white cloud rain icon";
          break;
        case 700:
          weatherIcon = "large circular colored white snowflake icon";
          break;
        case 800:
          weatherIcon = "large circular colored white poo storm icon";
          break;
        default:
          weatherIcon = "null";
      }
    }
    return weatherIcon;
  }
}
