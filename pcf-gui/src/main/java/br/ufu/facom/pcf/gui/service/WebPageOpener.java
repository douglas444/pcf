package br.ufu.facom.pcf.gui.service;

import br.ufu.facom.pcf.gui.components.singleton.MainFrame;
import br.ufu.facom.pcf.gui.exception.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WebPageOpener {

    private static void openWebpage(URI uri) throws ServiceException {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return;
            } catch (Exception e) {
                throw new ServiceException("Error while opening README URL", e);
            }
        }
        final String message = "Visit https://github.com/douglas444/pcf/blob/main/README.md " +
                "for information about the software";

        JOptionPane.showMessageDialog(MainFrame.getInstance(), message,
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void openWebpage(URL url) throws ServiceException {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
