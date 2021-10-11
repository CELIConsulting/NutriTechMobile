package com.istea.nutritechmobile.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class DayliUploadRegistry(
    val ImageName: String,
    val DoExcersice: Boolean,
    val Observations: String,
    @ServerTimestamp
    val LastAssignment: Timestamp,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readParcelable(Timestamp::class.java.classLoader) ?: Timestamp.now()
    )

    constructor() : this("", false, "", Timestamp.now())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ImageName)
        parcel.writeByte(if (DoExcersice) 1 else 0)
        parcel.writeString(Observations)
        parcel.writeParcelable(LastAssignment, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DayliUploadRegistry> {
        override fun createFromParcel(parcel: Parcel): DayliUploadRegistry {
            return DayliUploadRegistry(parcel)
        }

        override fun newArray(size: Int): Array<DayliUploadRegistry?> {
            return arrayOfNulls(size)
        }
    }

}