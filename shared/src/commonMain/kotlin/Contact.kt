package com.dexmatic.shared

data class Contact(
    val name: String,
    val phone: String?,
    val email: String?,
    val company: String? = null  
)