package com.example.homework_3.model

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val image: List<String> = emptyList()
)