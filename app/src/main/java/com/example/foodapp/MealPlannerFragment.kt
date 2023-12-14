package com.example.foodapp

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.foodapp.data.Recipe
import java.text.SimpleDateFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MealPlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealPlannerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var selectedDates = mutableSetOf<Long>()
    private lateinit var view: View
    private lateinit var mealplanner: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_meal_planner, container, false)
        mealplanner = view.findViewById(R.id.mealplanner)
        selectDate()
        initialSetup()
        return view
    }

    private fun initialSetup() {
        val mealplansArray = resources.obtainTypedArray(R.array.meal_plans)

        for (i in 0 until mealplansArray.length()) {
            val mealplansArrayResourceId = mealplansArray.getResourceId(i, 0)
            val mealplan = resources.getStringArray(mealplansArrayResourceId)
            addItem(mealplan[0] + "\t\t\t\t\t" + mealplan[1])
        }
    }

    fun selectDate() {
        val calendar = view.findViewById<CalendarView>(R.id.calendarView)
        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val dayOfWeek = dateFormat.format(selectedDate.time)
            addMealPlan("$month-$dayOfMonth-$year $dayOfWeek")
            println("$month-$dayOfWeek-$year $dayOfWeek")
        }
    }

    private fun addMealPlan(date: String) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Add a Meal Plan")

        val editText = EditText(view.context)
        editText.hint = "Enter meals"
        builder.setView(editText)

        builder.setPositiveButton("OK") { dialog, _ ->
            addItem(date + "\t\t\t\t\t" + editText.text.toString())
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun addItem(text: String) {
        val tv = TextView(view.context)
        tv.text = text
        mealplanner.addView(tv)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealPlannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealPlannerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
