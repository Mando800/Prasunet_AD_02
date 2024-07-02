package com.akerusan.todoapp.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.akerusan.todoapp.Common.Task
import com.akerusan.todoapp.R
import java.util.*


class ListAdapter (private val context: Context, itemList: ArrayList<Task>) : BaseAdapter() {

    private var mInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var items = itemList
    private var listener: OnTaskItemClickListener? = null

    interface OnTaskItemClickListener {
        fun onCheckboxClicked(position: Int, item: Task?)
        fun onDeleteClicked(position: Int, item: Task?)
        fun onEditClicked(position: Int, item: Task?)
    }

    fun setOnTaskClickListener(listener: OnTaskItemClickListener?) {
        this.listener = listener
    }

    fun setItemsList(itemList: ArrayList<Task>) {
        items = itemList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {

            view = mInflater.inflate(R.layout.task_layout, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {

            viewHolder = view.tag as ViewHolder
        }


        val item = items[position]


        viewHolder.taskLayoutDelete.setOnClickListener {
            listener!!.onDeleteClicked(position, item) }

        viewHolder.taskLayoutText.text = item.getText()
        viewHolder.taskLayoutText.setOnClickListener {
            listener!!.onEditClicked(position, item) }

        viewHolder.taskLayoutCheckbox.tag = position
        viewHolder.taskLayoutCheckbox.isChecked = item.isCheckboxChecked()
        viewHolder.taskLayoutCheckbox.setOnClickListener {
            listener!!.onCheckboxClicked(position, item) }


        if (item.isCheckboxChecked()){
            viewHolder.taskLayoutText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            viewHolder.taskLayoutText.setTextColor(Color.GRAY)
        } else {
            viewHolder.taskLayoutText.paintFlags = 0
            viewHolder.taskLayoutText.setTextColor(ContextCompat.getColor(context,
                R.color.colorPrimaryDark
            ))
        }
        return view!!
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }


    class ViewHolder(view: View?) {
        val taskLayoutCheckbox: CheckBox = view!!.findViewById(R.id.task_layout_checkbox)
        val taskLayoutText: TextView = view!!.findViewById(R.id.task_layout_text)
        val taskLayoutDelete: ImageButton = view!!.findViewById(R.id.task_layout_delete)
    }
}