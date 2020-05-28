package com.v41.efcs.controller;

import com.v41.efcs.model.IModel;
import com.v41.efcs.model.MainActivityModel;
import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.view.IView;

/**
 * Controlleur de MainActivity
 */
public class MainActivityController implements IController {

  final IView view;
  final MainActivityModel model;

  public MainActivityController(IView _view, MainActivityModel _model) {
    view = _view;
    model = _model;
  }

  /**
   * Retourne le senseur d'humidité depuis son ID dans la repository du modèle
   * @return Senseur d'humidité
   */
  public SensorData getHumiditySensor() {
    if(model.getRepository() != null) {
      return model.getRepository().find(SensorData.HUMIDITY_ID);
    }
    return null;
  }

  /**
   * Retourne le senseur de température depuis son ID dans la repository du modèle
   * @return Senseur de température
   */
  public SensorData getTemperatureSensor() {
    if(model.getRepository() != null) {
      return model.getRepository().find(SensorData.TEMPERATURE_ID);
    }
    return null;
  }
}
