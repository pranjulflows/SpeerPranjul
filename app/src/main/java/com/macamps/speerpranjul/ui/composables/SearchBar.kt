package com.macamps.speerpranjul.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.macamps.speerpranjul.ui.theme.BlackMinimal
import com.macamps.speerpranjul.ui.theme.RustyWhite
import com.macamps.speerpranjul.ui.theme.SnowWhite
import com.macamps.speerpranjul.utils.useDebounce

@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit, hint: String = "Search",) {
    var searchText by rememberSaveable { mutableStateOf("") }


        searchText.useDebounce { onSearch(it) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(

            modifier = Modifier.padding(
                start = 16.dp, top = 20.dp, bottom = 20.dp, end = 16.dp
            )

        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth(1F)
                    .clip(CircleShape),
                placeholder = {
                    Text(text = hint)
                },

                colors = TextFieldDefaults.colors(
                    focusedTextColor = BlackMinimal,
                    unfocusedTextColor = RustyWhite,
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
//        IconButton(
//            onClick = { }, modifier = Modifier.padding(end = 5.dp)
//        ) {
//            Icon(
//                tint = SnowWhite,
//                imageVector = Icons.Filled.Search,
//                contentDescription = "Search Button",
//                modifier = Modifier.size(40.dp)
//            )
//        }

    }
}
