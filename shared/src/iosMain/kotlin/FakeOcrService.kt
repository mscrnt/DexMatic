package com.dexmatic.shared

class FakeOcrService : OcrService {
    override suspend fun parseContact(imageData: ByteArray): Contact {
        // TODO: Implement OCR for iOS
        return Contact(name = "", phone = null, email = null, company = null)
    }
}
