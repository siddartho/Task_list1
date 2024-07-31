package com.example.ass2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.util.Log

class MainActivity : AppCompatActivity(), OnTaskAddedListener {

    private val tasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Load the TaskListFragment by default
            loadFragment(TaskListFragment.newInstance(tasks))
        }
        // The Toolbar defined in the layout has the id "my_toolbar".
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_task -> {
                showAddTaskDialog()
                true
            }
            R.id.menu_view_tasks -> {

                loadFragment(TaskListFragment.newInstance(tasks))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showAddTaskDialog() {
        val dialogFragment = AddTaskDialogFragment()
        dialogFragment.show(supportFragmentManager, "AddTaskDialog")
    }

    override fun onTaskAdded(task: Task) {
        Log.d("TaskListFragment1", "Adding task: ${task.task}")
        tasks.add(task)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is TaskListFragment) {
            fragment.addTask(task)
        }
    }
}
