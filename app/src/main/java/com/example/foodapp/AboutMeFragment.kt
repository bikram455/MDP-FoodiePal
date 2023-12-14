package com.example.foodapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.foodapp.data.User
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutMeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutMeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var view: View

    private lateinit var layout: LinearLayout
    private lateinit var leftElement: TextView
    private lateinit var rightElement: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_me, container, false)
        setupInitial()
        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun setupInitial() {
        val receivedIntent = activity?.intent
        val user: User? = receivedIntent?.getSerializableExtra("user", User::class.java)
        layout = view.findViewById(R.id.aboutMeLayout)

        var ll = LinearLayout(view.context)
        ll.orientation = LinearLayout.HORIZONTAL

        val nameLabel = TextView(view.context)
        nameLabel.text = "Name"
        nameLabel.id = R.id.name_label_id
        ll.addView(nameLabel)

        val name = TextView(view.context)
        name.setPadding(20, 0, 0, 0)
        name.text = user?.name
        name.id = R.id.name_text_id
        ll.addView(name)

        layout.addView(ll)
        leftElement = nameLabel
        rightElement =name
    }

     fun getLayoutParams(pos: Int, id: Int): RelativeLayout.LayoutParams {
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(pos, id)
        return layoutParams
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutMeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutMeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getRelativeLayout(): LinearLayout? {
        return view?.findViewById(R.id.aboutMeLayout)
    }

    fun getLeftElement(): Int {
        return leftElement.id
    }

    fun getRightElement(): Int {
        return rightElement.id
    }

    fun setLeftRighElement(left: TextView, right: TextView): Unit {
        leftElement = left
        rightElement = right
    }
}