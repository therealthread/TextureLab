package net.texturelab.jna.stb;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;

import org.jetbrains.annotations.ApiStatus;

/**
 * <h2>STB IMAGE - PUBLIC DOMAIN LICENSE</h2>
 * <p>
 * This is free and unencumbered software released into the public domain.
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute this
 * software, either in source code form or as a compiled binary, for any purpose,
 * commercial or non-commercial, and by any means.
 * </p>
 * <p>
 * For more information, please visit: <a href="https://github.com/nothings/stb">stb github</a>
 * </p>
 * <p>
 * - * - * - * - * - * - * - * - * - * - * - * - * - * - * - * - * - *
 * </p>
 * ALTERNATIVE B - Public Domain (www.unlicense.org)
 *
 * @author Sean T. Barrett (STB)
 * @author Anemys (JNA Wrapper)
 */

@ApiStatus.Internal
public interface IStbLib extends Library {

    boolean TLAB_ImageRepair(String inputPath, String outputPath);
    boolean TLAB_GetImageInfo(String path, IntByReference w, IntByReference h, IntByReference comp);
    boolean TLAB_ResampleImage(String inputPath, String outputPath, int targetW, int targetH);
    boolean TLAB_ResampleImageEx(String inputPath, String outputPath, int targetW, int targetH, boolean sharp);

}
