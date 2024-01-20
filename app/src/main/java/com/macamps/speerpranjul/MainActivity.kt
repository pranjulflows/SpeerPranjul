package com.macamps.speerpranjul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.macamps.speerpranjul.ui.composables.ProfileTile
import com.macamps.speerpranjul.ui.composables.SearchBar
import com.macamps.speerpranjul.ui.theme.Black
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.ui.theme.SnowWhite
import com.macamps.speerpranjul.ui.theme.SpeerPranjulTheme
import com.macamps.speerpranjul.utils.useDebounce
import com.macamps.speerpranjul.viewModel.GitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
                        MainScreen()
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val gitViewModel: GitViewModel = viewModel()
    val searchResults = gitViewModel.githubUsersList.collectAsState()
    var userList = searchResults.value?.items
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
            SearchBar(onSearch = {
                if (it.isNotEmpty()) {
                    userList?.clear()
                    gitViewModel.searchGithubUsers(it)

                } else {
                    userList?.clear()
                }
            })

            LazyColumn(content = {
                items(count = userList?.size ?: 0) {
                    ProfileTile(userList?.get(it))
                }
            })
            //   ProfileTile(user)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}