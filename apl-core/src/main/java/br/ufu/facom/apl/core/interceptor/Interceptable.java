package br.ufu.facom.apl.core.interceptor;

import java.util.HashMap;

public interface Interceptable {

    void execute(final Interceptor interceptor, final HashMap<String, String> parameters);

}
