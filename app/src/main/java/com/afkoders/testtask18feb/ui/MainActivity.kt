package com.afkoders.testtask18feb.ui

import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afkoders.testtask18feb.R
import com.afkoders.testtask18feb.databinding.ActivityMainBinding
import com.afkoders.testtask18feb.domain.models.ACTION_TYPE
import com.afkoders.testtask18feb.domain.models.ButtonAction
import com.afkoders.testtask18feb.util.sendNotification
import com.afkoders.testtask18feb.viewmodel.ActionButtonViewModel

class MainActivity : AppCompatActivity() {

    //i'm really sorry for this. I should use EventWrapper but i have 6 minutes left
    var skippingInitialTriggeringCrutch = true

    // should be done with DI
    private val viewModel: ActionButtonViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after ativity exists()"
        }
        ViewModelProvider(this, ActionButtonViewModel.Factory(activity.application))
            .get(ActionButtonViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.hasExtra(NOTIFICATION_MARKER_OPEN_CONTACTS) == true) {
            runContactsScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        //i'm really sorry for this. I should use EventWrapper but i have 6 minutes left
        skippingInitialTriggeringCrutch = true

        binding.btnRunAction.setOnClickListener { viewModel.runActionClicked() }

        viewModel.eventNetworkError.observe(this, { networkUnavailable ->
            binding.tvNetworkState.apply {
                text = getString(if (networkUnavailable) R.string.network_down else R.string.network_up)
                setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        if (networkUnavailable) R.color.red else R.color.green
                    )
                )
            }
        })

        viewModel.loading.observe(this, { inProgress ->
            binding.pbActionsFetching.visibility = if (inProgress) View.VISIBLE else View.GONE
        })

        viewModel.actionToExecute.observe(this, { action ->
            if(skippingInitialTriggeringCrutch){
                skippingInitialTriggeringCrutch = false
                return@observe
            }
            action?.apply {
                when (this.type) {
                    ACTION_TYPE.ACTION_TOAST -> runToast()
                    ACTION_TYPE.ACTION_NOTIFICATION -> runNotification()
                    ACTION_TYPE.ACTION_CALL -> runContactsScreen()
                    ACTION_TYPE.ACTION_ANIMATION -> runAnimation()
                    else -> {
                        showEmptyActionsToast()
                    }
                }
            }
        })
    }


    private fun showEmptyActionsToast() {
        Toast.makeText(applicationContext, "No latest actions available", Toast.LENGTH_LONG).show()
    }

    private fun runAnimation() {
        viewModel.addCooldownItem(ACTION_TYPE.ACTION_ANIMATION)
        val rotate = RotateAnimation(
            0F, 360F, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 500
        findViewById<Button>(R.id.btnRunAction).startAnimation(rotate)
    }

    private fun runContactsScreen() {
        viewModel.addCooldownItem(ACTION_TYPE.ACTION_CALL)
        val intent = Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            Toast.makeText(applicationContext, "No component found to open contacts screen", Toast.LENGTH_LONG).show()
        }
    }

    private fun runNotification() {
        viewModel.addCooldownItem(ACTION_TYPE.ACTION_NOTIFICATION)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.sendNotification(
            this.getText(R.string.notification_body).toString(),
            this
        )
    }

    private fun runToast() {
        viewModel.addCooldownItem(ACTION_TYPE.ACTION_TOAST)
        Toast.makeText(applicationContext, "Action is Toast Notification", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val NOTIFICATION_MARKER_OPEN_CONTACTS = "NotificationMarkerOpenContacts"
    }
}