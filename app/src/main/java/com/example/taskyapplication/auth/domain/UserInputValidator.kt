package com.example.taskyapplication.auth.domain

import javax.inject.Inject

class UserInputValidator @Inject constructor(
    private val patternValidator: PatternValidator
) {
    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email)
    }

    fun isNameValid(fullName: String): Boolean {
        val trimmed = fullName.trim()
        val words = trimmed.split(" ")
        val hasValidLength = trimmed.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH
        val hasTwoWords = words.size >= 2
        val hasOnlyLetters = trimmed.all { it.isLetter() || it.isWhitespace() }

        return hasTwoWords && hasOnlyLetters && hasValidLength
    }

    fun isPasswordValid(password: String): Boolean {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCaseCharacter = password.any { it.isLowerCase() }
        val hasUpperCaseCharacter = password.any { it.isUpperCase() }

       return hasMinLength && hasDigit && hasLowerCaseCharacter && hasUpperCaseCharacter
    }

    companion object {
        const val MIN_NAME_LENGTH = 4
        const val MAX_NAME_LENGTH = 50
        const val MIN_PASSWORD_LENGTH = 9
    }
}
