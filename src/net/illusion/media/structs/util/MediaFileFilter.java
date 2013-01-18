package net.illusion.media.structs.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Author: Kyle
 * Project: MediaPlayer [net.illusion.media.structs.util]
 * Created: 1/12/13 [8:53 PM]
 * Description:
 */
public interface MediaFileFilter {

    public static FilenameFilter ACCEPTED_MEDIA = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".mp3");
        }
    };

}
