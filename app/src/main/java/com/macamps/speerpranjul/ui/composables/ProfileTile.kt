package com.macamps.speerpranjul.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.macamps.speerpranjul.R
import com.macamps.speerpranjul.model.User
import com.macamps.speerpranjul.ui.theme.RustyWhite

@Composable
fun ProfileTile(profile: User?) {
    val localImagePainterUrl = remember { mutableStateOf(profile?.avatarUrl) }

    Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(localImagePainterUrl.value)
                    .crossfade(true)
                    .build(),
                contentDescription = "profile Image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp),
                placeholder = painterResource(id = R.drawable.github_placeholder),
            )
            Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)) {
                Text(text =profile?.login?:"", style = TextStyle(color = RustyWhite))
            }
        }
    }
}