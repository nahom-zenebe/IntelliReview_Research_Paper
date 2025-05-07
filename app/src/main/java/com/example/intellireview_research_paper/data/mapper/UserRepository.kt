package com.example.intellireview_research_paper.data.mapper
import com.example.intellireview_research_paper.model.usermodel

interface UserRepository {
    suspend fun Signup(name:String,email:String,password:Number,country:String,role:String):usermodel
    suspend fun Login(email:String,password:Number):usermodel
    suspend fun Logout()
    suspend fun updateprofile(ProfilePic:String): usermodel


}


