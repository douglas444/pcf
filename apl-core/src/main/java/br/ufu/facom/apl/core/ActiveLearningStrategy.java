package br.ufu.facom.apl.core;

import java.util.HashMap;

public interface ActiveLearningStrategy {

    boolean isNovelty(final HashMap<String, String> parameters);
    boolean isKnown(final HashMap<String, String> parameters);

}
