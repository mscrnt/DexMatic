// File: androidApp/src/main/java/com/dexmatic/android/ui/scan/ScanCardScreen.kt

package com.dexmatic.android.ui.scan

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.dexmatic.shared.Contact
import com.dexmatic.shared.FakeOcrService
import com.dexmatic.shared.OcrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@Composable
fun ScanCardScreen(
    onScanComplete: (Contact) -> Unit,
    ocrService: OcrService = FakeOcrService()
) {
    val scope = rememberCoroutineScope()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var contact by remember { mutableStateOf<Contact?>(null) }

    val launcher = rememberLauncherForActivityResult(TakePicturePreview()) { result: Bitmap? ->
        bitmap = result
        result?.let { bmp ->
            scope.launch(Dispatchers.IO) {
                val bytes = ByteArrayOutputStream().use { stream ->
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.toByteArray()
                }
                // parsed contact
                contact = ocrService.parseContact(bytes)
            }
        }
    }

    // as soon as we get a Contact, call back
    LaunchedEffect(contact) {
        contact?.let(onScanComplete)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { launcher.launch(null) }) {
            Text("Scan Business Card")
        }

        bitmap?.let { bmp ->
            Spacer(Modifier.height(16.dp))
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Captured card",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}
