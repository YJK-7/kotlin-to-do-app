package com.example.todo

import android.content.Context
import android.graphics.Color
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

// adapter contains logic for filling in recycler view with data
class ToDoAdapter (
    private val toDos: MutableList<ToDo>,
    private val listener: OnItemClickListenerInterface
    ): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    interface OnItemClickListenerInterface {
        fun onItemClick(position: Int)
    }

// viewholder represents a single row inour recycler view
//    it holds? reference to the single views in our row layout
//    it caches references(to stuff like textView) so that we can reuse it
// basically lets u use items that have been scrolled off rather than creating a new item every time
    inner class ToDoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.tvToDoTitle
        val checkBox: CheckBox = itemView.cbDone
        val linLayout: LinearLayout = itemView.ll_item_todo
        var taskDesc: TextView? = null

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
//        layout inflater class converts xml layout files into View objects
//        specifically .inflate method

//        bottom line is this piece of code is most of the time exact same whenever you
//        implement a recycler view we don't have to worry too much about what it's doing
//        R.layout.item_todo, item_todo-> xml file name for each item
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        )

//        create individual item view
        return ToDoViewHolder(itemView)
    }

    // call everytime, use object created by ToDoViewHolder
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currentToDo = toDos[position]
        val numView:Int = holder.linLayout.childCount

        holder.taskTitle.text = currentToDo.title
        holder.checkBox.isChecked = currentToDo.isDone
        if (currentToDo.desc !== null && numView <= 1) {
            holder.taskDesc = createDescTextView(
                currentToDo.desc,
                holder.taskTitle.context,
                holder.linLayout
            )
        }
//    for the case that our taskitem is already checked?? (but literally when would that...happen?)
        toggleStrikeThrough(
            holder.taskTitle,
            currentToDo.isDone,
            holder.taskDesc
        )

//    on change of checkbox do the following
//    isDone is the new/updated status of CB
        holder.checkBox.setOnCheckedChangeListener { _, isDone ->
//            _ means we don't need parameter in the function
            toggleStrikeThrough(holder.taskTitle,isDone,holder.taskDesc)
            //      update currentToDo item
            currentToDo.isDone = !currentToDo.isDone
        }
    }


    private fun toggleStrikeThrough(tvToDoTitle: TextView, isChecked: Boolean,  tvToDoDesc:TextView? = null){
        if(isChecked) {
            tvToDoTitle.setTextColor(Color.parseColor("#729F9F9F"))
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
            if(tvToDoDesc !== null) {
                tvToDoDesc.setTextColor(Color.parseColor("#729F9F9F"))
                tvToDoDesc.paintFlags = tvToDoDesc.paintFlags or STRIKE_THRU_TEXT_FLAG
            }
        } else if (!isChecked) {
            tvToDoTitle.setTextColor(Color.parseColor("#DE000000"))
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            if(tvToDoDesc !== null) {
                tvToDoDesc.setTextColor(Color.GRAY)
                tvToDoDesc.paintFlags = tvToDoDesc.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
    private fun createDescTextView(descText: String, context: Context, layout: LinearLayout): TextView {
        val newDescTextView = TextView(context)
        val resources = context.resources
        val margin:Int = resources.getDimension(R.dimen.text_left_margin).toInt()
        val textSize:Float = resources.getDimension(R.dimen.text_size)

        newDescTextView.text = descText
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

//        apparently getDimensions returns Resource value multiplied by the appropriate metric
//        aka a precalculated absolute px value?
//        so we have to specify to use px value
        newDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize)
        newDescTextView.layoutParams = layoutParams
        newDescTextView.id = R.id.tvToDoDesc
        newDescTextView.setTextColor(Color.GRAY)

        layoutParams.setMargins(margin,0,0,0)
        layout.addView(newDescTextView)
        return newDescTextView
    }

//Num of items
    override fun getItemCount(): Int {
        return toDos.size
    }
}