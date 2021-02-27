package br.ufu.facom.pcf.core;

public interface Interceptable {
    boolean execute(final Interceptor interceptor);
    void stop();
}
