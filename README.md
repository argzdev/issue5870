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
