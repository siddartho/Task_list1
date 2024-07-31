package com.example.ass2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var tasks = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        taskAdapter = TaskAdapter(tasks)
        recyclerView.adapter = taskAdapter

        Log.d("TaskListFragment", "Fragment created and view set up.")
        return view
    }

    companion object {
        fun newInstance(tasks: ArrayList<Task>): TaskListFragment {
            val fragment = TaskListFragment()
            val args = Bundle()
            args.putParcelableArrayList("tasks", tasks)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasks = arguments?.getParcelableArrayList("tasks") ?: ArrayList()
    }

    fun addTask(task: Task) {
        Log.d("TaskListFragment", "Adding task: ${task.task}")
        tasks.add(task)
        taskAdapter.notifyItemInserted(tasks.size - 1)
    }

    fun updateTasks(newTasks: ArrayList<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        taskAdapter.notifyDataSetChanged()
    }
}

private fun Bundle.putParcelableArrayList(s: String, tasks: ArrayList<Task>) {

}
