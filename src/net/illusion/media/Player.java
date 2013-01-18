package net.illusion.media;

import com.rsbuddy.api.gui.Location;
import com.rsbuddy.plugin.WidgetPluginBase;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.layout.Pane;
import net.illusion.media.structs.MediaStorage;
import net.illusion.media.ui.MediaPlayerPane;

import java.util.EnumSet;

/**
 * Author: Kyle
 * Project: Player [net.illusion.media]
 * Created: 1/11/13 [6:11 PM]
 * Description:
 */
public class Player extends WidgetPluginBase {

    public static final String PLUGIN_NAME = "Media Player";

    public static final String ICON_NON_ACTIVE = "img/normal.png";
    public static final String ICON_ACTIVE = "img/active.png";
    public static final String ICON_HOVER = "img/hover.png";

    public static final String STYLE_SHEET = "css/style.css";


    private MediaPlayerPane playerPane = null;
    private Pane mainPane = null;
    private Accordion settingsAccordion = null;

    private MediaStorage mediaStorage = null;


    public Player() {
        super(PLUGIN_NAME, ICON_NON_ACTIVE, ICON_HOVER, ICON_ACTIVE);
    }

    public Node content(Location location) {

        mediaStorage = new MediaStorage();

        mainPane = new Pane();
        playerPane = new MediaPlayerPane();
        settingsAccordion = new Accordion();


        playerPane.setMediaStorage(mediaStorage);
        playerPane.initalizeUI();


        try {

            String css = getClass().getResource(STYLE_SHEET).toExternalForm();
            mainPane.getStylesheets().add(css);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        mainPane.setMaxWidth(40);
        mainPane.getChildren().addAll(playerPane, settingsAccordion);


        return mainPane;


    }

    public EnumSet<Location> supportedLocations() {
        return EnumSet.of(Location.LEFT);
    }

}
