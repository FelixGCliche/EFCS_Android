package com.v41.efcs.model;

import com.v41.efcs.model.entity.HumidityZone;
import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.model.entity.SensorValue;
import com.v41.efcs.model.exception.InvalidHighLimitException;
import java.util.Arrays;

public class HumidityActivityModel implements HumidityModel {
  public static final int NB_ZONES = 5;

  private HumidityZone[] zones = new HumidityZone[NB_ZONES];
  private HumidityZone selectedZone;
  private SensorValue[] values;

  public HumidityActivityModel(SensorData sensor) {
    values = sensor.getValues();
    Arrays.sort(values);

    for (int i = 0; i < NB_ZONES; i++) {
      zones[i] = new HumidityZone(Zone.values()[i]);
    }

    initHumidityZones();
    selectedZone = zones[0];
  }

  /**
   * Initie les valeurs et les limites des zones
   */
  private void initHumidityZones() {
    double initalStep = (values[values.length - 1].getValue() - values[0].getValue()) / NB_ZONES;

    for (int i = 0; i < NB_ZONES; i++) {
      if (i == 0)
        zones[0].setLowLimit(values[0].getValue());
      else
        zones[i].setLowLimit(zones[i - 1].getHighLimit());
      zones[i].setHighLimit(values[0].getValue() + (initalStep * (i + 1)));

      for (SensorValue value : values) {
        if (value.getValue() < zones[i].getHighLimit() && value.getValue() >= zones[i].getLowLimit() || i == 4 && value.getValue() == zones[i].getHighLimit())
          zones[i].addSensorValue(value);
      }
    }
  }

  /**
   * Modifie les valeurs et les limites des zones à partir d'une nouvelle limite de la zone sélectionnée
   * @param limit nouvelle limite de la zone sélectionné
   */
  private void updateZoneLimits(double limit) {
    int position = getSelectedZoneIndex() + 1;
    double step = (getHighestValue() - limit) / (NB_ZONES - position);

    for (int i = getSelectedZoneIndex(); i < NB_ZONES; i++) {
      zones[i].clearData();
      if (i == selectedZone.getId().getIndex())
        zones[i].setHighLimit(limit);
      else
        zones[i].setHighLimit(selectedZone.getHighLimit() + step * (i));

      if (i == 0)
        zones[i].setLowLimit(values[0].getValue());
      else
        zones[i].setLowLimit(getHumidityZone(zones[i - 1].getId()).getHighLimit());

      for (SensorValue value : values) {
        if (value.getValue() < zones[i].getHighLimit() && value.getValue() >= zones[i].getLowLimit())
          zones[i].addSensorValue(value);
        if (zones[i].getId() == Zone.ZONE_5 && value.getValue() == getHighestValue())
          zones[i].addSensorValue(value);
      }
    }
  }

  /**
   * Retourne la zone en fonction de son nom
   * @param zone Nom de la zone
   * @return Zone associé au nom
   */
  @Override
  public HumidityZone getHumidityZone(Zone zone) {
    for (HumidityZone humidityZone : zones) {
      if (humidityZone.getId() == zone)
        return humidityZone;
    }
    return null;
  }

  /**
   * Retourne la zone sélectionnée dans la vue
   * @return Zone sélectionnée dans la vue
   */
  @Override
  public Zone getSelectedZone() {
    return selectedZone.getId();
  }

  /**
   * Retourne l'index de la zone sélectionnée dans la vue
   * @return Index de la zone sélectionnée
   */
  @Override
  public int getSelectedZoneIndex() {
    return selectedZone.getId().getIndex();
  }

  /**
   * Change la zone sélectionnée dans la vue
   * @param zone Nom de la zone à sélectionner
   */
  @Override
  public void setSelectedZone(Zone zone) {
    selectedZone = getHumidityZone(zone);
  }

  /**
   * Retourne la limite haute d'une zone
   * @param zone Zone à vérifier
   * @return limite haute de la zone
   */
  @Override
  public double getUpperLimit(Zone zone) {
    return getHumidityZone(zone).getHighLimit();
  }

  /**
   * Retourne la limite basse d'une zone
   * @param zone Zone à vérifier
   * @return limite basse de la zone
   */
  @Override
  public double getLowerLimit(Zone zone) {
    return getHumidityZone(zone).getLowLimit();
  }

  /**
   * Retourne le nombre de données dans la zone
   * @param zone Zone à vérifier
   * @return Nombre de données dans la zone
   */
  @Override
  public int getCountInZone(Zone zone) {
    return getHumidityZone(zone).getCount();
  }

  /**
   * Modifie la limite haute de la zone sélectionnée dans la vue
   * @param newLimit Nouvelle limite de la zone
   * @throws InvalidHighLimitException
   */
  @Override
  public void setCurrentZoneHighLimit(double newLimit) throws InvalidHighLimitException {
    if (selectedZone.getId() != Zone.values()[Zone.values().length - 1]) {
      selectedZone.setHighLimit(newLimit);
      updateZoneLimits(newLimit);
    } else {
      throw new InvalidHighLimitException("Cannot modifiy high limit of last zone");
    }
  }

  /**
   * Modifie la limite basse de la zone sélectionnée dans la vue
   * @param newLimit Nouvelle limit basse
   */
  @Override
  public void setCurrentZoneLowLimit(double newLimit) {
    selectedZone.setLowLimit(newLimit);
  }

  /**
   * Retourne la valeur d'humidité la plus élevée du senseur
   * @return Valeur max du senseur
   */
  @Override
  public double getHighestValue() {
    return values[values.length - 1].getValue();
  }
}
