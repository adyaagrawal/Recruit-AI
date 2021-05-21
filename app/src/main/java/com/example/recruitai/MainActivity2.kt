package com.example.recruitai

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File

class MainActivity2 : AppCompatActivity() {
    val TAG = "MainActivity2"
    var isRecording = false
    var CAMERA_PERMISSION = Manifest.permission.CAMERA
    var RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO
    var RC_PERMISSION = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val recordFiles = ContextCompat.getExternalFilesDirs(this, Environment.DIRECTORY_MOVIES)
        val storageDirectory = recordFiles[0]
        val videoRecordingFilePath = "${storageDirectory.absoluteFile}/${System.currentTimeMillis()}_video.mp4"

        if (checkPermissions()) startCameraSession() else requestPermissions()
        var video_record : Button = findViewById(R.id.video_record)
        video_record.setOnClickListener {
            val timer = object: CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    textView15.setText((millisUntilFinished/1000).toString());
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity2, "Time is up. Recording Stopped", Toast.LENGTH_SHORT).show()
                    camera_view.stopRecording()
                }
            }
            timer.start()
            if (isRecording) {
                isRecording = false
                video_record.text = "Record Video"
                Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()
                camera_view.stopRecording()
            } else {
                isRecording = true
                video_record.text = "Stop Recording"
                Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show()
                recordVideo(videoRecordingFilePath)
            }
        }
        button.setOnClickListener{
            startActivity(Intent(this@MainActivity2, FinalAnalysis::class.java))
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION, RECORD_AUDIO_PERMISSION), RC_PERMISSION)
    }

    private fun checkPermissions(): Boolean {
        return ((ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            RC_PERMISSION -> {
                var allPermissionsGranted = false
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false
                        break
                    } else {
                        allPermissionsGranted = true
                    }
                }
                if (allPermissionsGranted) startCameraSession() else permissionsNotGranted()
            }
        }
    }

    private fun startCameraSession() {
        camera_view.bindToLifecycle(this)
    }

    private fun permissionsNotGranted() {
        AlertDialog.Builder(this).setTitle("Permissions required")
                .setMessage("These permissions are required to use this app. Please allow Camera and Audio permissions first")
                .setCancelable(false)
                .setPositiveButton("Grant") { dialog, which -> requestPermissions() }
                .show()
    }

    private fun recordVideo(videoRecordingFilePath: String) {
        camera_view.startRecording(File(videoRecordingFilePath), ContextCompat.getMainExecutor(this), object : VideoCapture.OnVideoSavedCallback {
            override fun onVideoSaved(file: File) {
                Toast.makeText(this@MainActivity2, "Recording Saved", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onVideoSaved $videoRecordingFilePath")
            }

            override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                Toast.makeText(this@MainActivity2, "Recording Failed", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onError $videoCaptureError $message")
            }
        })
    }
}
