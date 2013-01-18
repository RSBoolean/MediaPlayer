package net.illusion.media.structs;

import net.illusion.media.structs.impl.MediaDirectory;
import net.illusion.media.structs.impl.MediaFile;
import net.illusion.media.structs.util.MediaFileFilter;

import java.io.File;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author: Kyle
 * Project: MediaPlayer [net.illusion.media.structs]
 * Created: 1/12/13 [1:28 PM]
 * Description:
 */
public class MediaStorage implements Runnable {

    private ConcurrentLinkedQueue<MediaFile> songsQueue;
    private ArrayList<Watchable> watchables;
    private ArrayList<MediaFile> loadedSongs;

    public void addMediaFile(MediaFile file) {

        if (extensionAccepted(file.getFile()))
            loadedSongs.add(file);
    }

    public void removeMediaFile(MediaFile file) {

        loadedSongs.remove(file);
        songsQueue.remove(file);

    }

    public void removeMediaDirectory(MediaDirectory directory) {


    }

    public void addMediaDirectory(MediaDirectory directory) {


    }

    public MediaFile getNextMediaFile() {

        if (songsQueue == null || songsQueue.isEmpty())
            buildSongQueue();

        return songsQueue.poll();
    }


    public void shuffleSongs() {
        Collections.shuffle(loadedSongs);
    }

    public void run() {

        while (true) {

            try {

                for (int i = 0; i < watchables.size(); i++) {

                    Watchable watchable = watchables.get(i);
                    WatchKey key = watchable.getWatchKey();

                    for (WatchEvent<?> event : key.pollEvents()) {

                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW)
                            continue;

                        if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            removeWatchable(watchable);
                        }

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            addWatchable(watchable);
                        }

                    }

                }

                Thread.sleep(1000);

            } catch (Exception ex) {

            }

        }

    }

    private void addWatchable(Watchable watchable) {

    }

    private void removeWatchable(Watchable watchable) {

    }

    private void buildSongQueue() {

        if (songsQueue == null)
            songsQueue = new ConcurrentLinkedQueue<>();

        if (songsQueue.isEmpty())
            songsQueue.addAll(loadedSongs);

    }


    private void examineMediaDirectory(MediaDirectory directory) {
        File root = directory.getFile();
        examineDirectory(root);
    }

    private void examineDirectory(File dir) {

        for (File file : dir.listFiles()) {

            if (file != null) {

                if (extensionAccepted(file))
                    addMediaFile(new MediaFile(file));

            }
        }

    }


    private boolean extensionAccepted(File file) {
        return MediaFileFilter.ACCEPTED_MEDIA.accept(file, file.getName());
    }

}
