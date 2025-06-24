// File: androidApp/src/main/java/com/dexmatic/android/ui/review/ReviewCardScreen.kt

package com.dexmatic.android.ui.review

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dexmatic.shared.Contact

@Composable
fun ReviewCardScreen(
    initialContact: Contact = Contact(name = "", phone = null, email = null),
    onSave: (Contact) -> Unit
) {
    // Local editable state
    var name by remember { mutableStateOf(initialContact.name) }
    var phone by remember { mutableStateOf(initialContact.phone.orEmpty()) }
    var email by remember { mutableStateOf(initialContact.email.orEmpty()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Review Contact") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { 
                    onSave(Contact(name = name, phone = phone.ifBlank { null }, email = email.ifBlank { null })) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Save Contact")
            }
        }
    }
}
