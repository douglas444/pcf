package br.ufu.facom.apl.core;

import java.util.HashMap;

public interface MetaCategorizer {

    Category categorize(final HashMap<String, String> parameters);

}
