// File: shared/src/androidMain/kotlin/com/dexmatic/shared/AndroidOcrService.kt

package com.dexmatic.shared

import android.graphics.BitmapFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

/**
 * Android-specific implementation of [OcrService] using ML Kit’s on-device Latin recognizer.
 */
class AndroidOcrService : OcrService {
    // Must supply a TextRecognizerOptions instance
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override suspend fun parseContact(imageData: ByteArray): Contact {
        // 1. Decode JPEG bytes → Bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        // 2. Wrap Bitmap for ML Kit
        val image = InputImage.fromBitmap(bitmap, /* rotationDegrees= */ 0)
        // 3. Run text recognition
        val result = recognizer.process(image).await()

        // 4. Split into non-blank, trimmed lines
        val lines = result.text
            .lines()
            .map(String::trim)
            .filter(String::isNotBlank)

        // 5. Heuristic extraction
        val name  = lines.firstOrNull().orEmpty()
        val phone = extractPhone(lines)
        val email = extractEmail(lines)

        return Contact(
            name  = name,
            phone = phone,
            email = email
        )
    }

    /** Finds the first line matching a simple phone-number pattern. */
    private fun extractPhone(lines: List<String>): String? {
        val phoneRegex = Regex("""\+?[0-9][0-9() \-]{6,}[0-9]""")
        return lines.firstOrNull { phoneRegex.matches(it) }
    }

    /** Finds the first line containing an email-like substring. */
    private fun extractEmail(lines: List<String>): String? {
        val emailRegex = Regex("""[A-Za-z0-9._%+\-]+@[A-Za-z0-9.\-]+\.[A-Za-z]{2,}""")
        return lines.firstOrNull { emailRegex.containsMatchIn(it) }
    }
}
