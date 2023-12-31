package com.example.retrofitsamplewithcleanarchitecture.domain.models

data class LoginCredentials(
    var email: String?,
    var password: String?
)

data class RegistrationCredentials(
    var email: String?,
    var password: String?
)
