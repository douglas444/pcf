package br.ufu.facom.apl.gui.components.singleton;

import br.ufu.facom.apl.core.ActiveLearningStrategy;
import br.ufu.facom.apl.core.MetaCategorizer;
import br.ufu.facom.apl.core.interceptor.Interceptable;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private static MenuBar instance;

    private final JMenuItem itemRun;
    private final JMenuItem itemStop;
    private final JMenuItem itemNew;
    private final JMenuItem itemLoad;
    private final JMenuItem itemSave;
    private final JMenuItem itemExit;
    private final JMenu menuFile;
    private final JMenu menuHelp;
    private final JMenu menuRun;

    private MenuBar() {

        this.itemRun = new JMenuItem("Run...");
        this.itemStop = new JMenuItem("Stop");
        this.itemNew = new JMenuItem("New");
        this.itemLoad = new JMenuItem("Load");
        this.itemSave = new JMenuItem("Save");
        this.itemExit = new JMenuItem("Exit");
        this.menuFile = new JMenu("File");
        this.menuHelp = new JMenu("Help");
        this.menuRun = new JMenu("Run");

        menuFile.add(itemNew);
        menuFile.add(itemLoad);
        menuFile.add(itemSave);
        menuFile.add(itemExit);
        menuRun.add(itemRun);
        menuRun.add(itemStop);
        this.add(menuFile);
        this.add(menuRun);
        this.add(menuHelp);

    }

    public static MenuBar getInstance() {

        if (instance == null) {
            instance = new MenuBar();
        }
        return instance;

    }

    static {

        MenuBar.getInstance().getItemExit().addActionListener((event) -> {

        });

        MenuBar.getInstance().getItemLoad().addActionListener((event) -> {

        });

        MenuBar.getInstance().getItemNew().addActionListener((event) -> {

        });

        MenuBar.getInstance().getItemRun().addActionListener((event) -> {

            FooterPanel.getInstance().setVisible(true);

            final Interceptable interceptable = (Interceptable) ConfiguratorPanel.getInstance()
                    .getInterceptableConfigurator().getSelectedInstance();
            ;
            final MetaCategorizer metaCategorizer = (MetaCategorizer) ConfiguratorPanel.getInstance()
                    .getMetaCategorizerConfigurator().getSelectedInstance();
            final ActiveLearningStrategy activeLearningStrategy = (ActiveLearningStrategy)
                    ConfiguratorPanel.getInstance().getActiveLearningStrategyConfigurator().getSelectedInstance();

            interceptable.execute(null, ConfiguratorPanel.getInstance()
                    .getInterceptableConfigurator().getParameterValueByName());

            metaCategorizer.categorize(ConfiguratorPanel.getInstance()
                    .getMetaCategorizerConfigurator().getParameterValueByName());

            activeLearningStrategy.isKnown(ConfiguratorPanel.getInstance()
                    .getActiveLearningStrategyConfigurator().getParameterValueByName());

            activeLearningStrategy.isNovelty(ConfiguratorPanel.getInstance()
                    .getActiveLearningStrategyConfigurator().getParameterValueByName());

            FooterPanel.getInstance().setVisible(false);

        });

        MenuBar.getInstance().getItemSave().addActionListener((event) -> {


        });

        MenuBar.getInstance().getItemStop().addActionListener((event) -> {

        });

        MenuBar.getInstance().getMenuFile().addActionListener((event) -> {

        });

        MenuBar.getInstance().getMenuHelp().addActionListener((event) -> {

        });
    }

    public JMenuItem getItemRun() {
        return itemRun;
    }

    public JMenuItem getItemStop() {
        return itemStop;
    }

    public JMenuItem getItemNew() {
        return itemNew;
    }

    public JMenuItem getItemLoad() {
        return itemLoad;
    }

    public JMenuItem getItemSave() {
        return itemSave;
    }

    public JMenuItem getItemExit() {
        return itemExit;
    }

    public JMenu getMenuFile() {
        return menuFile;
    }

    public JMenu getMenuHelp() {
        return menuHelp;
    }

    public JMenu getMenuRun() {
        return menuRun;
    }
}
