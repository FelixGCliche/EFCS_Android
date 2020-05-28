package com.v41.efcs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.v41.efcs.R;
import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.Repository;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class PreferencesActivity extends BaseActivity implements IView {

  private Switch switchAutoSave;
  private Button btnSave;
  private Button btnCancel;

  private boolean autoSaveEnabled;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preferences);

    init();
  }

  //region Button init
  private void setSwitchAutoSave() {
    switchAutoSave = findViewById(R.id.switch_auto_save);
    switchAutoSave.setChecked(isAutoSaveEnabled());

    switchAutoSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setAutoSaveEnabled(isChecked);
      }
    });
  }

  private void setBtnCancel() {
    btnCancel = findViewById(R.id.btn_cancel);
    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void setBtnSave() {
    btnSave = findViewById(R.id.btn_save);
    btnSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onPreferencesSave();
      }
    });
  }

  private void setPreferences() {
    TextView[] preferencesTextViews = new TextView[]{
            findViewById(R.id.txt_temp_low_limit),
            findViewById(R.id.txt_temp_high_limit),
            findViewById(R.id.txt_z1_limit),
            findViewById(R.id.txt_z2_limit),
            findViewById(R.id.txt_z3_limit),
            findViewById(R.id.txt_z4_limit)};

    PreferencesData preferences = getRepository().findLast();
    for (int i = 0; i < preferencesTextViews.length; i++) {
      preferencesTextViews[i].setText(preferences.getTransactionableData()[i+1]);
    }

    if (preferences.getKeepPreferences() == 1)
      setAutoSaveEnabled(true);
    if (preferences.getKeepPreferences() == 0)
      setAutoSaveEnabled(false);
  }

  private void onPreferencesSave() {
    finish();
    PreferencesData old = getRepository().findLast();
    getRepository().save(new PreferencesData(old.getId(), isAutoSaveEnabled() ? 1 : 0, ((double) old.getTempLowLimit()), ((double) old.getTempHighLimit()), toPrimitiveArray(old.getHumidityHighLimits())));
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
  //endregion

  @Override
  public void init() {
    setPreferences();
    setSwitchAutoSave();
    setBtnCancel();
    setBtnSave();
  }

  @Override
  public void show() {

  }

  @Override
  public void update() {

  }

  @Override
  public Repository<PreferencesData> getRepository() {
    return new PreferencesRepository(getDatabase());
  }

  public boolean isAutoSaveEnabled() {
    return autoSaveEnabled;
  }

  public void setAutoSaveEnabled(boolean autoSaveEnabled) {
    this.autoSaveEnabled = autoSaveEnabled;
  }
}
