// File: androidApp/src/main/java/com/dexmatic/android/ui/list/ContactListScreen.kt

package com.dexmatic.android.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dexmatic.shared.Contact

@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onAddNew: () -> Unit,
    onClick:   (Contact) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNew) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) { contact ->
                ContactRow(contact = contact, onClick = onClick)
            }
        }
    }
}

@Composable
private fun ContactRow(contact: Contact, onClick: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(contact) }
            .padding(16.dp)
    ) {
        Column {
            Text(contact.name, style = MaterialTheme.typography.h6)

            // only show phone if non-blank
            contact.phone
                ?.takeIf { it.isNotBlank() }
                ?.let { phone ->
                    Spacer(Modifier.height(4.dp))
                    Text(phone, style = MaterialTheme.typography.body2)
                }

            // only show email if non-blank
            contact.email
                ?.takeIf { it.isNotBlank() }
                ?.let { email ->
                    Spacer(Modifier.height(4.dp))
                    Text(email, style = MaterialTheme.typography.body2)
                }
        }
    }
}
