package com.example.taskque.db

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.taskque.App
import com.example.taskque.models.ItemData
import com.example.taskque.utils.Constants.MyIntents.SHARED_PREFERENCE_KEY
import com.example.taskque.utils.Constants.MyIntents.USER_EMAIL
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreManager {
    private val TAG: String = "Fire_Store_Manager"
    val db = FirebaseFirestore.getInstance()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userEmail: String

  init {

      initializePreference()
  }


    fun initializePreference() {
        sharedPreferences = App.applicationContext().getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        userEmail = sharedPreferences.getString(USER_EMAIL, " ") ?: ""
    }

    fun addTask(taskData: HashMap<String, String>, taskType: String) {
        val timeStamp = System.currentTimeMillis()
        val type: String = when (taskType) {
            "Work" -> "WorkTask"
            "Priority" -> "PriorityTask"
            "Routine" -> "RoutineTask"
            else -> {
                " "
            }
        }
        db.collection("user").document(userEmail).collection("Tasks")
            .document("$type $timeStamp").set(taskData)
            .addOnSuccessListener {
                Log.d(TAG, "added successfully  $taskType")
            }.addOnFailureListener {
                Log.d(TAG, "task is not  added ")
            }
    }

    fun getData(callback: (ArrayList<ItemData>) -> Unit) {
        val itemDataList = mutableListOf<ItemData>()

        val collectionRef = db.collection("user").document(userEmail as String).collection("Tasks")

        collectionRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val documentData = document.data
                    Log.d(TAG, documentData.toString())
                    Log.d(TAG, documentData["title"].toString())
                    val itemData = ItemData(
                        documentData["tittle"] as String,
                        documentData["date"] as String,
                        documentData["time"] as String,
                        documentData["description"] as String,
                        documentData["toggleSide"] as String,
                        documentData["taskType"] as String,
                        1
                    )
                    itemDataList.add(itemData)
                    Log.d(TAG, itemDataList.toString())
                }
                callback(itemDataList as ArrayList<ItemData>)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "failed to get")
            }
    }


    fun getPriorityTask(callback: (ArrayList<ItemData>) -> Unit) {
        val taskDataList = mutableListOf<ItemData>()
        val priorityTasks = db.collection("user").document(userEmail as String).collection("Tasks")
            .whereEqualTo("taskType", "Priority")
        priorityTasks.get()
            .addOnSuccessListener { result ->
                for (priority in result) {
                    val priorityTaskData = priority.data

                    val itemData = ItemData(
                        titleinputtext = priorityTaskData["tittle"] as String,
                        dateinput = priorityTaskData["date"] as String,
                        timeinput = priorityTaskData["time"] as String,
                        contentinput = priorityTaskData["description"] as String,
                        side = priorityTaskData["toggleSide"] as String,
                        taskType = priorityTaskData["taskType"] as String,
                        id = 1
                    )
                    taskDataList.add(itemData)
                }
                callback(taskDataList as ArrayList<ItemData>)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Failed to get data")
            }
    }

    fun getRoutineTask(callback: (ArrayList<ItemData>) -> Unit) {
        val taskDataList = mutableListOf<ItemData>()
        val routineTasks = db.collection("user").document(userEmail as String).collection("Tasks")
            .whereEqualTo("taskType", "Routine")
        routineTasks.get()
            .addOnSuccessListener { result ->
                for (routine in result) {
                    val routineTasksData = routine.data

                    val itemData = ItemData(
                        titleinputtext = routineTasksData["tittle"] as String,
                        dateinput = routineTasksData["date"] as String,
                        timeinput = routineTasksData["time"] as String,
                        contentinput = routineTasksData["description"] as String,
                        side = routineTasksData["toggleSide"] as String,
                        taskType = routineTasksData["taskType"] as String,
                        id = 1
                    )
                    taskDataList.add(itemData)
                }
                callback(taskDataList as ArrayList<ItemData>)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Failed to get data")
            }
    }

    fun getWorkTask(callback: (ArrayList<ItemData>) -> Unit) {
        val taskDataList = mutableListOf<ItemData>()
        val workTasks = db.collection("user").document(userEmail as String).collection("Tasks")
            .whereEqualTo("taskType", "Work")
        workTasks.get()
            .addOnSuccessListener { result ->
                for (work in result) {
                    val workTasksData = work.data

                    val itemData = ItemData(
                        titleinputtext = workTasksData["tittle"] as String,
                        dateinput = workTasksData["date"] as String,
                        timeinput = workTasksData["time"] as String,
                        contentinput = workTasksData["description"] as String,
                        side = workTasksData["toggleSide"] as String,
                        taskType = workTasksData["taskType"] as String,
                        id = 1
                    )
                    taskDataList.add(itemData)
                }
                callback(taskDataList as ArrayList<ItemData>)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Failed to get data")
            }
    }
    fun getItemData(itemData: ItemData, documents: List<DocumentSnapshot>): ItemData? {
        for (document in documents) {
            val documentData = document.data
            val itemId = documentData?.get("id") as Int

            if (itemData.id == itemId) {
                return ItemData(
                    documentData["title"] as String,
                    documentData["date"] as String,
                    documentData["time"] as String,
                    documentData["content"] as String,
                    documentData["side"] as String,
                    documentData["taskType"] as String,
                    itemId
                )
            }
        }

        return null
    }

}

