package com.example.bookly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksRVAdapter(private val context:Context, private val listener:IBooksRVAdapter):RecyclerView.Adapter<BooksRVAdapter.BookViewHolder>() {

    private val allBooks = ArrayList<Book>()

    inner class BookViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val textView:TextView = itemView.findViewById(R.id.textView)
        val textDate:TextView = itemView.findViewById(R.id.textDate)
        val returnBtn:ImageView = itemView.findViewById(R.id.returnBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val viewHolder = BookViewHolder(LayoutInflater.from(context).inflate(R.layout.item_book,parent,false))
        //we are not directly returning viewHolder as we need to handle the clicks as well
        //which we will do here
        viewHolder.returnBtn.setOnClickListener {
            listener.onItemClicked(allBooks[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = allBooks[position]
        holder.textView.text = currentBook.bookName
        holder.textDate.text = currentBook.date
    }

    override fun getItemCount(): Int {
        return allBooks.size
    }

    fun updateList(newlist:List<Book>){
        allBooks.clear()
        allBooks.addAll(newlist)
        notifyDataSetChanged()
    }
}

interface IBooksRVAdapter{
    fun onItemClicked(book: Book)
}