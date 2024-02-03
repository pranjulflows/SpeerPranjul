package com.macamps.speerpranjul.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.ui.composables.ProfileTile
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.ui.theme.RustyWhite
import com.macamps.speerpranjul.utils.noRippleClickable
import com.macamps.speerpranjul.viewModel.GitViewModel

@Composable
fun UserFollowerFollowing(
    navHostController: NavHostController,
    userId: String?,
    type: String?,
    gitViewModel: GitViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        gitViewModel.getUsersFollowerFollowers(userId ?: "Github User","$type")
    }


    val userState = gitViewModel.followerFollowingUsers.collectAsState()
    val user = userState.value

    val scrollState = rememberLazyListState()
    Scaffold(containerColor = BlackMinimal) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${userId}/${type?.lowercase()}",
                fontFamily = FontFamily.Monospace,
                style = TextStyle(color = RustyWhite)
            )
            Divider()
            LazyColumn(state = scrollState, modifier = Modifier.fillMaxWidth()) {
                items(
                    count = user?.size ?: 0
                ) { ProfileTile(profile = user!![it], modifier = Modifier.noRippleClickable {
                    navHostController.navigate("/ProfileScreen/${user[it].login}")

                }) }
            }
        }
    }
}