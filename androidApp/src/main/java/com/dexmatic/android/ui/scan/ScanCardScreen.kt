// File: androidApp/src/main/java/com/dexmatic/android/ui/scan/ScanCardScreen.kt

package com.dexmatic.android.ui.scan

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dexmatic.android.ui.viewmodel.CameraUiState
import com.dexmatic.android.ui.viewmodel.CameraViewModel
import com.dexmatic.shared.Contact

@Composable
fun ScanCardScreen(
    onScanComplete: (Contact) -> Unit,
    viewModel: CameraViewModel = viewModel()
) {
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val uiState by viewModel.uiState.collectAsState()

    // 1️⃣ Preview launcher – needs to be declared before the permission launcher
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bmp: Bitmap? ->
        previewBitmap = bmp
        viewModel.onImageCaptured(bmp)
    }

    // 2️⃣ Permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        if (granted) {
            takePictureLauncher.launch(null)
        }
    }

    // 3️⃣ When OCR completes, navigate
    LaunchedEffect(uiState) {
        if (uiState is CameraUiState.Success) {
            onScanComplete((uiState as CameraUiState.Success).contact)
            viewModel.reset()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Scan Business Card")
        }

        previewBitmap?.let { bmp ->
            Spacer(Modifier.height(16.dp))
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Captured card image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        if (uiState is CameraUiState.Loading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
