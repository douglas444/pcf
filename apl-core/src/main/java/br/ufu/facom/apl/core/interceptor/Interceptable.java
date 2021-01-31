package br.ufu.facom.apl.core.interceptor;

import java.util.HashMap;

public interface Interceptable {

    Log execute(final Interceptor interceptor,
                final HashMap<String, String> nominalParameters,
                final HashMap<String, Double> numericParameters);

}
