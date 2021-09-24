package com.keygenqt.currying

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.keygenqt.currying.ui.theme.CurryingTheme
import kotlinx.coroutines.launch

sealed class BottomNavigationTab {
    object Person : BottomNavigationTab()
    object Phone : BottomNavigationTab()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurryingTheme {

                val scope = rememberCoroutineScope()
                val scaffoldState: ScaffoldState = rememberScaffoldState()
                var currentTab: BottomNavigationTab by remember { mutableStateOf(BottomNavigationTab.Person) }

                fun onClick(tab: BottomNavigationTab): () -> Unit = {
                    currentTab = tab
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Selected - ${currentTab::class.simpleName}")
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = currentTab == BottomNavigationTab.Person,
                                icon = { Icon(Icons.Filled.Person, null) },
                                onClick = onClick(BottomNavigationTab.Person)
                            )
                            BottomNavigationItem(
                                selected = currentTab == BottomNavigationTab.Phone,
                                icon = { Icon(Icons.Filled.Phone, null) },
                                onClick = onClick(BottomNavigationTab.Phone)
                            )
                        }
                    }
                ) {
                    when (currentTab) {
                        BottomNavigationTab.Person -> Greeting("Selected - Person")
                        BottomNavigationTab.Phone -> Greeting("Selected - Phone")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurryingTheme {
        Greeting("Android")
    }
}