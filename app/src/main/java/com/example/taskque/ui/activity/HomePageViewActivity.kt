package com.example.taskque.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskque.models.ItemData
import com.example.taskque.R
import com.example.taskque.db.FireStoreManager
import com.example.taskque.db.InMemoryStore
import com.example.taskque.interfaces.DataItemClickListener
import com.example.taskque.ui.adapter.RecycleViewAdapter
import com.example.taskque.utils.Constants.MyIntents.DATA_ITEM_EXTRA
import com.example.taskque.utils.Constants.MyIntents.TASK_TYPE_ITEM_EXTRA
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FieldPath.documentId
import org.json.JSONArray

class HomePageViewActivity : AppCompatActivity() {
    private val TAG: String = "LIST_VIEW_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)
        val addTaskActivity = AddTaskViewActivity()
        val button = findViewById<FloatingActionButton>(R.id.addButton)
        val workTaskButton = findViewById<ImageButton>(R.id.WorkTaskButton)
        val priorityTakButton = findViewById<ImageButton>(R.id.PriorityTaskButton)
        val routineTaskButton = findViewById<ImageButton>(R.id.RoutineTaskButton)
        val toolbar = findViewById<Toolbar>(R.id.toolbarheader)
        val userimage = findViewById<ImageButton>(R.id.userimage)
        setData()
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.reset -> {
                    InMemoryStore.deletAllData()
                    setData()
                    Toast.makeText(this, "all enters are deleted", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }


                R.id.exit -> {
                    finish()
                    return@setOnMenuItemClickListener true
                }

                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }

        button.setOnClickListener {
            addtask()
        }
        workTaskButton.setOnClickListener {
            worktask()
        }

        priorityTakButton.setOnClickListener {
            priorityTask()
        }
        routineTaskButton.setOnClickListener {
            routineTask()
        }

    }

    private fun setData() {
        val recycleviewOngoing = findViewById<RecyclerView>(R.id.recycleOngoingTask)
        val customadapter = RecycleViewAdapter()
        recycleviewOngoing.layoutManager = LinearLayoutManager(this)
        recycleviewOngoing.adapter = customadapter

        FireStoreManager().getData { taskData ->
            customadapter.setList(taskData)
            Log.d(TAG, taskData.toString())
        }

        customadapter.setListener(object : DataItemClickListener {
            override fun onItemClicked(model: ItemData) {
                val intent =
                    Intent(this@HomePageViewActivity, DetailedViewActivity::class.java);
                intent.putExtra(DATA_ITEM_EXTRA, model);
                startActivity(intent)
            }
        })
    }


    override fun onResume() {
        super.onResume()
        setData()
    }

    fun addtask() {
        val intent = Intent(this, AddTaskViewActivity::class.java)
        intent.putExtra(TASK_TYPE_ITEM_EXTRA, "")
        startActivity(intent)
    }

    fun routineTask() {
        startActivity(Intent(this, RoutineTaskViewActivity::class.java));
    }

    fun priorityTask() {
        val intent = Intent(
            this, PriorityTaskViewActivity::class.java
        )
        startActivity(intent)

    }

    fun worktask() {
        val intent = Intent(
            this, WorkTasksViewActivity::class.java
        )
        startActivity(intent)
    }
}




