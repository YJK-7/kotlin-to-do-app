package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.todo.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_task.*

class UpdateTask(position: Int) : BottomSheetDialogFragment() {

    private var taskPosition = position
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var indivTaskViewModel: IndivTaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        indivTaskViewModel = ViewModelProvider(activity).get(IndivTaskViewModel::class.java)
        binding.btSave.setOnClickListener {
            updateAction(taskPosition)
        }
        val toDoObj = indivTaskViewModel.getItem(taskPosition)
        if(toDoObj != null){
            binding.etTaskTitle.setText(toDoObj.title)
            binding.etDescription.setText(toDoObj.desc)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        binding.etTaskTitle.doOnTextChanged { _, _, _, _ ->
            textInputLayoutTask.error = null
        }
        return binding.root
    }

    private fun updateAction(position: Int) {

        val taskName = binding.etTaskTitle.text.toString()
        var taskDes:String? = binding.etDescription.text.toString()

        if(taskName.isNotEmpty()){
            if (taskDes!!.isEmpty()){
                taskDes = null
            }
            val updatedTaskItem = ToDo(taskName,taskDes) //create list item
            indivTaskViewModel.updateItem(position, updatedTaskItem)//replace item

            dismiss()
        } else {
            textInputLayoutTask.error = "Please insert Task Title!!"
        }

    }
}