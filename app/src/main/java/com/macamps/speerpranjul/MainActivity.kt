package com.macamps.speerpranjul

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.network.Api
import com.macamps.speerpranjul.ui.composables.ProfileTile
import com.macamps.speerpranjul.ui.theme.Black
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.ui.theme.RustyWhite
import com.macamps.speerpranjul.ui.theme.SnowWhite
import com.macamps.speerpranjul.ui.theme.SpeerPranjulTheme
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeerPranjulTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SpeerPranjulTheme {
                        Greeting()
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val result = Api.get()

            val user2 = decodeFromString<User>(result.body())
            Log.e("TAG", "onCreate:NAME ${user2.name}")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(user: User? = null) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(containerColor = Black, topBar = {
        TopAppBar(
            title = {
                Text(text = "GitRadar", color = SnowWhite)
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = BlackMinimal,
            )
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(

                    modifier = Modifier.padding(
                        start = 16.dp, top = 20.dp, bottom = 20.dp, end = 0.dp
                    )

                ) {
                    TextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(.85F)
                            .clip(CircleShape),
                        placeholder = {
                            Text(text = "Search")
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedPlaceholderColor = RustyWhite,
                            focusedPlaceholderColor = BlackMinimal,
                            focusedContainerColor = SnowWhite,
                            unfocusedContainerColor = BlackMinimal,
                            cursorColor = Color(0XFF070E14),
                            focusedIndicatorColor = Color.Transparent,
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search, contentDescription = ""
                            )
                        },

                        )
                }
                IconButton(
                    onClick = { }, modifier = Modifier.padding(end = 5.dp)
                ) {
                    Icon(
                        tint = SnowWhite,
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Button",
                        modifier = Modifier.size(40.dp)
                    )
                }

            }
            ProfileTile(user)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}