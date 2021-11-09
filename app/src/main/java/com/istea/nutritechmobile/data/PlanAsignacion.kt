package com.istea.nutritechmobile.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable

data class PlanAsignacion(
    val PlanAlimentacion: String,
    val NotasAdicionales: String,
    @ServerTimestamp
    val LastAssignment: Timestamp,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Timestamp::class.java.classLoader) ?: Timestamp.now()
    ) {
    }

    constructor() : this("", "", Timestamp.now())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PlanAlimentacion)
        parcel.writeString(NotasAdicionales)
        parcel.writeParcelable(LastAssignment, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlanAsignacion> {
        override fun createFromParcel(parcel: Parcel): PlanAsignacion {
            return PlanAsignacion(parcel)
        }

        override fun newArray(size: Int): Array<PlanAsignacion?> {
            return arrayOfNulls(size)
        }
    }
}