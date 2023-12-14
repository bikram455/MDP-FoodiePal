package com.example.foodapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var view: View

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
        view = inflater.inflate(R.layout.fragment_contact, container, false)
        setupCall()
        setupEmail()
        return  view
    }

    private fun setupEmail() {
        view.findViewById<Button>(R.id.send).setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("admin@foodapp.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact message")
            val name = view.findViewById<EditText>(R.id.nameText)
            val emailId = view.findViewById<EditText>(R.id.emailText)
            val messageText = view.findViewById<EditText>(R.id.messageText)
            intent.putExtra(Intent.EXTRA_TEXT, "$name \n $emailId \n $messageText")
            startActivity(Intent.createChooser(intent, "Sending email..."))
        }
    }

    private fun setupCall() {
        view.findViewById<Button>(R.id.call).setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1234567890"))
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}