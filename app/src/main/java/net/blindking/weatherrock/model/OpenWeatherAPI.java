package net.blindking.weatherrock.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.blindking.weatherrock.R;
import net.blindking.weatherrock.controller.WindowController;

import java.util.concurrent.atomic.AtomicBoolean;

public class OpenWeatherAPI {
    private RequestQueue requestQueue;
    private String apiKey;
//    private String zipCode;
    private WindowController controller;

    public OpenWeatherAPI(Context context, WindowController controller) {
        this.requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
//        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        this.apiKey = context.getString(R.string.openWeatherApiKey);
//        LocationManager locationManager = (LocationManager)
//                context.getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ((MainActivity) context).requestPermissions();
//        }
//        try {
//            Location location = locationManager.getLastKnownLocation(bestProvider);
//            this.zipCode = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getPostalCode();
//        } catch (Exception e) {
//            this.zipCode = "45810";
//        }
        this.controller = controller;
    }

//    public void getWeatherInformation() throws ExecutionException, InterruptedException, TimeoutException {
//        String url = "https://api.openweathermap.org/data/2.5/weather?zip="+
//                this.zipCode +",us&APPID="+
//                apiKey +"&units=imperial&mode=xml";
//
//        StringRequest request = new StringRequest(
//                url,
//                controller,
//                error -> {
//                    error.printStackTrace();
//                });
//        requestQueue.add(request);
//    }

    public void getWeatherInformation(String zipCode) {
        AtomicBoolean test = new AtomicBoolean(false);
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
