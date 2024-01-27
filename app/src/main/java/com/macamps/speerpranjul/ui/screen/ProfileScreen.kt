package com.macamps.speerpranjul.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.macamps.speerpranjul.R
import com.macamps.speerpranjul.ui.composables.FollowerFollowingTile
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.viewModel.GitViewModel


@Composable
fun ProfileScreen(navHostController: NavHostController, userId: String? = "userId") {
    val viewModel = hiltViewModel<GitViewModel>()

    LaunchedEffect(key1 = userId) {
        userId?.let { viewModel.getUserWithLoginId(it) }

    }
    val userDetails = viewModel.githubUserDetails.collectAsState()
    Log.e("TAG", "ProfileScreen: $userId ")
    val profile = userDetails.value
    Scaffold(containerColor = BlackMinimal) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(profile?.avatarUrl)
                    .diskCacheKey("${profile?.avatarUrl}").crossfade(true).build(),
                contentDescription = "profile Image",
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .size(100.dp),
                placeholder = painterResource(id = R.drawable.github_placeholder),
            )
            Text(
                text = profile?.login ?: "Github User", style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    color = Color.LightGray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            FollowerFollowingTile(followersCount = profile?.followersCount ?: 0,
                followingCount = profile?.followingCount ?: 0,
                onFollowerClick = {
                    navHostController.navigate("/FollowerFollowingScreen/${profile?.login}/Follower")

                },
                onFollowingClick = {
                    navHostController.navigate("/FollowerFollowingScreen/${profile?.login}/Following")

                })


        }
    }

}