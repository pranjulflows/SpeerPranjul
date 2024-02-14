package com.macamps.speerpranjul

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.macamps.speerpranjul.model.SearchResponse
import com.macamps.speerpranjul.ui.composables.ProfileTile
import com.macamps.speerpranjul.ui.composables.SearchBar
import com.macamps.speerpranjul.ui.screen.ProfileScreen
import com.macamps.speerpranjul.ui.screen.UserFollowerFollowing
import com.macamps.speerpranjul.ui.theme.Black
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.ui.theme.SnowWhite
import com.macamps.speerpranjul.ui.theme.SpeerPranjulTheme
import com.macamps.speerpranjul.utils.noRippleClickable
import com.macamps.speerpranjul.viewModel.GitViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

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
    val gitViewModel: GitViewModel = hiltViewModel()
    NavHost(navController = navHostController, startDestination = "/MainScreen") {
        composable("/MainScreen") {
            MainScreen(navHostController = navHostController, gitViewModel = gitViewModel)
        }
        composable(
            "/ProfileScreen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfileScreen(navHostController, backStackEntry.arguments?.getString("userId"))
        }
        composable(
            "/FollowerFollowingScreen/{userId}/{navType}",
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("navType") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            UserFollowerFollowing(
                navHostController,
                backStackEntry.arguments?.getString("userId"),
                backStackEntry.arguments?.getString("navType"),
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(navHostController: NavHostController, gitViewModel: GitViewModel) {
    Log.i("TAG", "MainScreen: ")

    val searchResults = remember {
        Log.i("TAG", "MainScreen: remember")
        mutableStateOf<SearchResponse?>(null)
    }
    val isLoading = gitViewModel.isLoading.collectAsStateWithLifecycle()
//    val searchResults = gitViewModel.githubUsersList.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {

        gitViewModel.githubUsersList.collect {
            it?.let { response ->
                searchResults.value = response
            }
        }
    }


    val userList = searchResults.value?.items
    val scrollState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(modifier = Modifier.noRippleClickable { keyboardController?.hide() },
        containerColor = Black,
        topBar = {
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
                } else {
                    searchResults.value = null
                }

            })

            AnimatedVisibility(
                visible = isLoading.value, modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(100.dp))

                }

            }

            LazyColumn(state = scrollState, content = {
                items(count = userList?.size ?: 0) {

                    ProfileTile(userList?.get(it), modifier = Modifier.clickable {
                        keyboardController?.hide()
                        navHostController.navigate("/ProfileScreen/${userList?.get(it)?.login}")
                    })
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}

