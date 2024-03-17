package com.capstone.chillgoapp.ui.common

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.util.Locale

class FileUtility {
    companion object {

        fun Uri.getBitmap(c: ContentResolver): Bitmap? {
            return try {
                val input = c.openInputStream(this)
                BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                null
            }
        }

        fun bitmapToFile(
            context: Context,
            bitmap: Bitmap,
            fileName: String
        ): File? {
            return try {
                val dir = context.filesDir
                val file = File(dir, fileName)
                file.createNewFile()

                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
                val bitmapdata = bos.toByteArray()

                val fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
                file
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    }
}
