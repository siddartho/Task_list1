package com.example.ass2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTaskDialogFragment : DialogFragment() {

    private lateinit var taskEditText: EditText
    private lateinit var dateTimeButton: Button
    private lateinit var addButton: Button
    private var taskDateTime: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task_dialog, container, false)

        taskEditText = view.findViewById(R.id.edit_text_task)
        dateTimeButton = view.findViewById(R.id.button_date_time)
        addButton = view.findViewById(R.id.button_add_task)

        dateTimeButton.setOnClickListener {
            showDateTimePicker()
        }

        addButton.setOnClickListener {
            val task = taskEditText.text.toString()
            if (task.isNotEmpty()) {
                val formattedDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(taskDateTime.time)
                val listener = activity as? OnTaskAddedListener
                listener?.onTaskAdded(Task(task, formattedDateTime))
                dismiss()
            }
        }

        return view
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        taskDateTime.set(year, month, dayOfMonth, hourOfDay, minute)
                        val formattedDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(taskDateTime.time)
                        dateTimeButton.text = formattedDateTime
                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    true
                ).show()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
