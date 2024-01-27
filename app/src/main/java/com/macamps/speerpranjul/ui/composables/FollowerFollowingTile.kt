package com.macamps.speerpranjul.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FollowerFollowingTile(
    followersCount: Long,
    followingCount: Long,
    onFollowerClick: () -> Unit,
    onFollowingClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onFollowerClick() }
        ) {
            Text(
                text = "Followers", style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    color = Color.LightGray,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "$followersCount", style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Monospace,

                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onFollowingClick() }
        ) {
            Text(
                text = "Following", style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "$followingCount", style = TextStyle(
                    color = Color.LightGray,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}