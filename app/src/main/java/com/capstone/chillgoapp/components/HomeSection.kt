package com.capstone.chillgoapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@Preview
@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    title: String = "Title",
    showLocation: Boolean = false,
    cityOnly: Boolean = false,
    onTextSelected: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionText(modifier.weight(1f), title)

            if (!cityOnly) Text(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onTextSelected() },
                text = stringResource(id = R.string.more),
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                color = PrimaryMain
            )

            /*if (!showLocation) Text(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onTextSelected() },
                text = stringResource(id = R.string.more),
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                color = PrimaryMain
            )*/
        }

        if (showLocation) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = PrimaryMain
                )
                Spacer(modifier = Modifier.width(8.dp))
                NormalTextComponent(
                    modifier = Modifier.weight(1f),
                    value = stringResource(id = R.string.location),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier.clickable { onTextSelected() },
                    text = stringResource(id = R.string.more),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W600,
                    color = PrimaryMain
                )
            }
        }

        if (cityOnly) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                //hidden more in by city
            }
        }
        content()
    }

}