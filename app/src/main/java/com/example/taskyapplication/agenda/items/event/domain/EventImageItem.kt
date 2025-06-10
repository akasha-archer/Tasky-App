package com.example.taskyapplication.agenda.items.event.domain

import android.net.Uri

sealed class EventImageItem {
    data class PersistedImage(val imageUrl: String) : EventImageItem()
    data class NewImage(val imagePath: Uri) : EventImageItem()
}

