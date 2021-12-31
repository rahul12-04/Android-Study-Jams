package com.example.bookly

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

private const val CAMERA_REQUEST_CODE = 4

class ScanCode : AppCompatActivity() {

    private lateinit var scanner:CodeScanner
    private lateinit var scannerView:CodeScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)
        scannerView = findViewById(R.id.scanner_view)

        //dynamically setting permissions
        setupPermissions()
        // if permissions are already granted then it will proceed further

        //this function will scan the code and it will create get request to fetch book's detail and it would update the database
        decodeId()
    }

    private fun decodeId(){
        //initializing  scanner
        scanner = CodeScanner(this,scannerView)

        scanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE  //scanner will only scan the code once and then proceeds to next instruction
            isAutoFocusEnabled = true
            isFlashEnabled = false
            
            decodeCallback = DecodeCallback { 
                //"it" is the result parameter, so scanned content can be accessed through it.text

                //running it only single thread
                runOnUiThread {

                    //testing the app through toast
                    //toast will show the scanned text
                    Toast.makeText(this@ScanCode,it.text,Toast.LENGTH_LONG).show()

                    //todo:Insert the code to fetch the book data using scanned id and subsequently add an entry in room database
                    //note: we won't navigate to recyclerView activity from here. We will get back to MainActivity only

                    //Activity will be finished here and we will get back to MainActivity
                    finish()
                }
            }

            //in case of error
            errorCallback = ErrorCallback {
                //single threaded
                runOnUiThread {
                    //logging error
                    Log.e("ScanCode","Camera initialization error : ${it.message}")
                }
            }
        }

        //clicking on scannerView would activate scanner to read the code
        //Although here after reading first time, we are finishing the activity, so we don't require this piece of code
        scannerView.setOnClickListener {
            scanner.startPreview()
        }
    }

    //if we resume after some time
    override fun onResume() {
        super.onResume()
        scanner.startPreview()
    }

    //if we take a break, it would be great if we release resources acquired by camera for scanning
    override fun onPause() {
        scanner.releaseResources()
        super.onPause()
    }


    // this will check for camera and other permissions (if any) during runtime
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    //if permissions are not yet granted, then following code will help in prompting for permissions
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    //this function gets user's response to the permission request prompt
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                //if permission is denied
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Please grant permission to use camera. It can be granted through app permissions in settings", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}