package com.example.taskyapplication.auth.domain

import android.util.Log
import javax.inject.Inject

class UserInputValidator @Inject constructor(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String): Boolean {
        val match = patternValidator.matches(email)
        Log.d("EmailValidator", "isValidEmail: $match")
        return match
    }

    fun validateFullName(fullName: String): NameValidationState {
        val trimmedName = fullName.trim()
        val isEmptyInput = trimmedName.isEmpty()
        val isTooLong = trimmedName.length > MAX_NAME_LENGTH
        val isTwoWords = trimmedName.contains(" ")
        val containsOnlyLettersAndSpaces = trimmedName.matches(Regex("^[a-zA-Z]+(\\s[a-zA-Z]+)+\$"))

        Log.d("NameValidator", "validateFullName: isEmptyInput=$isEmptyInput, isTooLong=$isTooLong, isTwoWords=$isTwoWords, containsOnlyLettersAndSpaces=$containsOnlyLettersAndSpaces")
        return NameValidationState(
            isEmptyInput = isEmptyInput,
            isTooLong = isTooLong,
            isTwoWords = isTwoWords,
            hasOnlyLetters = containsOnlyLettersAndSpaces
        )
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCaseCharacter = password.any { it.isLowerCase() }
        val hasUpperCaseCharacter = password.any { it.isUpperCase() }
        Log.d("PasswordValidator", "validatePassword: hasMinLength=$hasMinLength, hasDigit=$hasDigit, hasLowerCaseCharacter=$hasLowerCaseCharacter, hasUpperCaseCharacter=$hasUpperCaseCharacter")

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasDigit,
            hasLowerCaseCharacter = hasLowerCaseCharacter,
            hasUpperCaseCharacter = hasUpperCaseCharacter
        )
    }

    companion object {
        const val MAX_NAME_LENGTH = 50
        const val MIN_PASSWORD_LENGTH = 9
    }
}
