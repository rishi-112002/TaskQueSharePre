package com.example.taskque.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskque.models.ItemData
import com.example.taskque.R
import com.example.taskque.db.InMemoryStore
import com.example.taskque.interfaces.DataItemClickListener
import com.example.taskque.ui.adapter.RecycleViewAdapter
import com.example.taskque.utils.Constants.MyIntents.DATA_ITEM_EXTRA
import com.example.taskque.utils.Constants.MyIntents.TASK_TYPE_ITEM_EXTRA
import com.example.taskque.utils.Constants.MyIntents.taskPriority
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class PriorityTaskViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.priority_task_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_header_priority_task)
        toolbar.title = " Priority Task"
        setSupportActionBar(toolbar)
        val prorityaddbutton = findViewById<FloatingActionButton>(R.id.priorityaddbutton)
        prorityaddbutton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddTaskViewActivity::class.java)
            intent.putExtra(TASK_TYPE_ITEM_EXTRA, taskPriority)
            startActivity(intent)
        })
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)

        }
        setData()
    }

    fun setData() {
        val recycleview = findViewById<RecyclerView>(R.id.Priority_task_recycleview)
        var customadapter = RecycleViewAdapter()
        val inputdata = ArrayList<ItemData>()
        recycleview.layoutManager = LinearLayoutManager(this)
        recycleview.adapter = customadapter
        if (!TextUtils.isEmpty(InMemoryStore.getPriorityData())) {
            val jsonArray = JSONArray(InMemoryStore.getPriorityData())
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val itemData = ItemData(
                    jsonObj.getString("title"),
                    jsonObj.getString("date"),
                    jsonObj.getString("time"),
                    jsonObj.getString("content"),
                    jsonObj.getString("side"),
                    jsonObj.getString("tasktype"),
                    jsonObj.getInt("id")
                )
                inputdata.add(itemData)
            }

            customadapter.setList(inputdata)
            customadapter.setListener(object : DataItemClickListener {
                override fun onItemClicked(model: ItemData) {
                    val intent =
                        Intent(this@PriorityTaskViewActivity, DetailedViewActivity::class.java);
                    intent.putExtra(DATA_ITEM_EXTRA, model);
                    startActivity(intent)
                }
            })

        }

    }

    override fun onResume() {
        setData()
        super.onResume()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}