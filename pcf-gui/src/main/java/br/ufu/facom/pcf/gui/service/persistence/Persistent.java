package br.ufu.facom.pcf.gui.service.persistence;

public interface Persistent {
    void reset();
    void load(final XMLConfiguration configuration);
    void save(final XMLConfiguration configuration);
}
