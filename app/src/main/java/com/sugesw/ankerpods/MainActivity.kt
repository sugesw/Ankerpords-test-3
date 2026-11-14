package com.sugesw.ankerpods

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sugesw.ankerpods.ui.AppRoot
import com.sugesw.ankerpods.ui.theme.AnkerPodsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnkerPodsTheme {
                AppRoot()
            }
        }
    }
}
