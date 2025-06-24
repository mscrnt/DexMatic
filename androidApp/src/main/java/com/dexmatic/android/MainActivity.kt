// File: androidApp/src/main/java/com/dexmatic/android/MainActivity.kt

package com.dexmatic.android

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.dexmatic.shared.Contact
import com.dexmatic.shared.FakeOcrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CameraScreen()
                }
            }
        }
    }
}

@Composable
fun CameraScreen() {
    val scope = rememberCoroutineScope()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var contact by remember { mutableStateOf<Contact?>(null) }
    val ocrService = remember { FakeOcrService() }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result: Bitmap? ->
        bitmap = result
        result?.let { bmp ->
            scope.launch(Dispatchers.IO) {
                val bytes = ByteArrayOutputStream().use { stream ->
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.toByteArray()
                }
                contact = ocrService.parseContact(bytes)
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { launcher.launch(null) }) {
            Text(text = "Capture Photo")
        }

        bitmap?.let { bmp ->
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Captured business card",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        contact?.let { c ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Name: ${c.name}")
            Text(text = "Phone: ${c.phone.orEmpty()}")
            Text(text = "Email: ${c.email.orEmpty()}")
        }
    }
}
