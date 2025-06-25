// File: androidApp/src/main/java/com/dexmatic/android/ui/navigation/NavGraph.kt

package com.dexmatic.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dexmatic.android.ui.list.ContactListScreen
import com.dexmatic.android.ui.review.ReviewCardScreen
import com.dexmatic.android.ui.scan.DocumentScannerScreen
import com.dexmatic.shared.Contact

object Routes {
    const val DocScan = "doc_scan"
    const val Review  = "review"
    const val List    = "list"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.DocScan
    ) {
        // 1️⃣ Document Scanner entry point
        composable(Routes.DocScan) {
            DocumentScannerScreen { imageUris ->
                // Convert the first image URI to a fake "Contact" placeholder
                val contact = Contact(
                    name = imageUris.firstOrNull() ?: "Unknown Document",
                    phone = null,
                    email = null
                )

                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("parsedContact", contact)

                navController.navigate(Routes.Review)
            }
        }

        // 2️⃣ Review Screen
        composable(Routes.Review) {
            val parsed = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Contact>("parsedContact")

            parsed?.let { contact ->
                ReviewCardScreen(
                    contact = contact,
                    onSave = { updated ->
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("savedContact", updated)
                        navController.navigate(Routes.List)
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }

        // 3️⃣ Contact List Screen
        composable(Routes.List) {
            val saved = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Contact>("savedContact")

            ContactListScreen(
                contacts = saved?.let { listOf(it) } ?: emptyList(),
                onAddNew = { navController.navigate(Routes.DocScan) },
                onClick  = { /* Optional: open full contact detail */ }
            )
        }
    }
}
