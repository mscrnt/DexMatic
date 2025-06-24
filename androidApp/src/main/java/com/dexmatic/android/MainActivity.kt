// File: androidApp/src/main/java/com/dexmatic/android/MainActivity.kt

package com.dexmatic.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dexmatic.android.ui.navigation.NavGraph
import com.dexmatic.android.ui.theme.DexMaticTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DexMaticTheme {
                DexMaticApp()
            }
        }
    }
}

@Composable
fun DexMaticApp() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("DexMatic") }) }
    ) { innerPadding ->
        NavGraph(modifier = Modifier.padding(innerPadding))
    }
}
