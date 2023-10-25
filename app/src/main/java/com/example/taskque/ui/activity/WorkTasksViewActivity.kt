package com.example.taskque.ui.activity

import android.annotation.SuppressLint
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
import com.example.taskque.utils.Constants
import com.example.taskque.utils.Constants.MyIntents.TASK_TYPE_ITEM_EXTRA
import com.example.taskque.utils.Constants.MyIntents.taskWork
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray

class WorkTasksViewActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.work_tasks_view)
        val toolbar = findViewById<Toolbar>(R.id.worktaskheader)
        toolbar.title = " Work Task"
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        val workaddbutton = findViewById<FloatingActionButton>(R.id.workaddbutton)
        workaddbutton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddTaskViewActivity::class.java)
            intent.putExtra(TASK_TYPE_ITEM_EXTRA, taskWork)
            startActivity(intent)
        })
        setData()
    }

    fun setData() {
        val workrecycleview = findViewById<RecyclerView>(R.id.workRecycleview)
        val workadapter = RecycleViewAdapter()
        val inputdata = ArrayList<ItemData>()
        workrecycleview.layoutManager = LinearLayoutManager(this)
        workrecycleview.adapter = workadapter
        if (!TextUtils.isEmpty(InMemoryStore.getWorkData())) {
            val jsonArray = JSONArray(InMemoryStore.getWorkData())
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
            workadapter.setList(inputdata)
            workadapter.setListener(object : DataItemClickListener {
                override fun onItemClicked(model: ItemData) {
                    val intent =
                        Intent(this@WorkTasksViewActivity, DetailedViewActivity::class.java);
                    intent.putExtra(Constants.MyIntents.DATA_ITEM_EXTRA, model);
                    startActivity(intent)
                }
            })
        }
    }


    override fun onResume() {
        super.onResume()
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}