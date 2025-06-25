// File: androidApp/src/main/java/com/dexmatic/android/ui/viewmodel/CameraViewModel.kt

package com.dexmatic.android.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexmatic.shared.AndroidOcrService      // <— import this
import com.dexmatic.shared.Contact
import com.dexmatic.shared.OcrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

sealed class CameraUiState {
    object Idle    : CameraUiState()
    object Loading : CameraUiState()
    data class Success(val contact: Contact) : CameraUiState()
    data class Error  (val throwable: Throwable) : CameraUiState()
}

class CameraViewModel(
    // use AndroidOcrService() here
    private val ocrService: OcrService = AndroidOcrService()
) : ViewModel() {

    private val _uiState = MutableStateFlow<CameraUiState>(CameraUiState.Idle)
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    fun onImageCaptured(bitmap: Bitmap?) {
        if (bitmap == null) {
            _uiState.value = CameraUiState.Idle
            return
        }
        _uiState.value = CameraUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bytes = ByteArrayOutputStream().use { st ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, st)
                    st.toByteArray()
                }
                val contact = ocrService.parseContact(bytes)
                _uiState.value = CameraUiState.Success(contact)
            } catch (t: Throwable) {
                _uiState.value = CameraUiState.Error(t)
            }
        }
    }

    fun reset() {
        _uiState.value = CameraUiState.Idle
    }
}
