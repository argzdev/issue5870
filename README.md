# GitHub link
- https://github.com/firebase/firebase-android-sdk/issues/5870
- 
# Summary
- RTDB retrieving data no longer works when reconnecting to Firebase Emulator.

# Tests
### Scenario A
- Connects to Firebase Emulator
- Retrieves data [SUCCESSFUL]

### Scenario B
- Connects to Firebase Emulator
- Turn RTDB OFF
- Turn RTDB ON
- Retrieves data [FAILS]

### How to use
1. Setup and run [Firebase emulators]([https://firebase.google.com/docs/emulator-suite/install_and_configure)
 ```
   firebase init emulators
   firebase emulators:start
```
2. Run issue570 app
3. Click first button.
- Log should appear that data even if it's null e.g. `Got value null`
4. Click second button.
- Returns error cannot reconnect. `com.google.firebase.database.tubesock.WebSocketException`
