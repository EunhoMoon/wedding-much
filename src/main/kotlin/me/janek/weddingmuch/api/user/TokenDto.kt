package me.janek.weddingmuch.api.user

class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
)

class TokenRequest(
    val refreshToken: String,
)