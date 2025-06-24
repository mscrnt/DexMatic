// File: androidApp/src/main/java/com/dexmatic/android/ui/viewmodel/CameraViewModel.kt

package com.dexmatic.android.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dexmatic.shared.Contact
import com.dexmatic.shared.OcrService
import com.dexmatic.shared.FakeOcrService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

/**
 * Represents the various UI states for the camera/OCR flow.
 */
sealed class CameraUiState {
    /** Initial or reset state. */
    object Idle : CameraUiState()
    /** OCR is in progress. */
    object Loading : CameraUiState()
    /** OCR succeeded with a Contact. */
    data class Success(val contact: Contact) : CameraUiState()
    /** OCR failed with an error. */
    data class Error(val throwable: Throwable) : CameraUiState()
}

/**
 * ViewModel to coordinate camera capture + OCR parsing.
 */
class CameraViewModel(
    private val ocrService: OcrService = FakeOcrService()
) : ViewModel() {

    private val _uiState = MutableStateFlow<CameraUiState>(CameraUiState.Idle)
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    /**
     * Call this when the camera returns a Bitmap (or null on cancel).
     */
    fun onImageCaptured(bitmap: Bitmap?) {
        if (bitmap == null) {
            _uiState.value = CameraUiState.Idle
            return
        }

        // Kick off OCR in IO dispatcher
        _uiState.value = CameraUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bytes = ByteArrayOutputStream().use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.toByteArray()
                }
                val contact = ocrService.parseContact(bytes)
                _uiState.value = CameraUiState.Success(contact)
            } catch (t: Throwable) {
                _uiState.value = CameraUiState.Error(t)
            }
        }
    }

    /**
     * Reset back to the Idle state (e.g. to re-scan another card).
     */
    fun reset() {
        _uiState.value = CameraUiState.Idle
    }
}
