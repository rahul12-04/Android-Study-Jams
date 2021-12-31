package com.example.bookly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var issueNewBtn:Button
    lateinit var curIssued:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        issueNewBtn = findViewById(R.id.newIssue)
        curIssued = findViewById(R.id.currentlyIssued)

        issueNewBtn.setOnClickListener { openScanner(it) }
        curIssued.setOnClickListener { currentlyIssuedList(it) }
    }

    private fun currentlyIssuedList(it: View?) {
    //link room database here
    }

    private fun openScanner(it: View?) {
        //intent to open ScanCode activity which will scan the QR code of book and register the entry in database
        val intent = Intent(this,ScanCode::class.java)
        startActivity(intent)
    }
}