package com.goodluck.entity

import android.os.Parcel
import android.os.Parcelable
import ex.readMutableList

/**
 * Created by zf on 2020/3/17
 * 描述：
 */
data class UserHomeData(
    var booklist: MutableList<OriginBook>? = null,
    val user: UserData? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readMutableList(),
        parcel.readParcelable(UserData::class.java.classLoader)
    )

    override fun toString(): String {
        return "UserHomeData(booklist=$booklist, user=$user)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(booklist)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserHomeData> {
        override fun createFromParcel(parcel: Parcel): UserHomeData {
            return UserHomeData(parcel)
        }

        override fun newArray(size: Int): Array<UserHomeData?> {
            return arrayOfNulls(size)
        }
    }
}