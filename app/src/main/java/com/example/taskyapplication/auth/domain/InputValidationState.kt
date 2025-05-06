package com.example.taskyapplication.auth.domain

data class NameValidationState(
    val isEmptyInput: Boolean = false,
    val isTooLong: Boolean = false,
    val isTwoWords: Boolean = false,
    val hasOnlyLetters: Boolean = false
) {
    val isValid: Boolean
        get() = !isEmptyInput && !isTooLong && isTwoWords && hasOnlyLetters
}

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasNumber && hasLowerCaseCharacter && hasUpperCaseCharacter
}