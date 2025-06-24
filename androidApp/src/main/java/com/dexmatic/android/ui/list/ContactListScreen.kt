// File: androidApp/src/main/java/com/dexmatic/android/ui/list/ContactListScreen.kt

package com.dexmatic.android.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dexmatic.shared.Contact

@Composable
fun ContactListScreen(
    contacts: List<Contact> = emptyList(),
    onAddNew: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Contacts") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNew) {
                Icon(Icons.Default.Add, contentDescription = "Add New Contact")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (contacts.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No contacts yet", style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = onAddNew) {
                        Text("Scan a Card")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(contacts) { contact ->
                        ContactListItem(contact)
                    }
                }
            }
        }
    }
}

@Composable
private fun ContactListItem(contact: Contact) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: navigate to detail/edit screen */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(contact.name, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(4.dp))
            Text(contact.phone.orEmpty(), style = MaterialTheme.typography.body2)
            Text(contact.email.orEmpty(), style = MaterialTheme.typography.body2)
        }
    }
}
