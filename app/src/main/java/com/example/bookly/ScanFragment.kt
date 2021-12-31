package com.example.bookly

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*

class ScanFragment : Fragment() {

    private lateinit var scanner: CodeScanner
    private lateinit var scannerView:CodeScannerView
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    Log.i("ScanFragCamPermission", "permission granted")
                } else {
                    Log.i("ScanFragCamPermission", "permission denied")
                    Toast.makeText(requireContext(), "App requires camera to scan code! Please give permission through settings", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        //dynamically setting permissions
        setupPermissions()
        // if permissions are already granted then it will proceed further

        //initializing scanner
        scanner = CodeScanner(activity,scannerView)

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
                activity.runOnUiThread {

                    //testing the app through toast
                    //toast will show the scanned text
                    Toast.makeText(activity,it.text,Toast.LENGTH_LONG).show()

                    //todo:Insert the code to fetch the book data using scanned id and subsequently add an entry in room database
                    //note: we won't navigate to recyclerView activity from here. We will get back to MainActivity only

                    //Activity will be finished here and we will get back to MainActivity
                    findNavController().popBackStack()
                }
            }

            //in case of error
            errorCallback = ErrorCallback {
                //single threaded
                activity.runOnUiThread {
                    //logging error
                    Log.e("ScanFragment","Camera initialization error : ${it.message}")
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
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            // You can use the API that requires the permission.
            return
        }

        else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}
