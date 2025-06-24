package com.dexmatic.android

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        contract = ActivityResultContracts.TakePicturePreview()
    ) { result: Bitmap? ->
        bitmap = result
        result?.let {
            scope.launch(Dispatchers.IO) {
                val bytes = ByteArrayOutputStream().use { stream ->
                    it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.toByteArray()
                }
                val parsed = ocrService.parseContact(bytes)
                contact = parsed
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { launcher.launch(null) }) {
            Text("Capture Photo")
        }
        bitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 16.dp))
        }
        contact?.let { c ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("Name: ${'$'}{c.name}")
            Text("Company: ${'$'}{c.company ?: ""}")
            Text("Phone: ${'$'}{c.phone ?: ""}")
            Text("Email: ${'$'}{c.email ?: ""}")
        }
    }
}
