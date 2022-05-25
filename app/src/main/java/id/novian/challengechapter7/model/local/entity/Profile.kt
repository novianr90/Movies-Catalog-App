package id.novian.challengechapter7.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "full_name") val fullName: String = "",
    @ColumnInfo(name = "birth_date") val birthDate: String = "",
    @ColumnInfo(name = "address") val address: String = ""
)