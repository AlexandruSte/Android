@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.hardware.Camera
import android.hardware.Camera.Parameters.FLASH_MODE_TORCH
import android.hardware.Camera.PictureCallback
import android.hardware.Camera.open
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity(), SurfaceHolder.Callback {
    private var camera: Camera? = null
    private var callback: PictureCallback? = null
    private var surfaceHolder: SurfaceHolder? = null

    private fun getDir(): File {
        val sdDir: File = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val newF = File(sdDir, "Camera_Test")
        if (!newF.exists()) {
            newF.mkdirs()
        }
        return newF
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        callback = PictureCallback { data, _ ->
            val pictureDir: File = getDir()
            val dateFormat = SimpleDateFormat("yyyymmddhhmmss")
            val date: String = dateFormat.format(Date())
            val photoFile = "Picture_$date.jpg"
            val filename: String =
                pictureDir.path + File.separator.toString() + photoFile
            val pictureFile = File(filename)
            try {
                pictureFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                val fos = FileOutputStream(pictureFile)
                fos.write(data)
                fos.close()
            } catch (error: Exception) {
                Log.d(
                    "photo saving", "File" + filename + "not saved: "
                            + error.message
                )
            }
        }
        val btn: Button = findViewById(R.id.takePhoto)
        btn.setOnClickListener {
            run {
                camera!!.takePicture(null, null, callback)
            }
        }
        val surfaceView: SurfaceView = findViewById(R.id.surface)
        surfaceHolder = surfaceView.holder.also {
            //it.type = SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS
            it.addCallback(this)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        camera = open()
        val parameters = camera!!.parameters
        parameters.flashMode = FLASH_MODE_TORCH
        camera!!.parameters = parameters
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder?,
        format: Int,
        width: Int,
        height: Int
    ) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        if (camera != null) {
            camera!!.stopPreview()
            camera!!.release()
            camera = null
        }
    }
}
