package net.texturelab.jna.bass;

import net.texturelab.jna.TLABNativeLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class BassLoader {

    @NotNull
    public static final IBassLib INSTANCE = Objects.requireNonNull(
            TLABNativeLoader.load("jna/un4seen/bass", IBassLib.class)
    );

}
