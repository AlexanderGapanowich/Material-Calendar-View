package com.applandeo.materialcalendarsampleapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarsampleapp.utils.getCircleDrawableWithText
import com.applandeo.materialcalendarsampleapp.utils.getThreeDots
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.utils.midnightCalendar
import kotlinx.android.synthetic.main.calendar_activity.*
import java.util.*

/**
 * Created by Applandeo Team.
 */

class CalendarActivity : AppCompatActivity() {

    private val disabledDays: List<Calendar>
        get() = listOf(
                midnightCalendar.apply { add(Calendar.DAY_OF_MONTH, 2) },
                midnightCalendar.apply { add(Calendar.DAY_OF_MONTH, 1) },
                midnightCalendar.apply { add(Calendar.DAY_OF_MONTH, 18) })

    private fun getRandomCalendar() =
            Calendar.getInstance().apply {
                add(Calendar.MONTH, Random().nextInt(99))
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_activity)

        val events = mutableListOf<EventDay>()

        val calendar = Calendar.getInstance()
        events.add(EventDay(calendar,
                this.getCircleDrawableWithText("M"),
                labelColor = Color.parseColor("#228B22")))

        val calendar1 = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 2) }
        val calendar2 = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 5) }
        val calendar3 = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 7) }
        val calendar4 = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 13) }

        events.apply {
            add(EventDay(calendar1, drawableRes = R.drawable.sample_icon_2, labelColor = Color.parseColor("#228B22")))
            add(EventDay(calendar2, drawableRes = R.drawable.sample_icon_3, labelColor = Color.parseColor("#228B22")))
            add(EventDay(calendar3, drawableRes = R.drawable.sample_four_icons, labelColor = Color.parseColor("#228B22")))
            add(EventDay(calendar4, (this@CalendarActivity).getThreeDots(), labelColor = Color.parseColor("#228B22")))
        }

        val min = Calendar.getInstance().apply { add(Calendar.MONTH, -2) }
        val max = Calendar.getInstance().apply { add(Calendar.MONTH, 2) }

        calendarView.apply {
            setMinimumDate(min)
            setMaximumDate(max)
            setEvents(events)
            setDisabledDays(disabledDays)
            setOnDayClickListener(object : OnDayClickListener {
                override fun onDayClick(eventDay: EventDay) {
                    Toast.makeText(applicationContext, getTitle(eventDay), Toast.LENGTH_SHORT).show()
                }
            })
        }

        setDateButton.setOnClickListener {
            try {
                val randomCalendar = getRandomCalendar()
                val text = randomCalendar.time.toString()
                Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
                calendarView.setDate(randomCalendar)
            } catch (exception: OutOfDateRangeException) {
                exception.printStackTrace()

                Toast.makeText(applicationContext, "Date is out of range", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getTitle(eventDay: EventDay) =
            "${eventDay.calendar?.time.toString()} ${eventDay.isEnabled}"
}
