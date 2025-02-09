package com.example.remind.feature.screens.patience

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.remind.app.Screens
import com.example.remind.core.common.navigation.NavItem
import com.example.remind.core.common.navigation.listOfNavItem
import com.example.remind.core.designsystem.theme.RemindTheme
import com.example.remind.core.util.currentRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatienceScreen() {
    val navController: NavHostController = rememberNavController()
    val currentRoute = navController.currentRoute()
    val visibleBottomnev = listOf(
        Screens.Patience.Home.route,
        Screens.Patience.MoodChart.route,
        Screens.Patience.Medicine.route,
        Screens.Patience.MyPage.route
    )
    Scaffold(
        bottomBar = {
            if(currentRoute in visibleBottomnev) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        PatienceGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.White,
        modifier = Modifier.height(66.dp)
    ) {
        listOfNavItem.forEachIndexed { index, navItem: NavItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon (
                        painter = painterResource(id = navItem.icon_unfill),
                        contentDescription = null,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .padding(bottom = 4.dp),
                        tint = if(selectedIndex == index) {
                            RemindTheme.colors.main_6
                        } else {
                            RemindTheme.colors.slate_300
                        },
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontSize = 10.sp,
                        color = if(selectedIndex == index) {
                            RemindTheme.colors.main_6
                        } else {
                            RemindTheme.colors.slate_300
                        }
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}