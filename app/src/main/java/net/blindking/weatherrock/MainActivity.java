package net.blindking.weatherrock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.blindking.weatherrock.controller.WindowController;
import net.blindking.weatherrock.model.OpenWeatherAPI;
import net.blindking.weatherrock.model.Weather;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends Activity {
    private View pendulum;
    private OpenWeatherAPI openWeatherAPI;
    private WindowController windowController;
    private Weather weather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActionBar()!=null)
            this.getActionBar().hide();
        setContentView(R.layout.main);

        weather = new Weather();

        pendulum = findViewById(R.id.pendulum);
        ImageView rock = findViewById(R.id.pendulumWeight);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.swinging);

        ArrayList<View> clouds = new ArrayList<>();
        clouds.add(findViewById(R.id.cloud1));
        clouds.add(findViewById(R.id.cloud2));
        clouds.add(findViewById(R.id.cloud3));
        clouds.add(findViewById(R.id.cloud4));
        clouds.add(findViewById(R.id.cloud5));
        clouds.add(findViewById(R.id.cloud6));
        clouds.add(findViewById(R.id.cloud7));
        clouds.add(findViewById(R.id.cloud8));
        clouds.add(findViewById(R.id.cloud9));
        clouds.add(findViewById(R.id.cloud10));

        windowController = new WindowController(pendulum, rock, animation, clouds, weather);

        openWeatherAPI = new OpenWeatherAPI(this, windowController);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            openWeatherAPI.getWeatherInformation();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        pendulum.clearAnimation();
        super.onPause();
    }
}