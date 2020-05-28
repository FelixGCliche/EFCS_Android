package com.v41.efcs.view;

import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.Repository;

import java.util.prefs.Preferences;

public interface IView {
  /**
   * Initialisation des propriétés de la vue
   */
  void init();

  /**
   * Affichage des mises à jour de la vue
   */
  void show();

  /**
   * Gestion des mises à jour de la vue
   */
  void update();

  Repository<PreferencesData> getRepository();
}
