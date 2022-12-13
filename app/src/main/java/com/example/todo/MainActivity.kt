package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ToDoAdapter.OnItemClickListenerInterface {

    private lateinit var tdAdapter: ToDoAdapter
    private lateinit var indivTaskViewModel: IndivTaskViewModel
    private lateinit var binding: ActivityMainBinding

    // to check whether sub FABs are visible or not
    private var isAllFABsVisible: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        🦋Copy
        indivTaskViewModel = ViewModelProvider(this).get(IndivTaskViewModel::class.java)
//        🦋Copy

//        hide additional FAB
        fabDel.visibility = View.GONE
        tvDelTitle.visibility = View.GONE
        fabClose.visibility = View.GONE
        tvCloseTitle.visibility = View.GONE

        efabAdd.shrink()

        isAllFABsVisible = false

        efabAdd.setOnClickListener {
            if(isAllFABsVisible == false){
//                println("🐽 $isAllFABsVisible")
//              set isAllFABsVisible to true so second click will run else
                onAddBtClicked()
            } else {
//                println("🐤 $isAllFABsVisible")
//                show fragment(popup to add new tasks) which has function
//                for save button, call saveAction()
//                saveAction() uses additem() of indivTaskViewModel
                AddTask().show(supportFragmentManager,"addTaskTag")

//              toggles the buttons and resets isAllFABsVisible to false
                onAddBtClicked()
            }
        }

        setRecyclerView()

        fabClose.setOnClickListener {
            onCloseClicked()
        }
    }

    //    implement interface method
    override fun onItemClick(position: Int) {
        UpdateTask(position).show(supportFragmentManager,"updateTaskTag")
    }

    private fun setRecyclerView() {
//        observe means that changes in indivTaksViewModel will set the recycler view?
        indivTaskViewModel.taskItems.observe(this){
//            no idea what's going on here
            tdAdapter = ToDoAdapter(it, this)
            rvToDoList.adapter = tdAdapter
            rvToDoList.layoutManager = LinearLayoutManager(this)
//            set click event to delConeToDo() from TodoAdapter
            fabDel.setOnClickListener {
                indivTaskViewModel.deleteItem()
//                tdAdapter.delDoneToDo()
            }
        }
    }

    private fun onCloseClicked() {
        setVisibility()
        fabDel.visibility = View.GONE
        tvDelTitle.visibility = View.GONE
        fabClose.visibility = View.GONE
        tvCloseTitle.visibility = View.GONE

        efabAdd.shrink()
    }

    private fun onAddBtClicked() {
//        println("variable is $isAllFABsVisible")

        if(isAllFABsVisible == false) {
//            println("change to show")
            fabDel.visibility = View.VISIBLE
            tvDelTitle.visibility = View.VISIBLE
            fabClose.visibility = View.VISIBLE
            tvCloseTitle.visibility = View.VISIBLE

            efabAdd.extend()
            setVisibility()
        } else {
//            println("change to hide")
            fabDel.visibility = View.GONE
            tvDelTitle.visibility = View.GONE
            fabClose.visibility = View.GONE
            tvCloseTitle.visibility = View.GONE

            efabAdd.shrink()
            setVisibility()
        }
    }

    private fun setVisibility() {
//        println("change visibility")
        isAllFABsVisible = !isAllFABsVisible!!
    }
}