package com.art241111.kconnectscreen.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kconnectscreen.R
import com.art241111.kconnectscreen.ui.theme.TextHeader
import com.art241111.kconnectscreen.ui.theme.verticalGradientBackground
import com.github.art241111.tcpClient.connection.Status

@Composable
fun KConnectScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onConnect: (String) -> Unit,
    connectStatus: State<Status>,
    onIpChange: (String) -> Unit,
    defaultIP: String = "192.168.31.63"
) {
    val imageVm = viewModel<ImageViewModel>()
    imageVm.startChangeImage(LocalContext.current)

    val image: ImageBitmap? by imageVm.promoImage.observeAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalGradientBackground()
    ) {
        TextHeader(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            text = R.string.connection
        )

        PromoImage(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 20.dp),
            image = image
        )

        ConnectBottomSheet(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            connectStatus = connectStatus.value,
            onConnect = { ip ->
                onConnect(ip)
                imageVm.stopChangeImage()
            },
            onCancel = {
                onBack()
                imageVm.stopChangeImage()
            },
            onIpChange = onIpChange,
            defaultIP = defaultIP,
        )
    }
}

@Composable
private fun PromoImage(
    modifier: Modifier = Modifier,
    image: ImageBitmap?
) {
    image?.let {
        Image(
            modifier = modifier,
            bitmap = it,
            contentScale = ContentScale.FillHeight,
            contentDescription = stringResource(id = R.string.robowizard)
        )
    }
}

@Composable
private fun ConnectBottomSheet(
    modifier: Modifier = Modifier,
    connectStatus: Status,
    onConnect: (String) -> Unit,
    onIpChange: (String) -> Unit,
    onCancel: () -> Unit,
    defaultIP: String = "192.168.31.63",
) {
    val isConnectReady =
        connectStatus == Status.DISCONNECTED || connectStatus == Status.ERROR

    var isFirstError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val errorText = stringResource(R.string.connect_error)
    if (connectStatus == Status.COMPLETED) {
        onCancel()
    } else if (connectStatus == Status.ERROR && isFirstError) {
        isFirstError = false
        Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
    }

    Surface(
        shape = RoundedCornerShape(topStartPercent = 20, topEndPercent = 20),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            val focusManager = LocalFocusManager.current
            var textIP by remember { mutableStateOf(defaultIP) }
            OutlinedTextField(
                modifier = Modifier.wrapContentHeight(),
                value = textIP,
                onValueChange = { textIP = it },
                label = { Text(text = stringResource(id = R.string.robot_ip)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (textIP != defaultIP)
                            onIpChange(textIP)

                        onConnect(textIP)
                        isFirstError = true
                        this.defaultKeyboardAction(ImeAction.Done)
                        focusManager.clearFocus()
                    }
                ),
                enabled = isConnectReady
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.wrapContentHeight(),
                onClick = {
                    if (textIP != defaultIP)
                        onIpChange(textIP)

                    onConnect(textIP)
                    isFirstError = true
                },
                enabled = isConnectReady
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.connect),
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                modifier = Modifier.wrapContentHeight(),
                onClick = { onCancel() },
                enabled = isConnectReady
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.cancel),
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
