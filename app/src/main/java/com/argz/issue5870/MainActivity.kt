package com.argz.issue5870

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.argz.issue5870.ui.theme.Issue5870Theme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Issue5870Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold {
                        Column(
                            modifier = Modifier.padding(it)
                        ) {
                            Greeting()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var db: FirebaseDatabase? = null
    LaunchedEffect(key1 = Unit) {
        db = Firebase.database.apply {
            useEmulator("10.0.2.2", 9000)
            setLogLevel(Logger.Level.DEBUG)

            val connectedRef = getReference(".info/connected")
            connectedRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val connected = snapshot.getValue(Boolean::class.java) ?: false
                    if (connected) {
                        Log.d(TAG, "was connected")
                    } else {
                        Log.d(TAG, "was not connected")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Listener was cancelled")
                }
            })
        }
    }
    Button(onClick = {
        testScenarioA(db!!)
    }) {
        Text("Get Data [SUCCEEDS]")
    }

    Button(onClick = {
        testScenarioB(db!!)
    }) {
        Text("Turn db on/off then get data [FAILS]")
    }
}

fun testScenarioA(db: FirebaseDatabase){
    getData(db)
}

fun testScenarioB(db: FirebaseDatabase){
    db.goOffline()
    db.goOnline()
    getData(db)
}

fun getData(db: FirebaseDatabase){
    val ref = db.reference
    ref.get().addOnSuccessListener {
        Log.i(TAG, "Got value ${it.value}")
    }.addOnFailureListener{
        Log.e(TAG, "Error getting data", it)
    }
}

private const val TAG = "MainActivity"
