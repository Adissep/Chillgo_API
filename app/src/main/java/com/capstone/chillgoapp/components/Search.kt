package com.capstone.chillgoapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.ui.theme.ChillGoAppTheme
import com.capstone.chillgoapp.ui.theme.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    onNavigateToMore: () -> Unit
) {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        shape = MaterialTheme.shapes.large,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable { onNavigateToMore() }
    ) {

    }
}

@Composable
fun buttonSearch(
    modifier: Modifier = Modifier,
    onNavigateToMore: () -> Unit
) {
    Button(
        onClick = { onNavigateToMore() },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Gray,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        /*colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray
        ),*/
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 59.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "button to next search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.placeholder_search),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    ChillGoAppTheme {
        Search(
            onNavigateToMore = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    ChillGoAppTheme {
        buttonSearch(
            onNavigateToMore = {}
        )
    }
}