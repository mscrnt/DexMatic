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
import com.dexmatic.android.ui.scan.ScanCardScreen
import com.dexmatic.shared.Contact

object Routes {
    const val Scan   = "scan"
    const val Review = "review"
    const val List   = "list"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier        = modifier,
        navController   = navController,
        startDestination = Routes.Scan
    ) {
        composable(Routes.Scan) {
            ScanCardScreen(
                onScanComplete = { contact: Contact ->
                    // stash parsedContact and navigate
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("parsedContact", contact)
                    navController.navigate(Routes.Review)
                }
            )
        }
        composable(Routes.Review) {
            val parsed: Contact? = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get("parsedContact")
            parsed?.let { contact ->
                ReviewCardScreen(
                    contact  = contact,
                    onSave   = { updated ->
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("savedContact", updated)
                        navController.navigate(Routes.List)
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
        composable(Routes.List) {
            val saved: Contact? = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get("savedContact")
            ContactListScreen(
                contacts = saved?.let { listOf(it) } ?: emptyList(),
                onAddNew = { navController.navigate(Routes.Scan) },
                onClick   = { /* TODO: go to detail/edit */ }
            )
        }
    }
}
