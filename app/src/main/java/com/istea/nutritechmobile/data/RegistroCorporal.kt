package com.istea.nutritechmobile.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class RegistroCorporal(
    var ImageName: String,
    var UrlImage: String,
    var Peso: Float,
    var MedidaCintura: Float,
    @ServerTimestamp
    var LastAssignment: Timestamp = Timestamp.now()
) : Parcelable {

    constructor() : this("", "", 0f, 0f, Timestamp.now())

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readParcelable(Timestamp::class.java.classLoader) ?: Timestamp.now()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ImageName)
        parcel.writeString(UrlImage)
        parcel.writeFloat(Peso)
        parcel.writeFloat(MedidaCintura)
        parcel.writeParcelable(LastAssignment, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegistroCorporal> {
        override fun createFromParcel(parcel: Parcel): RegistroCorporal {
            return RegistroCorporal(parcel)
        }

        override fun newArray(size: Int): Array<RegistroCorporal?> {
            return arrayOfNulls(size)
        }
    }
}
