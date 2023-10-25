package com.example.taskque.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.Toolbar
import com.example.taskque.R
import com.example.taskque.db.InMemoryStore
import com.example.taskque.models.ItemData
import com.example.taskque.utils.Constants.MyIntents.DATA_ITEM_EXTRA
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject
import java.util.Calendar

class EditViewActivity : AppCompatActivity() {
    private val TAG: String = "EDIT_VIEW_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_view)
        val titleInputtext = findViewById<TextInputEditText>(R.id.titleInputtext)
        val contentinputtext = findViewById<TextInputEditText>(R.id.contentinputtext)
        val dateinputtext = findViewById<MaterialAutoCompleteTextView>(R.id.dateinputtext)
        val timeinputtext = findViewById<MaterialAutoCompleteTextView>(R.id.timeinputtext)
        val saveButton = findViewById<Button>(R.id.savebutton)
        val sidebutton = findViewById<ToggleButton>(R.id.sidebutton)
        val edittoolbar = findViewById<Toolbar>(R.id.editheaderbar)
        val tasktypespinner = findViewById<Spinner>(R.id.tasktypespinner)
        val item = arrayOf("Work", "Priority", "Routine")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_layout, item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tasktypespinner.adapter = adapter
        edittoolbar.title = " Edit View"
        edittoolbar.subtitle = "you can recreate your task here"
        setSupportActionBar(edittoolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        dateinputtext.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })
        timeinputtext.setOnClickListener(View.OnClickListener {
            ShowtimePicker()
        })

      val itemData : ItemData ?=  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DATA_ITEM_EXTRA,ItemData::class.java)
        } else {
           intent.getParcelableExtra(DATA_ITEM_EXTRA)
        }
        if (itemData != null) {
            contentinputtext.setText(itemData.contentinput)
            titleInputtext.setText(itemData.titleinputtext)
            dateinputtext.setText(itemData.dateinput)
            timeinputtext.setText(itemData.timeinput)
            sidebutton.setText(itemData.side)
            val selectedvalue = itemData.taskType
            val position = adapter.getPosition(selectedvalue)
            tasktypespinner.setSelection(position)

        }
        val jsonObject = JSONObject()
        var selecteditem: String = ""
        tasktypespinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    selecteditem = parentView!!.getItemAtPosition(p2).toString()

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(
                        this@EditViewActivity,
                        "please select type",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

        saveButton.setOnClickListener(View.OnClickListener {

            if (timeinputtext.text!!.isEmpty() || dateinputtext.text!!.isEmpty() || titleInputtext.text!!.isEmpty() || contentinputtext.text!!.isEmpty()) {
                Toast.makeText(this, "Please fill required information", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else {

                val editSide = if (sidebutton.isChecked) "Non-Urgent" else "Urgent"
                jsonObject.put("title", titleInputtext.text.toString())
                jsonObject.put("date", dateinputtext.text.toString())
                jsonObject.put("time", timeinputtext.text.toString())
                jsonObject.put("content", contentinputtext.text.toString())
                jsonObject.put("side", editSide)
                jsonObject.put("tasktype", selecteditem)
                jsonObject.put("id", itemData!!.id)

                InMemoryStore.updateData(jsonObject)
                finish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDatePicker() {

        val calendar: Calendar = Calendar.getInstance()
        val date: Int = calendar.get(Calendar.DATE)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedyear, selectedmonth, selecteddate ->
                val selecteDate = "${selecteddate}/${selectedmonth + 1}/${selectedyear}"
                updateDate(selecteDate)
            },
            date,
            month,
            year
        )
        datePickerDialog.show()


    }

    private fun updateDate(data: String) {
        val dateinputtext = findViewById<MaterialAutoCompleteTextView>(R.id.dateinputtext)
        dateinputtext.setText(data)

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
        val timeInput = findViewById<MaterialAutoCompleteTextView>(R.id.timeinputtext)
        timeInput.setText(time)

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"hello")
    }


}
