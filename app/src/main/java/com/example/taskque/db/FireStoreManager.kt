package com.example.taskque.db

import android.util.Log
import com.example.taskque.models.ItemData
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreManager {
    private val TAG: String = "Fire_Store_Manager"
    val db = FirebaseFirestore.getInstance()

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
        db.collection("Tasks").document("$type $timeStamp").set(taskData)
            .addOnSuccessListener {
                Log.d(TAG, "added successfully  $taskType")
            }.addOnFailureListener {
                Log.d(TAG, "task is not  added ")
            }
    }

    fun getData(callback: (ArrayList<ItemData>) -> Unit) {
        val itemDataList = mutableListOf<ItemData>()

        val collectionRef = db.collection("Tasks")

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
        val priorityTasks = db.collection("Tasks").whereEqualTo("taskType", "Priority")
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
        val routineTasks = db.collection("Tasks").whereEqualTo("taskType", "Routine")
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
        val workTasks = db.collection("Tasks").whereEqualTo("taskType", "Work")
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

}

