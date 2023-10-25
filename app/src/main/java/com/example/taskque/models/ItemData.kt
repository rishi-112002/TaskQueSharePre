package com.example.taskque.models

import android.os.Parcel
import android.os.Parcelable

data class ItemData(
    val titleinputtext: String,
    val dateinput: String,
    var timeinput: String,
    val contentinput: String,
    val side: String,
    val taskType: String,
    val id:Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titleinputtext)
        parcel.writeString(dateinput)
        parcel.writeString(timeinput)
        parcel.writeString(contentinput)
        parcel.writeString(side)
        parcel.writeString(taskType)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemData> {
        override fun createFromParcel(parcel: Parcel): ItemData {
            return ItemData(parcel)
        }

        override fun newArray(size: Int): Array<ItemData?> {
            return arrayOfNulls(size)
        }
    }
}



