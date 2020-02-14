package com.pawelsobaszek.todoapppseleader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

//Adapter do uzupełniania ListView
class TodoAdapter(var context: Context, var todo: ArrayList<TodoItem>): BaseAdapter() {


    private class ViewHolder(row: View?) {
        var txt1: TextView
        var txt2: TextView
        var txt3: TextView

        init {
            this.txt1 = row?.findViewById(R.id.todoTitle) as TextView
            this.txt2 = row?.findViewById(R.id.todoDate) as TextView
            this.txt3 = row?.findViewById(R.id.todoCategory) as TextView

        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.activity_main_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var todo: TodoItem = getItem(position) as TodoItem
        viewHolder.txt1.text = todo.text1
        viewHolder.txt2.text = todo.text2
        viewHolder.txt3.text = todo.text3

        return view as View

    }

    override fun getItem(position: Int): Any {
        return todo.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return todo.count()
    }

}