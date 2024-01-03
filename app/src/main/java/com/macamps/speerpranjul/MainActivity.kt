package com.macamps.speerpranjul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.macamps.speerpranjul.ui.theme.Black
import com.macamps.speerpranjul.ui.theme.SnowWhite
import com.macamps.speerpranjul.ui.theme.SpeerPranjulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeerPranjulTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    val search = TextFieldValue()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "GitRadar", color = SnowWhite)
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Black,
            )
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement= Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                TextField(value = search, onValueChange = {

                },modifier = Modifier.fillMaxWidth(.85F))
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Search,
                        contentDescription = "Search Button",
                        modifier = Modifier.fillMaxWidth(1F))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpeerPranjulTheme {
        Greeting()
    }
}