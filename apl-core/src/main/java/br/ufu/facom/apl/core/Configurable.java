package br.ufu.facom.apl.core;

import java.util.List;

public interface Configurable {

    List<String> getNominalParametersNames();
    List<String> getNumericParametersNames();

}
