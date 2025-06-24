package com.dexmatic.shared

class FakeOcrService : OcrService {
    override suspend fun parseContact(imageData: ByteArray): Contact {
        // TODO: replace with real OCR processing
        return Contact(
            name = "John Doe",
            phone = "123-456-7890",
            email = "john@example.com"
        )
    }
}
