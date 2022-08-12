package ru.voodster.lwycd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import ru.voodster.lwycd.composable.CheckListContent
import ru.voodster.lwycd.entities.initialChecklistFolderState
import ru.voodster.lwycd.ui.theme.LwycdTheme


class MainActivity : ComponentActivity() {
    private val taskViewModel by viewModels<ChecklistViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
                LwycdTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize().systemBarsPadding(),
                        color = MaterialTheme.colors.background
                    ) {
                        CheckListContent(
                            Modifier.navigationBarsPadding().fillMaxSize(),
                            initialChecklistFolderState
                        )
                    }
                }

        }
    }
}