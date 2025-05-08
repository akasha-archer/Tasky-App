package com.example.taskyapplication.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}