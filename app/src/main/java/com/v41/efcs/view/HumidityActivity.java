package com.v41.efcs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.v41.efcs.R;
import com.v41.efcs.controller.HumidityActivityController;
import com.v41.efcs.model.HumidityActivityModel;
import com.v41.efcs.model.TemperatureActivityModel;
import com.v41.efcs.model.Zone;
import com.v41.efcs.model.entity.HumidityZone;
import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.model.entity.SensorValue;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.Repository;

import java.util.ArrayList;
import java.util.Arrays;

import static com.v41.efcs.model.HumidityActivityModel.NB_ZONES;

public class HumidityActivity extends BaseActivity implements IView, HumidityView {

  private HumidityActivityModel model;
  private HumidityActivityController controller;

  private Spinner spinnerZones;
  private TextView TextLowLimit;
  private EditText inputHighLimit;
  private Button btnApplyLimits;

  private GraphView humidityGraph;
  private BarGraphSeries<DataPoint> zones;
  BarGraphSeries<DataPoint> humidityData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_humidity);

    init();
  }

  /**
   * Initialise le graphique de température
   */
  private void initGraph() {
    humidityGraph = findViewById(R.id.hum_graph);
    GridLabelRenderer labelRenderer = humidityGraph.getGridLabelRenderer();
    LegendRenderer lengendRenderer = humidityGraph.getLegendRenderer();
    Viewport viewport = humidityGraph.getViewport();

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
    if (sensor != null && sensor.getId() == SensorData.HUMIDITY_ID) {
      viewport.setScalable(true);
      viewport.setScalableY(true);
      viewport.setXAxisBoundsManual(true);
      viewport.setMaxX(NB_ZONES + 1);

      setHumidityData();
    }

  }

  /**
   * Initie les données du graphique d'humidité
   */
  private void setHumidityData() {
    ArrayList<DataPoint> values = new ArrayList<DataPoint>();

    for (int i = 0; i < NB_ZONES; i++) {
      HumidityZone zone = model.getHumidityZone(Zone.values()[i]);
      values.add(new DataPoint(i + 1, zone.getCount()));
    }

    humidityData = new BarGraphSeries<DataPoint>(values.toArray(new DataPoint[0]));
    humidityGraph.addSeries(humidityData);
    humidityData.setSpacing(24);
    humidityData.setColor(Color.parseColor("#5e81ac"));
    humidityData.setTitle(getString(R.string.lbl_humidity_serie));
    humidityData.setDrawValuesOnTop(true);
    humidityData.setValuesOnTopColor(getResources().getColor(R.color.text));
  }

  /**
   * Initie le boutton de mise à jour du changement de limite
   */
  private void setApplyButton() {
    btnApplyLimits = findViewById(R.id.btn_apply_limits);
    btnApplyLimits.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        update();
      }
    });
  }

  /**
   * Initie le combobBox permettant de choisir une zone
   */
  private void initSpinner() {
    ArrayAdapter<Zone> adapter = new ArrayAdapter<Zone>(this, android.R.layout.simple_spinner_item, Zone.values());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerZones = findViewById(R.id.spinner_zones);
    spinnerZones.setAdapter(adapter);

    spinnerZones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        controller.onZoneSelected(Zone.values()[position]);
        show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  //region EditText Setter

  /**
   * Initialisation du EditText de la valeur de la limite minimale et de son listener
   */
  public void setInputLowLimit() {
    TextLowLimit = findViewById(R.id.input_low_limit);
    TextLowLimit.setText(String.valueOf(model.getLowerLimit(model.getSelectedZone())));
  }

  /**
   * Initialisation du EditText de la valeur de la limite maximale et de son listener
   */
  public void setInputHighLimit() {
    inputHighLimit = findViewById(R.id.input_high_limit);
    inputHighLimit.setText(String.valueOf(model.getUpperLimit(model.getSelectedZone())));
    inputHighLimit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        try {
          controller.onCurrentLimitChanged(Double.parseDouble(inputHighLimit.getText().toString()));
        } catch (NumberFormatException e) {
          controller.onCurrentLimitChanged(0);
        }
      }
    });
  }
  //endregion

  /**
   * Initialisation des limites à partir des préférences
   */
  private void loadPreferences() {
    PreferencesData preferences = getRepository().findLast();

    if (preferences != null) {
      for (int i = 0; i < NB_ZONES - 1; i++) {
        controller.onZoneSelected(Zone.values()[i]);
        controller.onCurrentLimitChanged(preferences.getHumidityHighLimits()[i]);
      }
      update();
    }
  }


  @Override
  public void init() {
    model = new HumidityActivityModel((SensorData) getIntentParcelable("sensor"));
    controller = new HumidityActivityController(model, this);

    initSpinner();

    setInputLowLimit();
    setInputHighLimit();
    setApplyButton();

    initGraph();
    loadPreferences();
  }

  @Override
  public void show() {
    TextLowLimit.setText(String.valueOf(model.getLowerLimit(model.getSelectedZone())));
    inputHighLimit.setText(String.valueOf(model.getUpperLimit(model.getSelectedZone())));

    if (model.getSelectedZone() == Zone.ZONE_5)
      inputHighLimit.setEnabled(false);
  }

  @Override
  public void update() {
    ArrayList<DataPoint> values = new ArrayList<DataPoint>();
    double[] preferencesLimits = new double[4];

    for (int i = 0; i < NB_ZONES; i++) {
      HumidityZone zone = model.getHumidityZone(Zone.values()[i]);
      values.add(new DataPoint(i + 1, zone.getCount()));

      if (i < preferencesLimits.length)
        preferencesLimits[i] = zone.getHighLimit();
    }

    PreferencesData old = getRepository().findLast();
    if (old.getKeepPreferences() == 1) {
      getRepository().save(new PreferencesData(old.getId(), old.getKeepPreferences(), old.getTempLowLimit(), old.getTempHighLimit(), preferencesLimits));
    }
    humidityData.resetData(values.toArray(new DataPoint[0]));
  }

  @Override
  public Repository<PreferencesData> getRepository() {
    return new PreferencesRepository(getDatabase());
  }

  /**
   * Activation du boutton de limite
   * @param enabled Activé si true, sinon désactivé
   */
  @Override
  public void setApplyButtonEnabled(boolean enabled) {
    btnApplyLimits.setEnabled(enabled);
  }

  @Override
  public boolean isApplyButtonEnabled() {
    return btnApplyLimits.isEnabled();
  }
}
