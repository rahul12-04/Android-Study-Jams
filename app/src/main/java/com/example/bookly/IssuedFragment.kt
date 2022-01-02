package com.example.bookly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IssuedFragment : Fragment(),IBooksRVAdapter {

    lateinit var viewModel: BookViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_issued, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BooksRVAdapter(requireContext(),this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        viewModel.allBooks.observe(viewLifecycleOwner, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        return view
    }


    override fun onItemClicked(book: Book) {
        viewModel.deleteBook(book)
    }


}