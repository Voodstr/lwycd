package ru.voodster.lwycd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.voodster.lwycd.ui.theme.LwycdTheme


val tasksFromFolder = arrayListOf(
    YouCanDo("get up", false),
    YouCanDo("brush teeth", false),
    YouCanDo("boil eggs", false),
    YouCanDo("make coffee", false),
    YouCanDo("eat breakfast", false),
    YouCanDo("dress up", false),
    YouCanDo("go to work", false),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LwycdTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //SimpleColumn(list = mutableListOf("1", "2"))
                    TaskScreen("Work", tasksFromFolder)
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
    LwycdTheme {
        Greeting("Android")
    }
}