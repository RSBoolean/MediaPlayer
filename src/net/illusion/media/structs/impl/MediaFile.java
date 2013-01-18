package net.illusion.media.structs.impl;

import javafx.scene.media.Media;
import net.illusion.media.codec.Codec;
import net.illusion.media.codec.CodecFactory;
import net.illusion.media.codec.CodecInformation;
import net.illusion.media.structs.Watchable;

import java.io.File;

/**
 * Author: Kyle
 * Project: Player [net.illusion.media.structs.impl]
 * Created: 1/11/13 [6:37 PM]
 * Description:
 */
public class MediaFile extends Watchable {

    private Codec mediaCodec = null;

    public MediaFile(File file) {
        this.file = file;
    }

    public CodecInformation getCodecInformation() {

        if (mediaCodec == null)
            mediaCodec = CodecFactory.createCodec(file);


        return mediaCodec;

    }

    public Media toMedia() {
        return new Media(file.getAbsolutePath());
    }

}
