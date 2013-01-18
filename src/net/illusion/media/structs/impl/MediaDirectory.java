package net.illusion.media.structs.impl;

import net.illusion.media.structs.util.MediaFileFilter;
import net.illusion.media.structs.Watchable;

import java.io.File;

/**
 * Author: Kyle
 * Project: Player [net.illusion.media.structs.impl]
 * Created: 1/11/13 [6:54 PM]
 * Description:
 */
public class MediaDirectory extends Watchable {

    private MediaFile[] mediaFiles = null;
    private boolean enabled = false;


    public MediaDirectory(File file) {

        if (file.isDirectory()) {
            this.file = file;

            File[] files = file.listFiles(MediaFileFilter.ACCEPTED_MEDIA);
            mediaFiles = new MediaFile[files.length];

            for (int i = 0; i < files.length; i++)
                mediaFiles[i] = new MediaFile(files[i]);


        }

    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public MediaFile[] getAcceptedMediaFiles() {
        return mediaFiles;
    }


}
