package com.example.quizmarket.ui.main.navigation

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.quizmarket.ui.main.MainActivity
import com.example.quizmarket.ui.myQuizzes.MyQuizzesActivity
import com.example.quizmarket.ui.settings.SettingsActivity
import com.example.quizmarket.ui.theme.Pinklight

@Composable
fun Drawer(
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val items = listOf(
        NavigationItem.MyQuiz,
        NavigationItem.Settings,
        NavigationItem.LogOut
    )
    val activity = LocalContext.current as MainActivity

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Pinklight),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Column {
                        IconButton(onClick = { /*TODO*/ }) {

                        }
                    }
                }
                items[0].let { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            Intent(
                                activity,
                                MyQuizzesActivity::class.java
                            ).also { intent ->
                                activity.startActivity(intent)
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
                items[1].let { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            Intent(
                                activity,
                                SettingsActivity::class.java
                            ).also { intent ->
                                activity.startActivity(intent)
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
                items[2].let { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.iconId),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            activity.getSharedPreferences("accessToken", ComponentActivity.MODE_PRIVATE).edit()
                                .putString("accessToken", "").apply()
                            activity.finish()
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        },
        content = {
            content()
        }
    )
}