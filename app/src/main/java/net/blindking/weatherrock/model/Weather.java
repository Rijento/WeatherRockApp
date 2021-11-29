package net.blindking.weatherrock.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Weather {
    private Calendar sunrise;
    private Calendar sunset;
    private double temperature;
    private double windSpeed;
    private int cloudiness;
    private double visibility;
    private String precipitationType;
    private double precipitationAmount;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);


    public Weather() {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Time is returned as utc
        sunrise = Calendar.getInstance();
        sunset = Calendar.getInstance();
        sunrise.set(sunrise.get(Calendar.YEAR),
                sunrise.get(Calendar.MONTH),
                sunrise.get(Calendar.DATE),
                8,
                0); // Set sunrise to 8 am
        sunset.set(sunset.get(Calendar.YEAR),
                sunset.get(Calendar.MONTH),
                sunset.get(Calendar.DATE),
                16,
                0); // set sunset to 8 pm

        temperature = 50.0;
        windSpeed = 0.0;
        cloudiness = 0;
        visibility = 10000;
        precipitationType = "no";
        precipitationAmount = 0.0;

    }

    public Calendar getSunrise() { return sunrise; }

    public Calendar getSunset() { return sunset; }

    public double getTemperature() { return temperature; }

    public double getWindSpeed() { return windSpeed; }

    public int getCloudiness() { return cloudiness; }

    public double getVisibility() { return visibility; }

    public String getPrecipitationType() { return precipitationType; }

    public double getPrecipitationAmount() { return precipitationAmount; }

    public void setSunrise(String sunriseString) throws ParseException {
        sunrise.setTime(sdf.parse(sunriseString));
    }
    public void setSunset(String sunsetString) throws ParseException {
        sunset.setTime(sdf.parse(sunsetString));
    }

    public void setTemperature(double temperature) { this.temperature = temperature; }

    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public void setCloudiness(int cloudiness) { this.cloudiness = cloudiness; }

    public void setVisibility(double visibility) { this.visibility = visibility; }

    public void setPrecipitationType(String precipitationType) { this.precipitationType = precipitationType; }

    public void setPrecipitationAmount(double precipitationAmount) { this.precipitationAmount = precipitationAmount; }

}
