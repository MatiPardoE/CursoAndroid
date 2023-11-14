package com.example.brewmaster.entities

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

data class CoffeeBean(
    val Brand: String = "Personal",
    val EAN13: String = "0000000000000",
    val Name: String = "Basic",
    val Origin: String = "Argentina",
    val Picture: String = "https://images.pond5.com/seamless-pattern-coffee-bean-handdrawn-illustration-219669180_iconl_nowm.jpeg",
    val Score: String = "1/10",
    val Variety: String = "Rica"
)
