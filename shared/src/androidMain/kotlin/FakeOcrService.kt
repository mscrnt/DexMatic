package com.dexmatic.shared

class FakeOcrService : OcrService {
    private val parser = CardParser()

    override suspend fun parseContact(imageData: ByteArray): Contact {
        val rawText = performAndroidOcr(imageData)
        return parser.parse(rawText)
    }
}
