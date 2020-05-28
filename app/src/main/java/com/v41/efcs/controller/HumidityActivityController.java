package com.v41.efcs.controller;

import com.v41.efcs.model.HumidityModel;
import com.v41.efcs.model.Zone;
import com.v41.efcs.model.entity.HumidityZone;
import com.v41.efcs.model.exception.InvalidHighLimitException;
import com.v41.efcs.view.HumidityView;
import com.v41.efcs.view.IView;

public class HumidityActivityController {
  private HumidityModel model;
  private HumidityView view;

  public HumidityActivityController(HumidityModel _model, HumidityView _view) {
    model = _model;
    view = _view;
  }

  /**
   * Action à appeler au changement de la zone sélectionnée dans la vue
   * @param zone Zone à sélectionner
   */
  public void onZoneSelected(Zone zone) {
    model.setSelectedZone(zone);
  }

  /**
   * Action à appeler au changement de la limite haute de la zone sélectionnée dans la vue
   * @param newLimit Nouvelle limite
   */
  public void onCurrentLimitChanged(double newLimit) {
    int currentZoneIndex = model.getSelectedZoneIndex();
    view.setApplyButtonEnabled(true);

    Zone next;
    try {
      next = Zone.values()[currentZoneIndex + 1];
    } catch (IndexOutOfBoundsException e) {
      next = Zone.ZONE_5;
    }

    if (next == null) {
      if (newLimit <= model.getHighestValue() && newLimit > model.getLowerLimit(model.getSelectedZone())) {
        try {
          model.setCurrentZoneHighLimit(newLimit);
        } catch (InvalidHighLimitException e) {
          e.printStackTrace();
        }
      }
      else
        view.setApplyButtonEnabled(false);
    }
    else {
      if (newLimit <= model.getUpperLimit(next) && newLimit > model.getLowerLimit(model.getSelectedZone())) {
        try {
          model.setCurrentZoneHighLimit(newLimit);
        } catch (InvalidHighLimitException e) {
          e.printStackTrace();
        }
      }
      else
        view.setApplyButtonEnabled(false);
    }
  }
}
