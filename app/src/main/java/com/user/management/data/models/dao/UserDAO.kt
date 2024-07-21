package com.user.management.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user : UserModel) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(user : List<UserModel>) : LongArray

    @Query("select * from user_table ")
    fun getAllUsers(): List<UserModel>?

    @Query("select COUNT(*) FROM user_table")
    fun getUsersSize(): Long

    @Query("delete from user_table")
    fun deleteAllUser()

    @Query("delete from user_table where id =:id")
    fun deleteUser(id : Int)

}