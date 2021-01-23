package com.example.notificationcreator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "channel-id-default"
    var notificationId = 0

    private val categoryMap = mapOf(
        "alarm" to "alarm",
        "call" to "call",
        "car_emergency" to "car emergency",
        "car_information" to "car information",
        "car_warning" to "car warning",
        "email" to "email",
        "err" to "error",
        "event" to "event",
        "msg" to "message",
        "navigation" to "navigation",
        "progress" to "progress",
        "promo" to "promotion",
        "recommendation" to "recommendation",
        "reminder" to "reminder",
        "service" to "service",
        "social" to "social",
        "status" to "status",
        "sys" to "system",
        "transport" to "transport"
    )

    lateinit var titleText : TextView
    lateinit var contentText : TextView
    lateinit var categorySpinner : Spinner
    lateinit var postButton  : Button
    lateinit var newIdCheckBox : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleText = findViewById<TextView>(R.id.title_input)
        contentText = findViewById<TextView>(R.id.content_input)
        categorySpinner = findViewById<Spinner>(R.id.category_spinner)
        postButton = findViewById<Button>(R.id.post_button)
        newIdCheckBox = findViewById<CheckBox>(R.id.create_new_id_checkbox)

        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categoryMap.keys.toList()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        postButton.setOnClickListener {
            sendNotification(titleText.text.toString(), contentText.text.toString(), categorySpinner.selectedItem.toString())
        }
    }

    private fun sendNotification(title: String, content: String, category: String) {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(content)
            .setCategory(category)
        createNotificationChannel()
        with(NotificationManagerCompat.from(this)) {

            if(newIdCheckBox.isChecked) {
                notify(++notificationId, builder.build())
            } else {
                notify(notificationId, builder.build())
            }
        }

    }

    private fun createNotificationChannel() {

        val notificationManager: NotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "NotificationCreator", importance)

            notificationManager.createNotificationChannel(channel)
        }
    }
}
