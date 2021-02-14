package br.ufu.facom.pmc.core;

import java.util.HashMap;

public interface Configurable {

    HashMap<String, String> getNominalParameters();
    HashMap<String, Double> getNumericParameters();

}
