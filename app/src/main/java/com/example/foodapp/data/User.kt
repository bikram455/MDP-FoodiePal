package com.example.foodapp.data

import java.io.Serializable

data class User(val username: String,
                val password: String,
                val name: String,

    ): Serializable {

    override fun toString(): String = "$username  $password"
}

