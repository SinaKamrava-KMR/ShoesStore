package com.mainway.store.feature.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mainway.store.R
import com.mainway.store.data.Comment
import java.util.ArrayList

class CommentAdapter(private val showAll : Boolean =  false) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTv = itemView.findViewById<TextView>(R.id.commentTitleTv)
        private val dateTv = itemView.findViewById<TextView>(R.id.commentDataTV)
        private val authorTv = itemView.findViewById<TextView>(R.id.commentAuthor)
        private val contentTv = itemView.findViewById<TextView>(R.id.commentContent)
        fun bindComment(comment: Comment) {
            titleTv.text = comment.title
            dateTv.text = comment.date
            authorTv.text = comment.author.email
            contentTv.text = comment.content

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount(): Int = if (comments.size>3 && !showAll) 3  else comments.size
}