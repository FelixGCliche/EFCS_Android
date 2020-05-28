package com.v41.efcs.controller;

import com.v41.efcs.model.TemperatureActivityModel;
import com.v41.efcs.model.TemperatureModel;
import com.v41.efcs.view.IView;
import com.v41.efcs.view.TemperatureView;

public class TemperatureActivityController implements IController {

  final TemperatureView view;
  final TemperatureModel model;

  public TemperatureActivityController(TemperatureView _view, TemperatureModel _model) {
    model = _model;
    view = _view;
  }

  /**
   * Incrémentation de la limite minimale
   * @param step Valeur d'incrémentation
   */
  public void onHighLimitIncrease(double step) {
    if (model.getHighLimit() - step >= model.getLowLimit())
      view.setLimitsEnabled(true);

    model.setHighLimit(model.getHighLimit() + step);
  }

  /**
   * Décrémentation de la limite minimale
   * @param step Valeur de décrémentation
   */
  public void onHighLimitDecrease(double step) {
    if (model.getHighLimit() - step >= model.getLowLimit()) {
      view.setLimitsEnabled(true);
      model.setHighLimit(model.getHighLimit() - step);
    } else {
      view.setLimitsEnabled(false);
    }

  }

  /**
   * Incrémentation de la limite maximale
   * @param step Valeur d'incrémentation
   */
  public void onLowLimitIncrease(double step) {
    if (model.getLowLimit() + step <= model.getHighLimit()) {
      model.setLowLimit(model.getLowLimit() + step);
    } else {
      view.setLimitsEnabled(false);
    }

  }

  /**
   * Décrémentation de la limite maximale
   * @param step Valeur de décrémentation
   */
  public void onLowLimitDecrease(double step) {
    if (model.getLowLimit() + step <= model.getHighLimit())
      view.setLimitsEnabled(true);

    model.setLowLimit(model.getLowLimit() - step);
  }

  /**
   * Change la valeur de la limite minimale
   * @param limit Nouvelle limite
   */
  public void onSetLowLimit(double limit) {
    if (limit <= model.getHighLimit())
      model.setLowLimit(limit);
  }

  /**
   * Change la valeur de la limite maximale
   * @param limit Nouvelle limite
   */
  public void onSetHighLimit(double limit) {
    if (limit >= model.getLowLimit())
      model.setHighLimit(limit);
  }
}
