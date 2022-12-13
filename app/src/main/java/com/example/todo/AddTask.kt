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

class AddTask : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var indivTaskViewModel: IndivTaskViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
//        initialize IndivTaskViewModel
        indivTaskViewModel = ViewModelProvider(activity).get(IndivTaskViewModel::class.java)
        binding.btSave.setOnClickListener {
            saveAction()
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

    private fun saveAction() {
        val taskName = binding.etTaskTitle.text.toString()
        var taskDes:String? = binding.etDescription.text.toString()

        if(taskName.isNotEmpty()){

            if (taskDes!!.isEmpty()){
                taskDes = null
            }

            val newTaskItem = ToDo(taskName,taskDes) //create list item
            indivTaskViewModel.addItem(newTaskItem) //add newItem to list

            dismiss()
        } else {
            textInputLayoutTask.error = "Please insert Task Title!!"

//            var alert = AlertDialog.Builder(context)
//            alert.apply {
//                setMessage("Please insert Task Title!!")
//                setPositiveButton("OK") {negative,_ ->
//                    negative.dismiss()
//                }
//            }
//            alert.create()
//            alert.show()

        }

    }
}