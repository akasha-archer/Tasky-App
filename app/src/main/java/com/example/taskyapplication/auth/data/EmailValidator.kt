package com.example.taskyapplication.auth.data

import android.util.Patterns
import com.example.taskyapplication.auth.domain.PatternValidator

object EmailPatternValidator: PatternValidator {

    override fun matches(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}