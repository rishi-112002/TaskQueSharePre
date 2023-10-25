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
import com.example.taskque.utils.Constants
import com.example.taskque.utils.Constants.MyIntents.TASK_TYPE_ITEM_EXTRA
import com.example.taskque.utils.Constants.MyIntents.taskRoutine
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray

class RoutineTaskViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.routine_task_view)
        val toolbar = findViewById<Toolbar>(R.id.routinetaskheader)
        setSupportActionBar(toolbar)
        toolbar.title = " Routine Task"
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        val routineaddbutton = findViewById<FloatingActionButton>(R.id.routineaddbutton)
        routineaddbutton.setOnClickListener(View.OnClickListener {
            val intent =  Intent(this, AddTaskViewActivity::class.java)
            intent.putExtra(TASK_TYPE_ITEM_EXTRA,taskRoutine)
            startActivity(intent)

        })
        setData()
    }

    fun setData() {
        val recycleView = findViewById<RecyclerView>(R.id.routineRecycleview)
        var customAdapter = RecycleViewAdapter()
        val inputData = ArrayList<ItemData>()
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = customAdapter
        if (!TextUtils.isEmpty(InMemoryStore.getRoutineData())) {
            val jsonArray = JSONArray(InMemoryStore.getRoutineData())

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val itemData = ItemData(
                    jsonObject.getString("title"),
                    jsonObject.getString("date"),
                    jsonObject.getString("time"),
                    jsonObject.getString("content"),
                    jsonObject.getString("side"),
                    jsonObject.getString("tasktype"),
                    jsonObject.getInt("id")

                )
                inputData.add(itemData)
            }

            customAdapter.setList(inputData)
            customAdapter.setListener(object : DataItemClickListener {
                override fun onItemClicked(model: ItemData) {
                    val intent =
                        Intent(this@RoutineTaskViewActivity, DetailedViewActivity::class.java);
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