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

@Composable
fun LaunchedEffectDemo(userId: Int) {
    // State to hold the fetched data
    var dataState by remember { mutableStateOf("Fetching user data...") }

    // When the key (userId) changes, the block below runs, and the previous run is cancelled.
    LaunchedEffect(key1 = userId) {
        // This is a suspend function block, safe for API calls
        dataState = "Loading user $userId..."
        delay(2000) // Simulate a network delay
        dataState = "Data for User $userId successfully loaded."
    }

    Column(Modifier.padding(16.dp)) {
        Text("Current User ID: $userId", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(dataState, color = if (dataState.contains("successfully")) Color.Green.copy(0.7f) else Color.Red.copy(0.7f))
    }
}

@Composable
fun CoroutineScopeDemo() {
    // 1. Get a CoroutineScope tied to this composable's lifecycle
    val scope = rememberCoroutineScope()

    var statusMessage by remember { mutableStateOf("Ready to process...") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (!isLoading) {
                    // 2. Launch the long-running task inside the scope
                    scope.launch {
                        isLoading = true
                        statusMessage = "Processing started..."
                        delay(3000) // Simulate heavy, asynchronous work
                        statusMessage = "Processing complete!"
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
        ) {
            Text(if (isLoading) "Working..." else "Start Heavy Processing", color = Color.White)
        }
        Spacer(Modifier.height(16.dp))
        if (isLoading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        Text(statusMessage, fontSize = 14.sp)
    }
}

@Composable
fun ListenerComposable(key: Boolean) {
    // Simple state to show if the component is active
    var statusText by remember { mutableStateOf("Listener waiting to be registered...") }

    // Use a key to show how the effect is disposed and re-initialized when the key changes.
    DisposableEffect(key1 = key) {
        // INIT BLOCK (Runs on composition or key change)
        statusText = "Listener registered: key=$key. Watching for external events..."
        println("DEBUG: Listener Registered for key $key")

        // DISPOSE BLOCK (Runs when the composable leaves the screen OR when the key changes)
        onDispose {
            statusText = "Listener unREGISTERED: key=$key. Cleanup complete."
            println("DEBUG: Listener Unregistered for key $key")
        }
    }

    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F0FE))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("DisposableEffect Demo", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(statusText, fontSize = 14.sp)
            Text("Try toggling the key in the main app to see cleanup happen.", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

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
                    // LaunchedEffect Demo Controls
                    Text("1. LaunchedEffect & Lifecycle", style = MaterialTheme.typography.titleMedium)
                    LaunchedEffectDemo(currentUserId)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = { currentUserId = 202 }) { Text("Load User 202") }
                        Button(onClick = { currentUserId = 303 }) { Text("Load User 303") }
                    }
                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

                    // CoroutineScope Demo Controls
                    Text("2. rememberCoroutineScope (User Events)", style = MaterialTheme.typography.titleMedium)
                    CoroutineScopeDemo()
                    HorizontalDivider(Modifier.padding(vertical = 12.dp))

                    // DisposableEffect Demo Controls
                    Text("3. DisposableEffect (Cleanup)", style = MaterialTheme.typography.titleMedium)
                    ListenerComposable(isListenerActive)
                    Button(onClick = { isListenerActive = !isListenerActive }) {
                        Text(if (isListenerActive) "Deactivate Listener (Key Change)" else "Activate Listener")
                    }
                }
            }
        }
    }
}