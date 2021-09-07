package com.example.wallet2

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.wallet2.data.UserViewModel
import com.example.wallet2.data.models.User
import com.example.wallet2.data.userDb
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var viewModel: UserViewModel? = null

    private lateinit var next_button: Button
    private lateinit var username: EditText
    private lateinit var password_text: EditText
    private lateinit var password_layout: TextInputLayout
    private lateinit var signUpBtn: TextView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        var dataBaseInstance = userDb.getDatabasenIstance(requireContext())
        viewModel?.setInstanceOfDb(dataBaseInstance)

        next_button = view.findViewById(R.id.next_button)
        password_text = view.findViewById(R.id.password_edit_text)
        username = view.findViewById(R.id.username)
        password_layout = view.findViewById(R.id.password_text_input)
        signUpBtn = view.findViewById(R.id.signUpText)

        signUpBtn.setOnClickListener {
            val registerFragment = RegisterFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, registerFragment)
            transaction.commit()
        }

        next_button.setOnClickListener {


            if (!isPasswordValid(password_text.text)) {
                password_layout.error = getString(R.string.astr_error_password)
            } else {
                log()
                Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()
            }
        }

        password_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_text.text)) {
                password_layout.error = null //Clear the error
            }
            false
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun log() {

        var usuario = username.text.trim().toString()
        var pass = password_text.text.trim().toString()

        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(pass)) {
            Toast.makeText(requireContext(), "Please enter valid details", Toast.LENGTH_LONG).show()
        } else {

            CoroutineScope(IO).launch {
                val dataBaseInstance = userDb.getDatabasenIstance(requireContext())
                val query = dataBaseInstance.personDataDao().getUser(usuario, pass)

                if (query == null || query.equals(null)){
//                    Toast.makeText(requireContext(), "Wrong credentials", Toast.LENGTH_LONG).show()
                }else{
                    val dashboardFragment = DashboardFragment()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, dashboardFragment)
                    transaction.commit()
                }
            }
        }
    }

    /*
       In reality, this will have more complex logic including, but not limited to, actual
       authentication of the username and password.
    */
    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}