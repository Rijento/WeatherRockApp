package net.blindking.weatherrock.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import net.blindking.weatherrock.MainActivity;
import net.blindking.weatherrock.R;
import net.blindking.weatherrock.model.OpenWeatherAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class MapController {
    private String zipCode;
    private EditText newZipCode;
    private Button enterZipCode;
    private TextView currentZipCode;
    private View zipDialog;
    private View map;
    private View zipExitButton;
    private OpenWeatherAPI openWeatherAPI;

    public MapController(Context context,
                         View map,
                         View zipDialog,
                         EditText newZipCode,
                         Button enterZipCode,
                         TextView currentZipCode,
                         OpenWeatherAPI openWeatherAPI,
                         View zipExitButton) {
        this.map = map;
        this.zipDialog = zipDialog;
        this.newZipCode = newZipCode;
        this.enterZipCode = enterZipCode;
        this.currentZipCode = currentZipCode;
        this.openWeatherAPI = openWeatherAPI;
        this.zipExitButton = zipExitButton;

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ((MainActivity) context).requestPermissions();
        }
        try {
            Location location = locationManager.getLastKnownLocation(bestProvider);
            this.zipCode = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getPostalCode();
        } catch (Exception e) {
            this.zipCode = "45810";
        }

        this.map.setOnClickListener(v -> {
            this.onMapClicked();
        });

        this.enterZipCode.setOnClickListener(v -> {
            this.onButtonClicked();
        });

        this.zipExitButton.setOnClickListener(v -> {
            this.closeKeyboard();
            this.hideDialog();
        });

    }

    public String getZipCode() {
        return zipCode;
    }

    private void showDialog() {
        this.zipDialog.setVisibility(View.VISIBLE);
        this.zipDialog.animate().setDuration(500).alpha(1.0f);
    }
    private void hideDialog() {
        this.zipDialog.animate().setDuration(500).alpha(0.0f);
        this.zipDialog.setVisibility(View.GONE);
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)this.newZipCode.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.newZipCode.getWindowToken(), 0);
    }

    private void onMapClicked() {
        this.currentZipCode.setText(zipCode);
        this.newZipCode.setText("");
        this.showDialog();
    }

    private void onButtonClicked() {
        String newZip = this.newZipCode.getText().toString();
        if (newZip.matches("^[0-9]{5}")) {
            if (!newZip.equals(this.zipCode)) {
                this.openWeatherAPI.getWeatherInformation(newZip);
            }
            this.newZipCode.setHint("");
            this.closeKeyboard();
            this.hideDialog();
            this.zipCode = newZip;
        } else {
            this.newZipCode.setHint("INVALID");
            this.newZipCode.setText("");
            return;
        }
    }
}
