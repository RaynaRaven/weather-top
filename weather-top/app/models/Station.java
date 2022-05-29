package models;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;
import play.jobs.On;

@Entity
public class Station extends Model
{
  public String name;
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
  /**
   * Constructor for Station object
   * @param name
   */
  public Station(String name) {
    this.name = name;
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
    /*initialize a local weatherCode variable
     */
    String weatherCode = "weather description";
    if (readings.size() !=0){
      /*declare a reading object and initialise to
      * the latest reading by calling the getLastReading()
      * method
      */
      Reading reading = getLatestReading();
      /*
      * Initialise a code variable and get the code value from
      * the above instance of reading.
      */
      int code = reading.getCode();
      /*
      * Passing local variable code as the parameter for a switch statement
      */
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
   * tempCelcius() is a method that takes the temperature value from the
   * latest reading and returns it as a double.
   * @return
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
   * @return
   */
  public double tempFahrenheit()
  {
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      double fahrenheit = reading.getTemperature() * 9/5 + 32;
      return fahrenheit;
    } else
      return 0.0;
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
      else
        beaufort = -1;
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

}
