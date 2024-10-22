package com.goodluck.entity

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val uid: String? = null,
    val username: String? = null,
    val device: String? = null,
    val gender: String? = null,
    val account: String? = null,
    val actype: Int = 0,
    val fanscnt: Int = 0,
    val followcnt: Int = 0,
    val followdatetime: String? = null,//2019-11-28 21:13:45.0
    val smallavatar: String? = null,
    val largeavatar: String? = null,
    val viptype: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(username)
        parcel.writeString(device)
        parcel.writeString(gender)
        parcel.writeString(account)
        parcel.writeInt(actype)
        parcel.writeInt(fanscnt)
        parcel.writeInt(followcnt)
        parcel.writeString(followdatetime)
        parcel.writeString(smallavatar)
        parcel.writeString(largeavatar)
        parcel.writeInt(viptype)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}