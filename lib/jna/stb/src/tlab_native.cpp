/**
 * TextureLab Image Processor API
 *
 * @author  Anemys
 * @version 0.1
 * @since   2026
 * @see     https://github.com/nothings/stb
 */
 
#define STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_WRITE_IMPLEMENTATION
#define STB_IMAGE_RESIZE_IMPLEMENTATION

#include "stb_image.h"
#include "stb_image_write.h"
#include "stb_image_resize2.h"

#define TLAB_API __declspec(dllexport)

extern "C" {


    TLAB_API bool TLAB_ImageRepair(
        const char* inputPath, 
        const char* outputPath
    ) {
        int x, y, n;

        unsigned char *data = stbi_load(inputPath, &x, &y, &n, 0);
        
        if (!data) {
            return false;
        }

        int result = stbi_write_png(outputPath, x, y, n, data, x * n);
        
        stbi_image_free(data);
        
        return result == 1;
    }
	
    TLAB_API bool TLAB_GetImageInfo(
        const char* path, 
        int* w, 
        int* h, 
        int* comp
    ) {
        return stbi_info(
            path,
            w,
            h,
            comp
        ) != 0;
    }

    TLAB_API bool TLAB_ResampleImageEx(
        const char* inputPath,
        const char* outputPath,
        int targetW,
        int targetH,
        bool sharp
    ) {

        int x, y, n;
        unsigned char* input_data = stbi_load(inputPath, &x, &y, &n, 0);

        if (!input_data) return false;

        unsigned char* output_data =
            (unsigned char*) malloc(targetW * targetH * n);

        if (!output_data) {
            stbi_image_free(input_data);
            return false;
        }

        stbir_filter filter =
            sharp ? STBIR_FILTER_BOX : STBIR_FILTER_DEFAULT;

        unsigned char* result_ptr = (unsigned char*) stbir_resize(
            input_data, x, y, 0,
            output_data, targetW, targetH, 0,
            (stbir_pixel_layout) n,
            STBIR_TYPE_UINT8_SRGB,
            STBIR_EDGE_CLAMP,
            filter
        );

        bool success = false;

        if (result_ptr) {
            success = stbi_write_png(
                outputPath,
                targetW,
                targetH,
                n,
                output_data,
                targetW * n
            ) == 1;
        }

        free(output_data);
        stbi_image_free(input_data);

        return success;
    }


    TLAB_API bool TLAB_ResampleImage(
        const char* inputPath,
        const char* outputPath,
        int targetW,
        int targetH
    ) {
        return TLAB_ResampleImageEx(
            inputPath,
            outputPath,
            targetW,
            targetH,
            false
        );
    }

}