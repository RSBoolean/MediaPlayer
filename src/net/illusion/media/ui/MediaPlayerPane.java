package net.illusion.media.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import net.illusion.media.Player;
import net.illusion.media.structs.impl.MediaFile;
import net.illusion.media.structs.MediaStorage;

/**
 * Author: Kyle
 * Project: Player [net.illusion.media.ui]
 * Created: 1/11/13 [7:15 PM]
 * Description:
 */

public class MediaPlayerPane extends BorderPane {

    public static final String ICON_PLAY = "/img/play.png";
    public static final String ICON_PAUSE = "/img/pause.png";
    public static final String ICON_NEXT = "/img/next.png";
    public static final String ICON_PREVIOUS = "/img/previous.png";
    public static final String ICON_VOLUME_SLIDER = "img/slider";

    public static final String STYLE_SHEET = "/css/style.css";

    private ImageView playImage = null;
    private ImageView pauseImage = null;
    private ImageView nextImage = null;
    private ImageView previousImage = null;

    private Button playPauseButton = null;
    private Button nextButton = null;
    private Button previousButton = null;

    private TextField searchBar = null;

    private ProgressBar songProgress = null;
    private Slider volumeControl = null;

    private Label albumnArtwork = null;
    private Label currentRunningTime = null;
    private Label songLength = null;
    private Label songName = null;
    private Label artistName = null;

    private MediaStorage mediaStorage = null;
    private MediaPlayer player = null;
    private MediaFile currentSong = null;


    public void initalizeUI() {


        String css = Player.class.getResource(STYLE_SHEET).toExternalForm();

        getStylesheets().add(css);

       /* if (playImage == null)
            playImage = new ImageView(new Image(Player.class.getResource(ICON_PLAY).toExternalForm()));

        playImage.setFitWidth(32);
        playImage.setFitHeight(32);

        if (pauseImage == null)
            pauseImage = new ImageView(new Image(Player.class.getResource(ICON_PAUSE).toExternalForm()));

        pauseImage.setFitWidth(32);
        pauseImage.setFitHeight(32);

        if (nextImage == null)
            nextImage = new ImageView(new Image(Player.class.getResource(ICON_NEXT).toExternalForm()));

        nextImage.setFitHeight(16);
        nextImage.setFitWidth(16);

        if (previousImage == null)
            previousImage = new ImageView(new Image(Player.class.getResource(ICON_PREVIOUS).toExternalForm()));

        previousImage.setFitWidth(16);
        previousImage.setFitHeight(16);

        playPauseButton = new Button("", playImage);
        playPauseButton.setMaxSize(32, 32);

        nextButton = new Button("", nextImage);
        nextButton.setMaxSize(16, 16);
        previousButton = new Button("", previousImage);
        previousButton.setMaxSize(16, 16);         */

        songProgress = new ProgressBar(0);

        volumeControl = new Slider();
        volumeControl.setMin(0);
        volumeControl.setValue(.5);
        volumeControl.setMax(1);
        volumeControl.setBlockIncrement(0.05);

        volumeControl.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                volumeChange(new_val.doubleValue());
            }
        });

        albumnArtwork = new Label();
        currentRunningTime = new Label("0:00");
        songLength = new Label("0:00");

        songName = new Label();
        artistName = new Label();


      /*  playPauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handlePlayPause();
            }
        }); */


        searchBar = new TextField();
        searchBar.setId("search-bar");

     //   VBox info = playerBox();

        setCenter(searchBar);


    }

    public void setMediaStorage(MediaStorage storage) {
        mediaStorage = storage;
    }

    private VBox playerBox() {

        VBox player = new VBox();
        player.setSpacing(10);

        HBox info = infoBox();

        info.setAlignment(Pos.CENTER);

        VBox control = controlBox();

        player.getChildren().addAll(info, control);

        return player;

    }

    private VBox controlBox() {
        VBox vertical = new VBox();
        vertical.setSpacing(10);

        HBox controls = new HBox();
        controls.setSpacing(2);

        controls.getChildren().addAll(previousButton, playPauseButton, nextButton);

        vertical.getChildren().addAll(volumeControl, controls);

        return vertical;
    }


    private HBox infoBox() {

        HBox area = new HBox();

        albumnArtwork.setPrefSize(100, 100);

        VBox info = new VBox();

        info.setSpacing(10);

        songName.setText("Song");
        artistName.setText("Artist");

        HBox songLengthInfo = new HBox();

        songLengthInfo.setAlignment(Pos.CENTER);

        songLengthInfo.getChildren().addAll(currentRunningTime, songProgress, songLength);

        info.getChildren().addAll(songName, artistName, songLengthInfo);

        area.getChildren().addAll(info);


        return area;

    }


    private void volumeChange(double newValue) {

        if (player != null)
            player.setVolume(newValue);

    }

    private void handlePlayPause() {

        if (player != null) {

            if (player.getStatus() == MediaPlayer.Status.PLAYING) {

                playPauseButton.setGraphic(pauseImage);
                player.pause();

            } else if (player.getStatus() == MediaPlayer.Status.PAUSED) {

                playPauseButton.setGraphic(playImage);
                player.play();

            }

        } else {

            nextSong();

        }

    }

    private void playerStatusChange(MediaPlayer.Status status) {

        if (status == MediaPlayer.Status.READY) {
            player.play();
            songLength.setText(player.getStopTime().toString());
            songProgress.setProgress(0);
        }

        if (status == MediaPlayer.Status.PLAYING) {
            Duration time = player.getCurrentTime();
            double endTime = player.getStopTime().toMillis();
            currentRunningTime.setText(time.toString());

            double progress = (endTime - time.toMillis()) / endTime;

            songProgress.setProgress(progress);

        }


    }

    private void nextSong() {

        if (player != null)
            player.stop();

        currentSong = mediaStorage.getNextMediaFile();

        currentRunningTime.setText("0:00");
        songProgress.setProgress(0);

        player = new MediaPlayer(currentSong.toMedia());

        player.statusProperty().addListener(new ChangeListener<MediaPlayer.Status>() {
            @Override
            public void changed(ObservableValue<? extends MediaPlayer.Status> observableValue, MediaPlayer.Status status, MediaPlayer.Status status2) {
                playerStatusChange(status2);
            }
        });

        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                nextSong();
            }
        });

        player.play();


    }


}
