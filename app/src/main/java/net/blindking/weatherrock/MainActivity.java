package net.blindking.weatherrock;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.android.volley.VolleyError;

import net.blindking.weatherrock.controller.InfoController;
import net.blindking.weatherrock.controller.MapController;
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
    private InfoController infoController;
    private MapController mapController;
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

        View book = findViewById(R.id.infoBook);
        TextView dialog = findViewById(R.id.dialog);

        infoController = new InfoController(dialog, weather, getString(R.string.infoBookContents), getString(R.string.rockDisplayText));

        book.setOnClickListener(v -> {
            infoController.bookClicked();
        });

        pendulum.setOnClickListener(v -> {

            infoController.rockClicked();
        });

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

        View fog = findViewById(R.id.fog);

        windowController = new WindowController(pendulum, rock, fog, animation, clouds, weather);
        openWeatherAPI = new OpenWeatherAPI(this, windowController);

        View map = findViewById(R.id.map);
        View zipDialog = findViewById(R.id.zipCodeDialog);
        EditText newZipCode = findViewById(R.id.editZipCode);
        Button enterZipCode = findViewById(R.id.enterZipCode);
        TextView currentZipCode = findViewById(R.id.currentZipCode);
        View zipExitButton = findViewById(R.id.zipExitButton);
        mapController = new MapController(this, map, zipDialog, newZipCode, enterZipCode, currentZipCode, openWeatherAPI, zipExitButton);
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 25);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 25:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }  else {
                    // FAILURE
                }
                return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        openWeatherAPI.getWeatherInformation(this.mapController.getZipCode());

    }

    @Override
    public void onPause() {
        pendulum.clearAnimation();
        super.onPause();
    }
}