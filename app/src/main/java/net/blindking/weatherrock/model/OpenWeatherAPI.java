package net.blindking.weatherrock.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.blindking.weatherrock.R;
import net.blindking.weatherrock.controller.WindowController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class OpenWeatherAPI {
    private RequestQueue requestQueue;
    private String apiKey;
    private String zipCode;
    private WindowController controller;

    public OpenWeatherAPI(Context context, WindowController controller) {
        this.requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        this.apiKey = context.getString(R.string.openWeatherApiKey);
        this.zipCode = "45810";
        this.controller = controller;
    }

    public void getWeatherInformation() throws ExecutionException, InterruptedException, TimeoutException {
        String url = "https://api.openweathermap.org/data/2.5/weather?zip="+
                zipCode +",us&APPID="+
                apiKey +"&units=imperial&mode=xml";

        StringRequest request = new StringRequest(
                url,
                controller,
                error -> {
                    error.printStackTrace();
                });
        requestQueue.add(request);
    }

}
