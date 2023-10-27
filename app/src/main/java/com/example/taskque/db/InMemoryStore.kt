package com.example.taskque.db

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import com.example.taskque.models.ItemData
import com.example.taskque.utils.Constants
import com.example.taskque.utils.Constants.MyIntents.PREFERENCE_KEY
import com.example.taskque.utils.Constants.MyIntents.SHARED_PREFERENCE_KEY
import com.example.taskque.utils.Constants.MyIntents.taskPriority
import com.example.taskque.utils.Constants.MyIntents.taskRoutine
import com.example.taskque.utils.Constants.MyIntents.taskWork
import com.google.firebase.firestore.DocumentSnapshot
import org.json.JSONArray
import org.json.JSONObject

object InMemoryStore {

    private val TAG: String = "IN_MEMORY_STORE"
    private var myString: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    fun initializePreference(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
        myString = sharedPreferences.getString(SHARED_PREFERENCE_KEY, "") ?: ""
    }

    fun setData(newValue: String) {
        myString = newValue
        sharedPreferences.edit().putString(SHARED_PREFERENCE_KEY, myString).apply()
    }

    fun getData(): String {
        return sharedPreferences.getString(SHARED_PREFERENCE_KEY, "").toString()
    }

    fun updateData(jsonObject: JSONObject) {
        val jsonArray = JSONArray(myString)
        val updateJArray = JSONArray()
        for (i in 0 until jsonArray.length()) {

            var updateJobject = jsonArray.getJSONObject(i)
            if (jsonObject.getInt("id") == updateJobject.getInt("id")) {
                updateJobject = jsonObject
            }
            updateJArray.put(updateJobject)
        }
        myString = updateJArray.toString()
        sharedPreferences.edit().putString(SHARED_PREFERENCE_KEY, myString).apply()
    }

    fun getJsonObject(itemData: ItemData): JSONObject {
        val jsonArray = JSONArray(getData())
        var jsonObject = JSONObject()
        for (i in 0 until jsonArray.length()) {
            val editJobj = jsonArray.getJSONObject(i)

            if (itemData.id == editJobj.getInt("id")) {
                jsonObject = editJobj
                break
            }

        }
        return jsonObject
    }

    fun getRoutineData(): String {
        if (!TextUtils.isEmpty(getData())) {
            val jsonArr = JSONArray(getData())
            val routineJarray = JSONArray()
            for (i in 0 until jsonArr.length()) {
                val jsonObj = jsonArr.getJSONObject(i)
                val tasktype = jsonObj.getString("tasktype")
                if (tasktype.equals(taskRoutine, true)) {
                    routineJarray.put(jsonObj)
                }
            }
            return routineJarray.toString()
        } else {
            return ""
        }
    }

    fun getWorkData(): String {
        if (!TextUtils.isEmpty(getData())) {
            val jsonArray = JSONArray(getData())
            val workJarray = JSONArray()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val tasktype = jsonObject.get("tasktype")
                if (tasktype.equals(taskWork)) {
                    workJarray.put(jsonObject)
                }
            }
            return workJarray.toString()
        } else {
            return ""
        }
    }

    fun getPriorityData(): String {
        if (!TextUtils.isEmpty(getData())) {
            val jsonArray = JSONArray(getData())
            val priorityJArray = JSONArray()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val tasktype = jsonObject.get("tasktype")
                if (tasktype.equals(taskPriority)) {
                    priorityJArray.put(jsonObject)
                }
            }
            return priorityJArray.toString()
        } else {
            return ""
        }
    }

    fun deletAllData() {
        sharedPreferences.edit().clear().apply()
        Log.d(TAG, "hello")

    }







}
