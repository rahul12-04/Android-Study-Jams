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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ScanFragment : Fragment() {

    private lateinit var scanner: CodeScanner
    private lateinit var scannerView:CodeScannerView
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    lateinit var viewModel: BookViewModel

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
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        //Asking for crucial permissions during runtime
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

                    //submitting the data to be added to our local database
                    submitData(it.text.toString())

                    //ScanCodeFragment will be finished here and we will get back to HomeFragment
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

    private fun submitData(idBook: String) {
        //fetching local date
        val localDate = LocalDate.now()

        //checking if id is valid or not
        val idBookInt = try {
            idBook.toInt()
        } catch (e: Exception) {
            Toast.makeText(requireContext(),"Error:Invalid Code!",Toast.LENGTH_SHORT).show()
            return
        }
        var bookName:String? = null
        var i = 0

        //here while developing we are searching on list already available with the app
        //But this app could be further extended by adding a feature of retrieving book name
        //corresponding to scanned id from an online library database. Online database
        //and API for the app is also developed by Abhishek Purohit
        //API for reading single entry can be accessed at :
        //https://bookly-by-abhishek-purohit.herokuapp.com/api/book/read_single.php?id={$idBookInt}
        //It returns a json object with "name" and "id" as keys
        //Implementing request in synchronous way is in development

        while(i<MainActivity.libdb.size){
            if(MainActivity.libdb[i].id==idBookInt){
                bookName= MainActivity.libdb[i].bookName
                break
            }
            i++
        }

        //if there is no book with such id, then return
        if(bookName==null){
            Toast.makeText(requireContext(),"Code not found in library database! Please contact library staff",Toast.LENGTH_LONG).show()
            return
        }

        //adding entry to BookDatabase
        viewModel.insertBook(Book(bookName,idBookInt,
            DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate)))
        Toast.makeText(requireContext(),"$bookName issued successfully",Toast.LENGTH_SHORT).show()
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
