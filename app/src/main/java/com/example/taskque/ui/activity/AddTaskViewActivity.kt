package com.example.taskque.ui.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskque.R
import com.example.taskque.db.InMemoryStore
import com.example.taskque.utils.Constants.MyIntents.TASK_TYPE_ITEM_EXTRA
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class AddTaskViewActivity : AppCompatActivity() {

    private val TAG: String = "ADD_TASK_VIEW"
    private var taskType: String = ""

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtaskview)
        val timeInput = findViewById<EditText>(R.id.timeInput)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val titletext = findViewById<TextInputEditText>(R.id.title_input_text)
        val contentInput = findViewById<EditText>(R.id.contentInput)
        val submitbutton = findViewById<Button>(R.id.submitButton)
        val datetext = findViewById<TextView>(R.id.textdate)
        val texttime = findViewById<TextView>(R.id.textTime)
        val timeButton = findViewById<ImageButton>(R.id.timebutton)
        val calendarButton = findViewById<ImageButton>(R.id.calendarButton)
        val typeradiogroup = findViewById<RadioGroup>(R.id.radiotaskgroups)
        val radiowork = findViewById<RadioButton>(R.id.radiobtnwork)
        val radiopriority = findViewById<RadioButton>(R.id.radiobtnpriority)
        val radioroutine = findViewById<RadioButton>(R.id.radiobtnRoutine)
        val togglesidebtn = findViewById<ToggleButton>(R.id.togglesidebtn)
        val categorieslayout = findViewById<ConstraintLayout>(R.id.categoreiesLayout)
        taskType = intent.getStringExtra(TASK_TYPE_ITEM_EXTRA).toString()
        if (!TextUtils.isEmpty(taskType)) {
            categorieslayout.visibility = View.INVISIBLE
        } else {
            categorieslayout.visibility = View.VISIBLE
        }


        val jsonObj = JSONObject()
        submitbutton.setOnClickListener(View.OnClickListener {
            if (titletext.text!!.isEmpty() || contentInput.text.isEmpty() || dateInput.text.isEmpty() || timeInput.text.isEmpty()) {
                Toast.makeText(this, "Please fill all required Information ", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(taskType)) {
                val checkRadioBtn = typeradiogroup.checkedRadioButtonId
                if (checkRadioBtn == -1) {
                    Toast.makeText(this, "please select  task type ", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                } else {

                    when (checkRadioBtn) {
                        R.id.radiobtnwork -> {
                            taskType = radiowork.text.toString()

                        }

                        R.id.radiobtnpriority -> {
                            taskType = radiopriority.text.toString()

                        }

                        R.id.radiobtnRoutine -> {
                            taskType = radioroutine.text.toString()
                        }

                        else -> {
                            return@OnClickListener
                        }
                    }
                }
            }
            val toggleSide = if (togglesidebtn.isChecked) "Urgent" else "Non-Urgent"
            jsonObj.put("title", titletext.text.toString())
            jsonObj.put("date", dateInput.text.toString())
            jsonObj.put("time", timeInput.text)
            jsonObj.put("content", contentInput.text.toString())
            jsonObj.put("side", toggleSide)
            jsonObj.put("tasktype", taskType)
            Log.d(TAG, jsonObj.toString())
            if (TextUtils.isEmpty(InMemoryStore.getData())) {
                val jsonArr = JSONArray()
                jsonObj.put("id", 1)
                jsonArr.put(jsonObj);
                InMemoryStore.setData(jsonArr.toString())
            } else {
                val jsonArr = JSONArray(InMemoryStore.getData());
                jsonObj.put("id", jsonArr.length() + 1)
                jsonArr.put(jsonObj);
                InMemoryStore.setData(jsonArr.toString());
            }
            finish()
        })


        datetext.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })

        calendarButton.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })


        timeButton.setOnClickListener(View.OnClickListener {
            ShowtimePicker()
        })

        texttime.setOnClickListener(View.OnClickListener {
            ShowtimePicker()
        })
        val addtasktoobar = findViewById<Toolbar>(R.id.addtaskheader)
        addtasktoobar.title = "TaskQue"
        addtasktoobar.subtitle = "add new task here"
        setSupportActionBar(addtasktoobar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
    }

    fun showDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val date: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        val datePickerDialog: DatePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedyear, selectedmonth, selecteddate ->
                val selectedDate = "$selecteddate/${selectedmonth + 1}/$selectedyear"
                updateDate(selectedDate)
            },
            date, month, year
        )
        datePickerDialog.show()

    }

    fun updateDate(data: String) {
        val dateText = findViewById<EditText>(R.id.dateInput)
        dateText.setText(data).toString()
        dateText.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })
    }

    fun ShowtimePicker() {
        val calendar = Calendar.getInstance()
        val hh: Int = calendar.get(Calendar.HOUR)
        val mm: Int = calendar.get(Calendar.MINUTE)
        val timePickerDialog: TimePickerDialog = TimePickerDialog(
            this, TimePickerDialog.OnTimeSetListener { view, selecthour, selectminute ->
                val Selecttime = "$selecthour:$selectminute"
                UpdateTime(Selecttime)

            },
            hh, mm, false
        )
        timePickerDialog.show()
    }

    fun UpdateTime(time: String) {
        val timeInput = findViewById<EditText>(R.id.timeInput)
        timeInput.setText(time).toString()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}


