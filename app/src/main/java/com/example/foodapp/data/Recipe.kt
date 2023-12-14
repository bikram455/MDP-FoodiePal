package com.example.foodapp.data

import java.io.Serializable

data class Recipe(var name: String, var instruction: String, var ingredients: String) {
    constructor(name: String, instruction: String): this(name, instruction, ""){
        this.name = name;
        this.instruction = instruction
    }
}