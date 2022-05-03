package com.dietrich.myapplication

class State {
    companion object {
        val values = mutableMapOf<String, Any>()

        operator fun get(key: String): Any? = values[key]

        operator fun set(key: String, value: Any) {
            values[key] = value
        }
    }
}