package net.blindking.weatherrock.controller;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import net.blindking.weatherrock.R;
import net.blindking.weatherrock.model.Weather;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WindowController implements Response.Listener<String> {
    private View pendulum;
    private View fog;
    private ImageView rock;
    private Animation animation;
    private ArrayList<View> clouds;
    Weather weather;

    public WindowController(View pendulum, ImageView rock, View fog, Animation animation, ArrayList<View> clouds, Weather weather) {
        this.pendulum = pendulum;
        this.rock = rock;
        this.fog = fog;
        this.animation = animation;
        this.clouds = clouds;
        this.weather = weather;
    }

    @Override
    public void onResponse(String response) {
        Document parsed = null;
        try {
            parsed = parseXML(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Node sunData = parsed.getElementsByTagName("sun").item(0);
        String rise = sunData.getAttributes().getNamedItem("rise").getNodeValue();
        String set = sunData.getAttributes().getNamedItem("set").getNodeValue();
        try {
            weather.setSunrise(rise);
            weather.setSunset(set);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //TODO: Do stuff with position of sun/moon

        Node temperatureData = parsed.getElementsByTagName("feels_like").item(0);
        weather.setTemperature(Double.parseDouble(temperatureData.getAttributes().getNamedItem("value").getNodeValue()));

        Node windData = parsed.getElementsByTagName("wind").item(0).getChildNodes().item(0);
        weather.setWindSpeed(Double.parseDouble(windData.getAttributes().getNamedItem("value").getNodeValue()));
        if(weather.getWindSpeed() > 0.0) {
            long swingSpeed = Math.round(60.0/weather.getWindSpeed() * 1000/2.0);
            animation.setDuration(swingSpeed);
            pendulum.startAnimation(animation);
        } else {
            pendulum.clearAnimation();
        }

        Node cloudData = parsed.getElementsByTagName("clouds").item(0);
        weather.setCloudiness((int) Math.floor(Integer.parseInt(cloudData.getAttributes().getNamedItem("value").getNodeValue()) / 10.0));
        for (int i = 0; i < 10; i++) {
            if (weather.getCloudiness() > i) {
                clouds.get(i).setVisibility(View.VISIBLE);
            } else {
                clouds.get(i).setVisibility(View.INVISIBLE);
            }

        }


        int visiblityData = Integer.parseInt(parsed.getElementsByTagName("visibility")
                .item(0).getAttributes().getNamedItem("value").getNodeValue());
        weather.setVisibility(1.0-(visiblityData / 10000.0));
        if (weather.getVisibility() > 0.75) {
            weather.setVisibility(0.75);
        }
        fog.setAlpha((float) weather.getVisibility());



        NamedNodeMap precipitationData = parsed.getElementsByTagName("precipitation").item(0).getAttributes();

        weather.setPrecipitationType(precipitationData.getNamedItem("mode").getNodeValue());

        switch (weather.getPrecipitationType()) {
            case "rain":
                rock.setImageResource(R.drawable.wetrock);
                weather.setPrecipitationAmount(Double.parseDouble(precipitationData
                        .getNamedItem("value").getNodeValue()));
                break;
            case "snow":
                rock.setImageResource(R.drawable.snowyrock);
                weather.setPrecipitationAmount(Double.parseDouble(precipitationData
                        .getNamedItem("value").getNodeValue()));
            default:
                rock.setImageResource(R.drawable.rock);
                weather.setPrecipitationAmount(0.0);
                break;
        }

    }

    private Document parseXML(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
