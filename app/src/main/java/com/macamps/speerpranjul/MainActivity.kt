package com.macamps.speerpranjul

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.ui.composables.ProfileTile
import com.macamps.speerpranjul.ui.composables.SearchBar
import com.macamps.speerpranjul.ui.theme.Black
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.ui.theme.SnowWhite
import com.macamps.speerpranjul.ui.theme.SpeerPranjulTheme
import com.macamps.speerpranjul.viewModel.GitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeerPranjulTheme {
                navHostController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SpeerPranjulTheme {

                        Navigation(navHostController)
                    }
                }
            }
        }
    }

}

@Composable
fun Navigation(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "/MainScreen") {
        composable("/MainScreen") {
            MainScreen(navHostController = navHostController)
        }
        composable("/ProfileScreen") {
            ProfileScreen()
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, gitViewModel: GitViewModel = hiltViewModel()) {
    Log.i("TAG", "MainScreen: ")

    val searchResults = remember {
        Log.i("TAG", "MainScreen: remember")

        mutableStateOf<SearchResponse?>(null) }
    LaunchedEffect(key1 = gitViewModel.githubUsersList) {
        Log.i("TAG", "MainScreen: launched")

        gitViewModel.githubUsersList.collect {
            it?.let {response->
                searchResults.value =response
            }
        }
    }
    val userList = searchResults.value?.items
    val scrollState = rememberLazyListState()

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
                Log.i("TAG", "MainScreen: called Again")
                if (it.isNotEmpty()) {
                    userList?.clear()
                    gitViewModel.searchGithubUsers(it)

                }else{
                    searchResults.value?.items?.clear()
                }

            })

            LazyColumn(
                state = scrollState,
                content = {
                    items(count = userList?.size ?: 0) {

                        ProfileTile(userList?.get(it), modifier = Modifier.clickable {
                            navHostController.navigate("/ProfileScreen")
                        })
                    }
                })
            //   ProfileTile(user)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
    Scaffold(containerColor = BlackMinimal) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }

}