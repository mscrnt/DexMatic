package com.dexmatic.shared

interface OcrService {
    suspend fun parseContact(imageData: ByteArray): Contact
}
