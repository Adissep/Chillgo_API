package com.capstone.chillgoapp.screens

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.ButtonComponent
import com.capstone.chillgoapp.components.DialogUploadDocument
import com.capstone.chillgoapp.components.MyTextFieldComponent
import com.capstone.chillgoapp.data.umkm.UmkmViewModel
import com.capstone.chillgoapp.ui.common.FileUtility
import com.capstone.chillgoapp.ui.common.FileUtility.Companion.getBitmap
import com.capstone.chillgoapp.ui.theme.PrimaryBody
import com.capstone.chillgoapp.ui.theme.PrimaryMain

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UmkmFormScreen(
    onBackPressed: () -> Unit = {},
    viewModel: UmkmViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    var umkmName by viewModel.umkmName
    var category by viewModel.category
    var umkmAddress by viewModel.umkmAddress
    var umkmRating by viewModel.umkmRating
    var callNumber by viewModel.callNumber
    var productPrice by viewModel.productPrice
    var scheduleOperational by viewModel.scheduleOperational
    var productDescription by viewModel.productDescription
    var file by viewModel.file
    var mediaType by viewModel.mediaType

    var bitmapFile by remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val launchGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        try {
            uri?.let {
                mediaType = context.contentResolver.getType(it).toString()
            }
            bitmapFile = uri?.getBitmap(context.contentResolver)
            bitmapFile?.let {
                file = FileUtility.bitmapToFile(context, it, "${System.currentTimeMillis()}")
            }
        } catch (e: Exception) {
            Log.e("FileManager", e.message ?: "")
        }
    }

    val launchCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmapFile = bitmap
        try {
            bitmap?.let {
                file = FileUtility.bitmapToFile(context, it, "${System.currentTimeMillis()}")
            }
            mediaType = "image/png"
        } catch (e: Exception) {
            Log.e("FileManager", e.message ?: "")
        }

    }

    DialogUploadDocument(
        openCamera = {
            launchCamera.launch(null)
        },
        openGallery = {
            launchGallery.launch("image/*")
        },
        onCancel = {
            showDialog = false
        },
        show = showDialog,
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryBody)
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryBody)
                    .verticalScroll(rememberScrollState())
            ) {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PrimaryBody),
                            text = "MSME Submission Form",
                            color = PrimaryMain,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W700,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBody),
                    navigationIcon = {
                        Icon(
                            modifier = Modifier
                                .background(PrimaryBody)
                                .clickable { onBackPressed() },
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryMain
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .border(1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            showDialog = true
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (bitmapFile == null) {
                        Text(text = "Upload image here")
                    } else {
                        Image(
                            rememberAsyncImagePainter(model = bitmapFile),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.name_msme),
                    onTextChanged = {
                        umkmName = it
                    },
                    errorStatus = umkmName.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.product_price),
                    onTextChanged = {
                        productPrice = it
                    },
                    errorStatus = productPrice.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.address),
                    onTextChanged = {
                        umkmAddress = it
                    },
                    errorStatus = umkmAddress.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.ratings),
                    onTextChanged = {
                        umkmRating = it
                    },
                    errorStatus = umkmRating.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.description),
                    onTextChanged = {
                        productDescription = it
                    },
                    errorStatus = productDescription.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.category),
                    onTextChanged = {
                        category = it
                    },
                    errorStatus = category.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.number_phone),
                    onTextChanged = {
                        callNumber = it
                    },
                    errorStatus = callNumber.isNotBlank()
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.schedule_ops),
                    onTextChanged = {
                        scheduleOperational = it
                    },
                    errorStatus = scheduleOperational.isNotBlank()
                )
                Spacer(modifier = Modifier.height(22.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.submit),
                    onButtonClicked = {
                        viewModel.saveUmkm(context)
                    },
                    isEnabled = true
                )
            }
        }
    }
}