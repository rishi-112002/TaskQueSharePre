package com.example.taskque.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.taskque.R
import com.example.taskque.db.FireStoreManager
import com.example.taskque.db.InMemoryStore
import com.example.taskque.models.ItemData
import com.example.taskque.utils.Constants.MyIntents.DATA_ITEM_EXTRA
import org.json.JSONArray
import org.json.JSONObject

class DetailedViewActivity : AppCompatActivity() {
    private lateinit var detailtasktype: TextView
    private lateinit var detailinputside: TextView
    private lateinit var detailinputcontent: TextView
    private lateinit var detailinputtime: TextView
    private lateinit var detailinputdate: TextView
    private lateinit var detailinputtitle: TextView
    private val TAG: String = "DETAILED_VIEW_ACTIVITY"
     private var itemData: ItemData? = null
//    val fireStoreManager = FireStoreManager()
//    val fireStoreData = fireStoreManager.getData {taskdata->
//
//    }
//    val retriveItemData = fireStoreManager.getItemData(itemData , fireStoreManager)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_view_activity)
        val detailedtoolbar = findViewById<Toolbar>(R.id.detailed_toolbar)
        setSupportActionBar(detailedtoolbar)
         detailinputtitle = findViewById(R.id.detailedinputtitle)
         detailinputdate = findViewById(R.id.detailedinputdate)
         detailinputtime = findViewById(R.id.detailedinputtime)
         detailinputcontent = findViewById(R.id.detailedinputcontent)
         detailinputside = findViewById(R.id.detailedinputside)
         detailtasktype = findViewById(R.id.detailtasktype)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
           itemData = intent.getParcelableExtra(DATA_ITEM_EXTRA,ItemData::class.java)
        } else {
            itemData = intent.getParcelableExtra(DATA_ITEM_EXTRA)
        }
        val editbutton = findViewById<Button>(R.id.editButton)
        editbutton.setOnClickListener {
            val intent = Intent(this, EditViewActivity::class.java)
            intent.putExtra(DATA_ITEM_EXTRA, itemData)
            startActivity(intent)
        }
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        detailedtoolbar.title = "TaskQue"
        detailedtoolbar.subtitle = "detail's of your task "

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        
//        detailinputtitle.text = title.toString()
//        detailinputdate.text = date.toString()
//        detailinputtime.text = time.toString()
//        detailinputcontent.text = content.toString()
//        detailinputside.text = side.toString()
//        detailtasktype.text = taskType.toString()
//        Log.d(TAG, jsonObject.toString())
    }
}