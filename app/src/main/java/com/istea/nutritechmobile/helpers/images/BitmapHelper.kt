package com.istea.nutritechmobile.helpers.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class BitmapHelper {
    companion object {
        private const val MAX_HEIGHT = 1024;
        private const val MAX_WIDTH = 1024;

        /**
         * This method is responsible for solving the rotation issue if exist. Also scale the images to
         * 1024x1024 resolution
         *
         * @param context       The current context
         * @param selectedImage The Image URI
         * @return Bitmap image results
         * @throws IOException
         */
        @Throws(IOException::class)
        fun handleSamplingAndRotationBitmap(context: Context, selectedImage: Uri): Bitmap? {
            // First decode with inJustDecodeBounds=true to check dimensions

            val options = BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            var imageStream = context.contentResolver.openInputStream(selectedImage);
            BitmapFactory.decodeStream(imageStream, null, options);
            imageStream?.close();

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            imageStream = context.contentResolver.openInputStream(selectedImage);
            val img = BitmapFactory.decodeStream(imageStream, null, options);

            return rotateImageIfRequired(img, selectedImage)
        }

        /**
         * Calculate an inSampleSize for use in a [BitmapFactory.Options] object when decoding
         * bitmaps using the decode* methods from [BitmapFactory]. This implementation calculates
         * the closest inSampleSize that will result in the final decoded bitmap having a width and
         * height equal to or larger than the requested width and height. This implementation does not
         * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
         * results in a larger bitmap which isn't as useful for caching purposes.
         *
         * @param options   An options object with out* params already populated (run through a decode*
         * method with inJustDecodeBounds==true
         * @param reqWidth  The requested width of the resulting bitmap
         * @param reqHeight The requested height of the resulting bitmap
         * @return The value to be used for inSampleSize
         */
        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int, reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

                // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
                // with both dimensions larger than or equal to the requested height and width.
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

                // This offers some additional logic in case the image has a strange
                // aspect ratio. For example, a panorama may have a much larger
                // width than height. In these cases the total pixels might still
                // end up being too large to fit comfortably in memory, so we should
                // be more aggressive with sample down the image (=larger inSampleSize).
                val totalPixels = (width * height).toFloat()

                // Anything more than 2x the requested pixels we'll sample down further
                val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
                while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                    inSampleSize++
                }
            }
            return inSampleSize
        }

        /**
         * Rotate an image if required.
         *
         * @param img           The image bitmap
         * @param selectedImage Image URI
         * @return The resulted Bitmap after manipulation
         */
        @Throws(IOException::class)
        private fun rotateImageIfRequired(img: Bitmap?, selectedImage: Uri): Bitmap? {
            val ei = ExifInterface(selectedImage.path!!)
            val orientation =
                ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            img?.let {
                return when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270f)
                    else -> img
                }
            }

            return null
        }

        private fun rotateImage(img: Bitmap, degree: Float): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(degree)
            val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
            img.recycle()
            return rotatedImg
        }

        fun reduceImageSizeToUpload(context: Context, imgFile: File): ByteArray {
            val imgPath = imgFile.absolutePath
            val imgBitmap = handleSamplingAndRotationBitmap(context, Uri.parse("file://$imgPath"))
            val arrayOutput = ByteArrayOutputStream()
            imgBitmap?.compress(Bitmap.CompressFormat.JPEG, 25, arrayOutput)
            return arrayOutput.toByteArray()
        }
    }
}
