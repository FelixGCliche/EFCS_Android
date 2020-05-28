package com.v41.efcs.view.mock;

import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.Repository;
import com.v41.efcs.view.HumidityView;
import com.v41.efcs.view.IView;

public class HumidityActivityMock implements IView, HumidityView {
  private boolean isEnabled;

  public HumidityActivityMock(boolean _isEnabled) {
    isEnabled = _isEnabled;
  }

  @Override
  public void setApplyButtonEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  @Override
  public boolean isApplyButtonEnabled() {
    return isEnabled;
  }

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
}
