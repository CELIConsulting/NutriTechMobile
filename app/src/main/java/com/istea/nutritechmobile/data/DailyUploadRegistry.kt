package com.istea.nutritechmobile.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class DailyUploadRegistry(
    val ImageName: String,
    val UrlImage: String,
    val DoExcersice: Boolean,
    val Observations: String,
    val Food: String,
    @ServerTimestamp
    val LastAssignment: Timestamp = Timestamp.now(),
) : Parcelable {

    constructor() : this("", "", false, "", "", Timestamp.now())

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Timestamp::class.java.classLoader) ?: Timestamp.now()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ImageName)
        parcel.writeString(UrlImage)
        parcel.writeByte(if (DoExcersice) 1 else 0)
        parcel.writeString(Observations)
        parcel.writeString(Food)
        parcel.writeParcelable(LastAssignment, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DailyUploadRegistry> {
        override fun createFromParcel(parcel: Parcel): DailyUploadRegistry {
            return DailyUploadRegistry(parcel)
        }

        override fun newArray(size: Int): Array<DailyUploadRegistry?> {
            return arrayOfNulls(size)
        }
    }


}

enum class Food{
    DESAYUNO,
    ALMUERZO,
    MERIENDA,
    CENA
}