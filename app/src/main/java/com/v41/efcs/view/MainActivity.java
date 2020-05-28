package com.v41.efcs.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.v41.efcs.R;
import com.v41.efcs.controller.MainActivityController;
import com.v41.efcs.model.MainActivityModel;
import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.Repository;

public class MainActivity extends BaseActivity implements IView {

  public static final String API_URL = "https://v41-mobile-api.herokuapp.com/sensordata";

  MainActivityModel model;
  MainActivityController controller;

  private Button btnHumidity;
  private Button btnTemperature;
  ProgressBar progressBar;

  private TextView msgWindow;
  private StringBuilder msgTxt = new StringBuilder();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
    update();

    if (savedInstanceState != null)
      msgWindow.setText(savedInstanceState.getString("msgText"));
  }

  /**
   * Gestion de la réception de l'URL d'API depuis un intent implicite
   * @param intent Intent de l'activité
   * @return URL de l'API
   */
  private String getURL(Intent intent) {
    if ("text/plain".equals(intent.getType()))
      return intent.getStringExtra(Intent.EXTRA_TEXT);
    else
      return API_URL;
  }

  /**
   * Ajoute une ligne de texte dans la boite de messages
   * @param text Texte à ajouter
   */
  private void printMessageLine(String text) {
    msgTxt.append(text).append('\n');
  }

  /**
   * Gestion de la réception de senseurs depuis un intent explicite
   */
  private void getSensorData() {
    printMessageLine(getIntent().getStringExtra("msgText"));
    printMessageLine("Current URL: " + getURL(getIntent()));

    SensorData sensorData = (SensorData) getIntentParcelable("sensor");
    if (sensorData != null) {
      printMessageLine(getString(R.string.sensor_data_receive_msg));
      printMessageLine(String.format("%d datas received", getSensorDataCount(sensorData)));
      printMessageLine(sensorData.toString());
    } else {
      printMessageLine(getString(R.string.sensor_data_receive_error_msg));
    }
  }

  /**
   * Initialisation du boutton humidité et de ses Listeners
   */
  private void setBtnHumidity() {
    btnHumidity = findViewById(R.id.btn_humidity);
    btnHumidity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        printMessageLine(getString(R.string.humidity_send_msg));
        if (controller.getHumiditySensor() != null) {
          sendSensor(controller.getHumiditySensor());
        } else {
          printMessageLine(getString(R.string.humidity_send_error_msg));
        }
        msgWindow.setText(msgTxt.toString());
      }
    });
  }

  /**
   * Initialisation du boutton température et de ses Listeners
   */
  private void setBtnTemperature() {
    btnTemperature = findViewById(R.id.btn_temperature);
    btnTemperature.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        printMessageLine(getString(R.string.temperature_send_msg));
        if (controller.getTemperatureSensor() != null) {
          sendSensor(controller.getTemperatureSensor());
        } else {
          printMessageLine(getString(R.string.temperature_send_error_msg));
        }
        msgWindow.setText(msgTxt.toString());
      }
    });
  }

  /**
   * Envoie les données d'un senseur en Parcelable dans un intent
   * @param sensorData Senseur à envoyer
   */
  private void sendSensor(SensorData sensorData) {
//    finish();

    Intent intent;
    if (sensorData.getId() == SensorData.TEMPERATURE_ID)
       intent = new Intent(this, TemperatureActivity.class);
    else if (sensorData.getId() == SensorData.HUMIDITY_ID)
      intent = new Intent(this, HumidityActivity.class);
    else
      intent = new Intent();

    intent.putExtra("sensor", sensorData);
    intent.putExtra("msgText", msgTxt.toString());
    startActivity(intent);
  }

  @Override
  public void init() {
    progressBar = findViewById(R.id.api_progress_bar);

    getURL(getIntent());

    model = new MainActivityModel();
    controller = new MainActivityController(this, model);

    model.initRepository(getURL(getIntent()));

    msgWindow = findViewById(R.id.msg_txt);
    msgWindow.setMovementMethod(new ScrollingMovementMethod());

    setBtnHumidity();
    setBtnTemperature();
  }

  @Override
  public void show() {
    msgWindow.setGravity(Gravity.BOTTOM);
    msgWindow.setText(msgTxt);
  }

  @Override
  public void update() {
    Intent intent = getIntent();

    if (intent.hasExtra("sensor") && getIntent().hasExtra("msgText"))
      getSensorData();

    show();
  }

  @Override
  public Repository<PreferencesData> getRepository() {
    return new PreferencesRepository(getDatabase());
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    outState.putString("msgText", msgTxt.toString());
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    msgTxt.append(savedInstanceState.getString("msgText"));
    super.onRestoreInstanceState(savedInstanceState);
  }
}
