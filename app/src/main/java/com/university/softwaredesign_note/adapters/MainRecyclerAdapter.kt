package com.university.softwaredesign_note.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.university.softwaredesign_note.R
import com.university.softwaredesign_note.models.Note
import kotlinx.android.synthetic.main.item_note.view.*

import timber.log.Timber

class MainRecyclerAdapter(
    private val onItemClickListener: OnItemClickListener

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

        holder.itemView.item__card_title.text = "Title"
        holder.itemView.item__card_description.text = "Description"
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

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(position, item)
        }
    }

    fun del(position: Int) {
        Timber.i("POSITION $position  $titles  ${titles.size}")

    }

    fun bind(it: ArrayList<Note>?) {
        if (it != null) {
            this.titles.clear()
            this.titles.addAll(it)
            notifyDataSetChanged()
        }
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}
