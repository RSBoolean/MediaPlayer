package net.illusion.media.structs;

import java.io.File;
import java.nio.file.*;

/**
 * Author: Kyle
 * Project: MediaPlayer [net.illusion.media.structs]
 * Created: 1/12/13 [9:59 PM]
 * Description:
 */
public abstract class Watchable {

    public File file = null;
    public WatchKey watchKey = null;

    private void registerWatchSystem() {

        Path path = Paths.get(file.getAbsolutePath());

        try {

            WatchService watchService = FileSystems.getDefault().newWatchService();

            watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                                                   StandardWatchEventKinds.ENTRY_CREATE,
                                                   StandardWatchEventKinds.ENTRY_DELETE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public WatchKey getWatchKey() {

        if (watchKey == null)
            registerWatchSystem();

        return watchKey;

    }

    public File getFile() {
        return file;
    }


}
