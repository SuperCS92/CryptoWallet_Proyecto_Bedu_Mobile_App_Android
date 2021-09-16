package com.example.wallet2

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.app.Application
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.wallet2.data.UserViewModel
import com.example.wallet2.data.models.User
import com.example.wallet2.data.userDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private var viewModel: UserViewModel? = null

    private lateinit var logInBtn: TextView
    private lateinit var registerBtn: Button
    private lateinit var generateSeedBtn: Button

    private lateinit var fullname: EditText
    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var seed: EditText

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
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        var dataBaseInstance = userDb.getDatabasenIstance(requireContext())
        viewModel?.setInstanceOfDb(dataBaseInstance)

        fullname = view.findViewById(R.id.fullname)
        email = view.findViewById(R.id.email)
        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        seed = view.findViewById(R.id.seedphrase)

        logInBtn = view.findViewById(R.id.loginText)
        registerBtn = view.findViewById(R.id.buttonSignUp)
        generateSeedBtn = view.findViewById(R.id.buttongenerateSeed)

        generateSeedBtn.setOnClickListener {
            seed.setText(generateSeed())
        }

        registerBtn.setOnClickListener {
            saveData()

            val logInFragment = LoginFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, logInFragment)
            transaction.commit()
        }


        logInBtn.setOnClickListener {
            val logInFragment = LoginFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, logInFragment)
            transaction.commit()
        }


        // Inflate the layout for this fragment
        return view
    }

    private fun saveData() {
        var name = fullname.text.trim().toString()
        var correo = email.text.trim().toString()
        var usuario = username.text.trim().toString()
        var pass = password.text.trim().toString()
        var seed = seed.text.trim().toString()

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(seed)) {
            Toast.makeText(requireContext(), "Please enter valid details", Toast.LENGTH_LONG).show()
        } else {

            var person = User(0, fullname = name, email = correo, username = usuario, password = pass, seed = seed)
            viewModel?.saveDataIntoDb(person)
            Toast.makeText(requireContext(), "Usuario Registrado", Toast.LENGTH_LONG).show()
        }
    }

    private fun generateSeed(): String{
        var seed:String = ""

        seed = "This is a seed test"

        return seed
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}