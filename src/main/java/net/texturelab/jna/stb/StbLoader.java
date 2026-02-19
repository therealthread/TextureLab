package net.texturelab.jna.stb;

import net.texturelab.jna.TLABNativeLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
public class StbLoader {

    @NotNull
    public static final IStbLib INSTANCE = Objects.requireNonNull(
            TLABNativeLoader.load("jna/stb/tlab_native", IStbLib.class)
    );

}
