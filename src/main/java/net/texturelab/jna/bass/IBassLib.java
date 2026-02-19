package net.texturelab.jna.bass;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

import org.jetbrains.annotations.ApiStatus;

/**
 * <h2>un4seen - BASS LICENSE</h2>
 * <p>
 * This software utilizes the BASS Audio Library.
 * Copyright (c) 1999-2026 Un4seen Developments Ltd. All rights reserved.
 * </p>
 * <p>
 * Usage Terms
 * </p>
 * This application integrates the BASS library for its core audio
 * playback and processing functionalities. The audio-related
 * features provided by this software are distributed for FREE.
 * <p>
 * The developer of this application complies with the "Non-Commercial"
 * use terms of BASS, as the software is provided to the general
 * public without any mandatory charges for its primary audio functions.
 * </p>
 * - * - * - * - * - * - * - * - * - * - * - * - * - * - * - * - * - *
 * <p>
 * For complete documentation, add-ons, and commercial licensing
 * information regarding the BASS Audio Library, please visit:
 * </p>
 * Official Web: <a href="https://www.un4seen.com/">
 * un4seen</a>
 * <p>
 * @author Anemys (JNA Wrapper)
 * @since 0.1
 */

@ApiStatus.Internal
@SuppressWarnings("all")
public interface IBassLib extends Library {

    /*
    * init & release
    * */
    boolean BASS_Init(int device, int freq, int flags, Pointer win, Pointer clsid);

    boolean BASS_Free();

    boolean BASS_StreamFree(int handle);

    int BASS_ErrorGetCode();

    /*
    * file comp
    * */
    int BASS_StreamCreateFile(boolean mem, String file, long offset, long length, int flags);

    /*
    * channel control
    * */
    boolean BASS_ChannelPlay(int handle, boolean restart);

    boolean BASS_ChannelPause(int handle);

    boolean BASS_ChannelStop(int handle);

    /*
    * 0 = stop
    * 1 = playing
    * 3 = pause
    * */
    int BASS_ChannelIsActive(int handle);

    int BASS_ChannelFlags(int handle, int flags, int mask);

    /*
    * audio info / mode 0 = byte
    * */
    long BASS_ChannelGetLength(int handle, int mode);

    long BASS_ChannelGetPosition(int handle, int mode);

    boolean BASS_ChannelSetPosition(int handle, long pos, int mode);

    double BASS_ChannelBytes2Seconds(int handle, long pos);

    boolean BASS_ChannelSetAttribute(int handle, int attrib, float value);

    int BASS_ChannelGetData(int handle, float[] buffer, int length);
}
