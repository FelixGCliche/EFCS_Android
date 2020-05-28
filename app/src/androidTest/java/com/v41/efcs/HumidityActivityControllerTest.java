package com.v41.efcs;

import org.junit.Before;
import org.junit.Test;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.v41.efcs.controller.HumidityActivityController;
import com.v41.efcs.controller.TemperatureActivityController;
import com.v41.efcs.model.HumidityModel;
import com.v41.efcs.model.TemperatureModel;
import com.v41.efcs.model.Zone;
import com.v41.efcs.model.entity.HumidityZone;
import com.v41.efcs.model.entity.SensorValue;
import com.v41.efcs.model.exception.InvalidHighLimitException;
import com.v41.efcs.model.mock.HumidityActivityModelMock;
import com.v41.efcs.model.mock.TemperatureActivityModelMock;
import com.v41.efcs.view.HumidityActivity;
import com.v41.efcs.view.HumidityView;
import com.v41.efcs.view.mock.HumidityActivityMock;
import com.v41.efcs.view.mock.TemperatureActivityMock;
import com.v41.efcs.view.TemperatureView;

import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Tests unitaires du controlleur de HumidityActivity
 */
@RunWith(AndroidJUnit4.class)
public class HumidityActivityControllerTest {

  private HumidityModel model;
  private HumidityView view;

  @Before
  public void initialize() {
    model = new HumidityActivityModelMock(initMockSensorValues(100));
    view = new HumidityActivityMock(true);
  }

  private SensorValue[] initMockSensorValues(int nbValues) {
    SensorValue[] sensorValues = new SensorValue[nbValues];
    for (int i = 0; i < nbValues; i++) {
      sensorValues[i] = new SensorValue(0, i);
    }
    return sensorValues;
  }

  @Test
  public void canSetSelectedZone() {
    HumidityActivityController controller = new HumidityActivityController(model, view);

    controller.onZoneSelected(Zone.ZONE_1);
    assertTrue(model.getSelectedZone() == Zone.ZONE_1);

    controller.onZoneSelected(Zone.ZONE_2);
    assertTrue(model.getSelectedZone() == Zone.ZONE_2);

    controller.onZoneSelected(Zone.ZONE_3);
    assertTrue(model.getSelectedZone() == Zone.ZONE_3);

    controller.onZoneSelected(Zone.ZONE_4);
    assertTrue(model.getSelectedZone() == Zone.ZONE_4);

    controller.onZoneSelected(Zone.ZONE_5);
    assertTrue(model.getSelectedZone() == Zone.ZONE_5);
  }

  @Test
  public void canChangeZoneHighLimit() {
    HumidityActivityController controller = new HumidityActivityController(model, view);

    controller.onZoneSelected(Zone.ZONE_1);
    controller.onCurrentLimitChanged(30);

    assertTrue(model.getUpperLimit(Zone.ZONE_1) == 30);
  }

  @Test
  public void canNotChangeLastZoneHighLimit() {
    HumidityActivityController controller = new HumidityActivityController(model, view);

    controller.onZoneSelected(Zone.ZONE_5);
    controller.onCurrentLimitChanged(120);

    assertTrue(model.getUpperLimit(Zone.ZONE_5) == model.getHighestValue());
  }

  @Test
  public void shouldApplyLimitButtonBeEnabledIfLimitIsSetLowerThanNextZoneHighLimit() {
    HumidityActivityController controller = new HumidityActivityController(model, view);

    controller.onZoneSelected(Zone.ZONE_2);
    controller.onCurrentLimitChanged(30);

    assertTrue(view.isApplyButtonEnabled());
  }

  @Test
  public void shouldApplyLimitButtonBeDisabledIfLimitIsSetHigherThanNextZoneHighLimit() {
    HumidityActivityController controller = new HumidityActivityController(model, view);

    controller.onZoneSelected(Zone.ZONE_2);
    controller.onCurrentLimitChanged(120);

    assertTrue(!view.isApplyButtonEnabled());
  }

  @Test
  public void shouldApplyLimitButtonBeDisabledIfLimitIsSetLowerThanLowLimit() {
    HumidityActivityController controller = new HumidityActivityController(model, view);

    controller.onZoneSelected(Zone.ZONE_2);
    controller.onCurrentLimitChanged(10);

    assertTrue(!view.isApplyButtonEnabled());
  }

}
