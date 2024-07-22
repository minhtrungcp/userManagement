package com.user.management.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : UserModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(user : List<UserModel>)

    @Query("select * from user_table")
    fun getAllUsers(): Flow<List<UserModel>?>

    @Query("delete from user_table")
    suspend fun deleteAllUser()

    @Query("delete from user_table where id =:id")
    suspend fun deleteUser(id : Int)
}