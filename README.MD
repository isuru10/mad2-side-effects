# Step 1. `LaunchedEffect` Demo

1.  Create the **`LaunchedEffectDemo`** Composable accepting a `userId: Int`.
2.  Define a mutable state **`dataState`** initialized to "Fetching user data...".
3.  Implement a **`LaunchedEffect`** block, setting its key to **`userId`**. Inside the block:
    * Set `dataState` to "Loading user \$userId...".
    * Call **`delay(2000)`** to simulate network latency.
    * Set `dataState` to "Data for User \$userId successfully loaded."
4.  Define the Column layout to display the current user ID and the `dataState` text, using conditional color based on the message content.
5.  Uncomment the relevant section in `SideEffectsDemoApp` to test the functionality

# Step 2. `rememberCoroutineScope` Demo

1.  Create the **`CoroutineScopeDemo`** Composable.
2.  Get a coroutine scope using **`val scope = rememberCoroutineScope()`**.
3.  Define two mutable states: **`statusMessage`** ("Ready to process...") and **`isLoading`** (`false`).
4.  Implement a Column containing a **`Button`**.
5.  In the button's `onClick` lambda:
    * Check if `!isLoading`.
    * Launch a coroutine using **`scope.launch { ... }`**.
    * Inside the launch block, update `isLoading` to `true`, update `statusMessage`, call **`delay(3000)`**, and then set `statusMessage` and `isLoading` back to their completed/false states.
6.  Add a **`LinearProgressIndicator`** to the Column, visible only when `isLoading` is `true`.
7.  Uncomment the relevant section in `SideEffectsDemoApp` to test the functionality

# Step 3. `DisposableEffect` Demo

1.  Create the **`ListenerComposable`** accepting a `key: Boolean`.
2.  Define a mutable state **`statusText`**.
3.  Implement a **`DisposableEffect`** block, setting its key to **`key`**.
4.  In the main body (the init block):
    * Update `statusText` to reflect the "registered" state.
    * Add a `println` statement for debug.
5.  Define the **`onDispose { ... }`** block:
    * Update `statusText` to reflect the "unREGISTERED" state (the cleanup).
    * Add a `println` statement for debug.
6.  Wrap the text components in a **`Card`** with custom colors and padding.
7.  Uncomment the relevant section in `SideEffectsDemoApp` to test the functionality