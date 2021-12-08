package net.blindking.weatherrock.controller;

import android.view.View;
import android.widget.TextView;

import net.blindking.weatherrock.model.Weather;

public class InfoController {
    private TextView dialog;
    private Weather weather;
    private String infoBookString;
    private String rockString;

    public InfoController(TextView dialog, Weather weather, String infoBookString, String rockString) {
        this.dialog = dialog;
        this.weather = weather;
        this.infoBookString = infoBookString;
        this.rockString = rockString;

        this.dialog.setOnClickListener(v -> {
            this.dialog.animate().setDuration(500).alpha(0.0f);
            this.dialog.setVisibility(View.GONE);
        });
    }

    private void showDialog() {
        this.dialog.setVisibility(View.VISIBLE);
        this.dialog.animate().setDuration(500).alpha(1.0f);
    }

    public void bookClicked() {
        this.dialog.setText(infoBookString);
        this.showDialog();
    }

    public void rockClicked() {
        String tempString = String.format(rockString, weather.getWindSpeed(), weather.getTemperature());
        this.dialog.setText(tempString);
        this.showDialog();
    }
}
