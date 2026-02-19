package net.texturelab.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

public final class TLABNativeLoader {

    public static <T extends Library> T load(String libName, Class<T> IClass) {
        try {

            /*
            * JNA DIR TARGET -> -Djna.library.path="lib"
            * */
            return Native.load(libName, IClass);

        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return null;
        }
    }

}
