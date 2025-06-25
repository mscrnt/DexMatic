package com.dexmatic.android.ui.scan

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
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
    var preview by remember { mutableStateOf<Bitmap?>(null) }
    val uiState by viewModel.uiState.collectAsState()

    val launcher = rememberLauncherForActivityResult(TakePicturePreview()) { result: Bitmap? ->
        preview = result
        viewModel.onImageCaptured(result)
    }

    LaunchedEffect(uiState) {
        if (uiState is CameraUiState.Success) {
            onScanComplete((uiState as CameraUiState.Success).contact)
            viewModel.reset()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { launcher.launch(null) }) {
            Text("Scan Business Card")
        }

        preview?.let { bmp ->
            Spacer(Modifier.height(16.dp))
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Captured card",
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
