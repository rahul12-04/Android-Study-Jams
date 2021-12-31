package com.example.bookly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    lateinit var issueNewBtn: Button
    lateinit var curIssued: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        issueNewBtn = view.findViewById(R.id.newIssue)
        curIssued = view.findViewById(R.id.currentlyIssued)

        issueNewBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scanFragment)
        }
        curIssued.setOnClickListener {
//            currentlyIssuedList(it)
        }

        return view
    }

}