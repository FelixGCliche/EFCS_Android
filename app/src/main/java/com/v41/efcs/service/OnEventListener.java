package com.v41.efcs.service;

/**
 * Interface de gestion du résultat d'un appel async
 * @param <T> Type de retour de l'appel async
 */
public interface OnEventListener<T> {
  /**
   * Gestion de l'appel async réussie
   * @param result Résultat de l'appel async
   */
  void onSuccess(T result);

  /**
   * Gestion de l'appel async échouée
   * @param e Exception lancée  par l'appel async
   */
  void onFailure(Exception e);
}
