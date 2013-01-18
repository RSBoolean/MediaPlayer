package net.illusion.media.codec;

import net.illusion.media.structs.impl.MediaFile;

/**
 * Author: Kyle
 * Project: Player [net.illusion.media.codec]
 * Created: 1/11/13 [6:40 PM]
 * Description:
 */
public abstract class Codec implements CodecInformation {


    public abstract void parseCodec(MediaFile mediaFile);

}
