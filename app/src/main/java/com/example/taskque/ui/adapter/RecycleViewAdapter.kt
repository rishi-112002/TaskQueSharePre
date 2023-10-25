package com.example.taskque.ui.adapter

import android.service.controls.ControlsProviderService.TAG
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.taskque.models.ItemData
import com.example.taskque.R
import com.example.taskque.interfaces.DataItemClickListener

fun main() {

}

class RecycleViewAdapter() :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private var taskList: List<ItemData> = ArrayList()
    private var mItemClickListener: DataItemClickListener? = null;
    fun setList(list: List<ItemData>) {
        Log.d(TAG, list.toString())
        taskList = list
        notifyDataSetChanged()

    }

    fun setListener(listener: DataItemClickListener) {
        this.mItemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        val titleTextView: TextView = itemView.findViewById(R.id.inputTextTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.taskdate)
        val timeTextView: TextView = itemView.findViewById(R.id.inputtime)
        val contentTextView: TextView = itemView.findViewById(R.id.input_content)
        val sideText: TextView = itemView.findViewById(R.id.tasktoggle)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        val tasktype: TextView = itemView.findViewById(R.id.taskTypetext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_recycleview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.titleTextView.text = currentItem.titleinputtext
        holder.dateTextView.text = currentItem.dateinput
        holder.timeTextView.text = currentItem.timeinput
        holder.contentTextView.text = currentItem.contentinput
        holder.sideText.text = currentItem.side
        holder.tasktype.text = currentItem.taskType
        if (currentItem.taskType.equals("Routine", true)) {
            holder.tasktype.setBackgroundResource(R.drawable.curvedcornergreen)
        } else if (currentItem.taskType.equals("Work", true)) {
            holder.tasktype.setBackgroundResource(R.drawable.curvedcornerorange)
        } else if (currentItem.taskType.equals("Priority", true)) {
            holder.tasktype.setBackgroundResource(R.drawable.curvedcornor)
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            if (mItemClickListener != null) {
                mItemClickListener?.onItemClicked(currentItem)
            }
        })
    }

}