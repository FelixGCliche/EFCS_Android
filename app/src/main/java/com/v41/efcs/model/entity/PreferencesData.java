package com.v41.efcs.model.entity;

import androidx.annotation.Nullable;

public class PreferencesData {

  private long id;
  private int keepPreferences;
  private double tempLowLimit;
  private double tempHighLimit;

  private double[] humidityHighLimits;

  public PreferencesData(long id, int keepPreferences, double tempLowLimit, double tempHighLimit, double[] humidityHighLimits) {
    this.id = id;
    this.keepPreferences = keepPreferences;
    this.tempLowLimit = tempLowLimit;
    this.tempHighLimit = tempHighLimit;
    this.humidityHighLimits = humidityHighLimits;
  }

  public PreferencesData(long _id, boolean _keepPreferences, double _tempLowLimit, double _tempHighLimit, double[] _humidityHighLimits) {
    id = _id;
    keepPreferences = _keepPreferences ? 1 : 0;
    tempLowLimit = _tempLowLimit;
    tempHighLimit = _tempHighLimit;
    humidityHighLimits = _humidityHighLimits;
  }

  /**
   * Retourne la liste de string nécéssaire à une insertion via un repository
   *
   * @return liste de string
   */
  public String[] getTransactionableData() {
    return new String[]{
            String.valueOf(keepPreferences),
            String.valueOf(tempLowLimit),
            String.valueOf(tempHighLimit),
            String.valueOf(humidityHighLimits[0]),
            String.valueOf(humidityHighLimits[1]),
            String.valueOf(humidityHighLimits[2]),
            String.valueOf(humidityHighLimits[3])
    };
  }

  /**
   * Retourne la liste de string nécéssaire à une mise à jour via un repository
   *
   * @return
   */
  public String[] getUpdateData() {
    return new String[]{
            String.valueOf(keepPreferences),
            String.valueOf(tempLowLimit),
            String.valueOf(tempHighLimit),
            String.valueOf(humidityHighLimits[0]),
            String.valueOf(humidityHighLimits[1]),
            String.valueOf(humidityHighLimits[2]),
            String.valueOf(humidityHighLimits[3]),
            String.valueOf(id)
    };
  }

  public long getId() {
    return id;
  }

  public int getKeepPreferences() {
    return keepPreferences;
  }

  public Double getTempLowLimit() {
    return tempLowLimit;
  }

  public Double getTempHighLimit() {
    return tempHighLimit;
  }

  public Double[] getHumidityHighLimits() {
    Double[] limits = new Double[]{
            (Double) humidityHighLimits[0],
            (Double) humidityHighLimits[1],
            (Double) humidityHighLimits[2],
            (Double) humidityHighLimits[3]
    };
    return limits;
  }

  public void setZoneLimits(double[] limits) {
    humidityHighLimits = limits;
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    PreferencesData data;
    try {
      data = (PreferencesData) obj;
    } catch (ClassCastException e) {
      return false;
    }

    boolean areSameHumidityLimits = true;
    for (int i = 0; i < getHumidityHighLimits().length; i++) {
      areSameHumidityLimits = getHumidityHighLimits()[i].equals(data.getHumidityHighLimits()[i]);
    }

    return keepPreferences == data.getKeepPreferences() &&
            getTempLowLimit().equals(data.getTempLowLimit()) &&
            getTempHighLimit().equals(data.getTempHighLimit()) &&
            areSameHumidityLimits;
  }
}
