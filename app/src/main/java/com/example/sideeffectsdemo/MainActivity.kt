package com.example.sideeffectsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sideeffectsdemo.ui.theme.SideEffectsDemoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SideEffectsDemoTheme {
                SideEffectsDemoApp()
            }
        }
    }
}



// Refer to the README.MD for instructions
@Composable
fun SideEffectsDemoApp() {
    var currentUserId by remember { mutableStateOf(101) }
    var isListenerActive by remember { mutableStateOf(true) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF0F4F8)).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Side Effects Demo", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))

            // -- Controls for Side Effects --
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
//                    // LaunchedEffect Demo Controls
//                    Text("1. LaunchedEffect & Lifecycle", style = MaterialTheme.typography.titleMedium)
//                    LaunchedEffectDemo(currentUserId)
//                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//                        Button(onClick = { currentUserId = 202 }) { Text("Load User 202") }
//                        Button(onClick = { currentUserId = 303 }) { Text("Load User 303") }
//                    }
//                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

//                    // CoroutineScope Demo Controls
//                    Text("2. rememberCoroutineScope (User Events)", style = MaterialTheme.typography.titleMedium)
//                    CoroutineScopeDemo()
//                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

//                    // DisposableEffect Demo Controls
//                    Text("3. DisposableEffect (Cleanup)", style = MaterialTheme.typography.titleMedium)
//                    ListenerComposable(isListenerActive)
//                    Button(onClick = { isListenerActive = !isListenerActive }) {
//                        Text(if (isListenerActive) "Deactivate Listener (Key Change)" else "Activate Listener")
//                    }
                }
            }
        }
    }
}