package com.university.softwaredesign_note.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.extensions.setCustomOnClickListener
import com.university.softwaredesign_note.extensions.toFormat
import com.university.softwaredesign_note.extensions.toStrDate
import com.university.softwaredesign_note.models.Note
import kotlinx.android.synthetic.main.item_note.view.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class MainRecyclerAdapter(
        private val onItemClickListener: OnItemClickListener,
        private val onItemLikeClickListener: OnItemClickListener

) :
        RecyclerView.Adapter<DataViewHolder>() {
    private val titles = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = titles[position]

        holder.itemView.item__card_title.text = item.title
        holder.itemView.item__card_description.text = Date((item.dateEdit)).toStrDate()

        val type = when (position) {
            in 1..2 -> {
                R.drawable.ic_edit
            }
            in 3..4 -> {
                R.drawable.ic_time_alert
            }
            else -> {
                R.drawable.bottom_nav__add
            }
        }
        holder.itemView.item__card_image.setImageResource(type)
        holder.itemView.item__card_like.setBackgroundResource(R.drawable.bottom_nav__like)
        when (item.liked) {
            true -> {
                holder.itemView.item__card_like.setBackgroundResource(R.drawable.bottom_nav__like_filled)
                holder.itemView.item__card_like.tag = "liked"
            }
        }

        holder.itemView.item__card_like.setOnClickListener {
            onItemLikeClickListener.onItemClicked(position, item)
            if (holder.itemView.item__card_like.tag == "liked") {
                holder.itemView.item__card_like.setBackgroundResource(R.drawable.bottom_nav__like)
                holder.itemView.item__card_like.tag = "unlicked"
                return@setOnClickListener
            }
            holder.itemView.item__card_like.setBackgroundResource(R.drawable.bottom_nav__like_filled)
            holder.itemView.item__card_like.tag = "liked"
        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(position, item)
        }
    }

    fun del(position: Int) {
        titles.removeAt(position)
        notifyDataSetChanged()
        Timber.i("POSITION $position  $titles  ${titles.size}")
    }

    fun getItem(position: Int): Note = titles[position]

    fun bind(it: ArrayList<Note>?) {
        Timber.i("RECBIND ITEM CREATE")
        if (it != null) {
            this.titles.clear()
            this.titles.addAll(it)
            notifyDataSetChanged()
        }
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}
