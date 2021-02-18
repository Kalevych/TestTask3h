package com.afkoders.testtask18feb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.afkoders.testtask18feb.R
import com.afkoders.testtask18feb.viewmodel.ActionButtonViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ActionButtonViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after ativity exists()"
        }
        ViewModelProvider(this, ActionButtonViewModel.Factory(activity.application))
            .get(ActionButtonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}