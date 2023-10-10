package com.example.brewmaster.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brewmaster.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM Users ORDER BY id")
    fun fetchAllUsers(): MutableList<User?>?

    @Query("SELECT * FROM Users WHERE id = :id")
    fun fetchUserById(id: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User) //Lo hace en funcion de la primary key

    @Delete
    fun delete(user: User)
}