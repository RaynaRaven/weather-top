package models;

import java.util.ArrayList;
import java.util.List;

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

public int beaufort()
{
  int beaufort = -1;
  if (readings.size() !=0){
    Reading reading = getLatestReading();
    double windSpeed = reading.getWindSpeed();

    if ((windSpeed>=0)&&(windSpeed<1))
      beaufort = 0;
    else if ((windSpeed>=1)&&(windSpeed<6))
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

  public int pressure()
  {
    if (readings.size() !=0){
      Reading reading = getLatestReading();
      return reading.getPressure();
    } else
      return 0;
  }


}
