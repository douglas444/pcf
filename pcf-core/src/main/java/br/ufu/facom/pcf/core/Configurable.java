package br.ufu.facom.pcf.core;

import java.util.HashMap;

public interface Configurable {

    HashMap<String, String> getNominalParameters();
    HashMap<String, Double> getNumericParameters();

}
