package com.v41.efcs.view.mock;

import com.v41.efcs.controller.TemperatureActivityController;
import com.v41.efcs.model.TemperatureActivityModel;
import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.Repository;
import com.v41.efcs.view.IView;
import com.v41.efcs.view.TemperatureView;

public class TemperatureActivityMock implements IView, TemperatureView {

  private TemperatureActivityController controller;
  private TemperatureActivityModel model;

  private boolean limitsEnabled = true;

  @Override
  public void init() {

  }

  @Override
  public void show() {

  }

  @Override
  public void update() {

  }

  @Override
  public Repository<PreferencesData> getRepository() {
    return null;
  }

  @Override
  public void setLimitsEnabled(boolean enabled) {
    limitsEnabled = enabled;
  }

  @Override
  public boolean isLimitsEnabled() {
    return limitsEnabled;
  }
}
