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

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "scan"
    ) {
        composable("scan") {
            ScanCardScreen(
                onScanComplete = { contact ->
                    // TODO: pass `contact` into review screen via savedStateHandle or args
                    navController.navigate("review")
                }
            )
        }
        composable("review") {
            ReviewCardScreen(
                onSave = {
                    navController.navigate("list")
                }
            )
        }
        composable("list") {
            ContactListScreen(
                onAddNew = {
                    navController.navigate("scan")
                }
            )
        }
    }
}
