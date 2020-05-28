package com.v41.efcs.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.v41.efcs.R;
import com.v41.efcs.controller.TemperatureActivityController;
import com.v41.efcs.model.TemperatureActivityModel;
import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.Repository;

import java.util.ArrayList;
import java.lang.*;

public class TemperatureActivity extends BaseActivity implements IView, TemperatureView {

  private TemperatureActivityModel model;
  private TemperatureActivityController controller;

  private GraphView temperatureGraph;
  private LineGraphSeries<DataPoint> lowLimit;
  private LineGraphSeries<DataPoint> highLimit;

  private EditText inputLowLimit;
  private ImageButton btnLowLimitDecrease;
  private ImageButton btnLowLimitIncrease;

  private EditText inputHighLimit;
  private ImageButton btnHighLimitDecrease;
  private ImageButton btnHighLimitIncrease;

  private double step;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_temperature);
    init();
    show();
  }

  /**
   * Initialise le graphique de température
   */
  private void initGraph() {
    temperatureGraph = findViewById(R.id.hum_graph);
    GridLabelRenderer labelRenderer = temperatureGraph.getGridLabelRenderer();
    LegendRenderer lengendRenderer = temperatureGraph.getLegendRenderer();
    Viewport viewport = temperatureGraph.getViewport();

    // Initialisation du graphique
    labelRenderer.setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
    labelRenderer.setGridColor(getResources().getColor(R.color.text_disabled));
    labelRenderer.setHorizontalLabelsColor(getResources().getColor(R.color.text_disabled));
    labelRenderer.setVerticalLabelsColor(getResources().getColor(R.color.text_disabled));
    labelRenderer.setHighlightZeroLines(false);

    // Initialisation de la légende
    lengendRenderer.setVisible(true);
    lengendRenderer.setAlign(LegendRenderer.LegendAlign.TOP);
    lengendRenderer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    lengendRenderer.setTextColor(getResources().getColor(R.color.text));
    lengendRenderer.setTextSize(24);
    lengendRenderer.setSpacing(16);

    // Initialisation du viewport
    SensorData sensor = (SensorData) getIntentParcelable("sensor");
    if (sensor != null && sensor.getId() == SensorData.TEMPERATURE_ID) {
      viewport.setScalable(true);
      viewport.setScalableY(true);
      viewport.setXAxisBoundsManual(true);
      viewport.setMaxX(getSensorDataCount(sensor));

      setTemperatureData(sensor);
      setStepValue(viewport);
    }

    setLimits();

  }

  /**
   * Remplis le graphique avec les données d'un senseur
   *
   * @param sensor Senseur contenant les données
   */
  private void setTemperatureData(SensorData sensor) {
    ArrayList<DataPoint> values = new ArrayList<DataPoint>();
    for (int i = 0; i < sensor.getValues().length; i++) {
      values.add(new DataPoint(i, sensor.getValues()[i].getValue()));
    }

    LineGraphSeries<DataPoint> temperatureData = new LineGraphSeries<DataPoint>(values.toArray(new DataPoint[0]));
    temperatureGraph.addSeries(temperatureData);
    temperatureData.setColor(Color.parseColor("#5e81ac"));
    temperatureData.setTitle(getString(R.string.lbl_temperature_serie));
  }

  /**
   * Initialise les limites de température
   */
  private void setLimits() {
    lowLimit = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, model.getLowLimit()),
            new DataPoint(temperatureGraph.getViewport().getMaxX(true), model.getLowLimit())
    });
    lowLimit.setColor(Color.parseColor("#88c0d0"));
    lowLimit.setTitle(getString(R.string.lbl_low_limit));

    highLimit = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, model.getHighLimit()),
            new DataPoint(temperatureGraph.getViewport().getMaxX(true), model.getHighLimit())
    });
    highLimit.setColor(Color.parseColor("#b48ead"));
    highLimit.setTitle(getString(R.string.lbl_high_limit));

    temperatureGraph.addSeries(lowLimit);
    temperatureGraph.addSeries(highLimit);
  }

  /**
   * Initialisation des limites à partir des préférences
   */
  private void loadPreferences() {
    PreferencesData preferences = getRepository().findLast();
    if (preferences != null) {
      controller.onSetLowLimit(preferences.getTempLowLimit());
      controller.onSetHighLimit(preferences.getTempHighLimit());

      update();
    }
  }

  /**
   * Calcule la valeur d'incrémentation/décrémentation des limites
   *
   * @param viewPort
   */
  private void setStepValue(Viewport viewPort) {
    step = (viewPort.getMaxY(true) - viewPort.getMinY(true)) / 10;
  }

  //region Button setters

  /**
   * Initialisation du bouton de décrémentation de la limite minimale et de son listener
   */
  private void setBtnLowLimitDecrease() {
    btnLowLimitDecrease = findViewById(R.id.btn_low_limit_decrease);
    btnLowLimitDecrease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        controller.onLowLimitDecrease(step);
        show();
      }
    });
  }

  /**
   * Initialisation du bouton d'incrémentation de la limite minimale et de son listener
   */
  private void setBtnLowLimitIncrease() {
    btnLowLimitIncrease = findViewById(R.id.btn_low_limit_increase);
    btnLowLimitIncrease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        controller.onLowLimitIncrease(step);
        show();
      }
    });
  }

  /**
   * Initialisation du bouton de décrémentation de la limite maximale et de son listener
   */
  private void setBtnHighLimitDecrease() {
    btnHighLimitDecrease = findViewById(R.id.btn_high_limit_decrease);
    btnHighLimitDecrease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        controller.onHighLimitDecrease(step);
        show();
      }
    });
  }

  /**
   * Initialisation du bouton d'incrémentation de la limite maximale et de son listener
   */
  private void setBtnHighLimitIncrease() {
    btnHighLimitIncrease = findViewById(R.id.btn_high_limit_increase);
    btnHighLimitIncrease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        controller.onHighLimitIncrease(step);
        show();
      }
    });
  }
  //endregion

  //region EditText Setter

  /**
   * Initialisation du EditText de la valeur de la limite minimale et de son listener
   */
  public void setInputLowLimit() {
    inputLowLimit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        controller.onSetLowLimit(Double.parseDouble(inputLowLimit.getText().toString()));
        update();
      }
    });
  }

  /**
   * Initialisation du EditText de la valeur de la limite maximale et de son listener
   */
  public void setInputHighLimit() {
    inputHighLimit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        controller.onSetHighLimit(Double.parseDouble(inputHighLimit.getText().toString()));
        update();
      }
    });
  }
  //endregion

  /**
   * Initialisation des propriétés de la vue
   */
  @Override
  public void init() {
    model = new TemperatureActivityModel();
    controller = new TemperatureActivityController(this, model);

    initGraph();
    loadPreferences();

    inputHighLimit = findViewById(R.id.input_high_limit);
    inputLowLimit = findViewById(R.id.input_low_limit);

    setInputLowLimit();
    setInputHighLimit();

    setBtnHighLimitDecrease();
    setBtnHighLimitIncrease();
    setBtnLowLimitDecrease();
    setBtnLowLimitIncrease();
  }

  /**
   * Gestion de l'affichage de la vue
   */
  @Override
  public void show() {
    inputLowLimit.setText(String.valueOf(model.getLowLimit()));
    inputHighLimit.setText(String.valueOf(model.getHighLimit()));
  }

  /**
   * Gestion de la mise a jour de la vue depuis les informations du modèle
   */
  @Override
  public void update() {
    lowLimit.resetData(new DataPoint[]{
            new DataPoint(0, model.getLowLimit()),
            new DataPoint(temperatureGraph.getViewport().getMaxX(true), model.getLowLimit())
    });
    highLimit.resetData(new DataPoint[]{
            new DataPoint(0, model.getHighLimit()),
            new DataPoint(temperatureGraph.getViewport().getMaxX(true), model.getHighLimit())
    });

    PreferencesData old = getRepository().findLast();
    if (old.getKeepPreferences() == 1) {
      getRepository().save(new PreferencesData(old.getId(), old.getKeepPreferences(), model.getLowLimit(), model.getHighLimit(), toPrimitiveArray(old.getHumidityHighLimits())));
    }
  }

  @Override
  public Repository<PreferencesData> getRepository() {
    return new PreferencesRepository(getDatabase());
  }

  /**
   * Activation des boutons de limite
   *
   * @param enabled Activé si true, sinon désactivé
   */
  @Override
  public void setLimitsEnabled(boolean enabled) {
    btnLowLimitIncrease.setEnabled(enabled);
    btnHighLimitDecrease.setEnabled(enabled);
  }

  //region Méthodes tests unitaires

  /**
   * Retourne l'état d'activation des boutons de limite
   *
   * @return Vrai si activé sinon faux
   */
  @Override
  public boolean isLimitsEnabled() {
    return btnHighLimitDecrease.isEnabled() && btnLowLimitIncrease.isEnabled();
  }
  //endregion
}
