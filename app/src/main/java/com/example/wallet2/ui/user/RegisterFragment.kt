package com.example.wallet2.ui.user

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.wallet2.MainActivity
import com.example.wallet2.R
import com.example.wallet2.data.models.User
import com.example.wallet2.data.userDb
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    private lateinit var auth: FirebaseAuth

    private var viewModel: UserViewModel? = null

    private lateinit var logInBtn: TextView
    private lateinit var registerBtn: Button
    private lateinit var generateSeedBtn: Button

    private lateinit var email_layout: TextInputLayout
    private lateinit var progress_bar: ProgressBar

    private lateinit var fullname: EditText
    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var seed: EditText

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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
        val dataBaseInstance = userDb.getDatabasenIstance(requireContext())
        viewModel?.setInstanceOfDb(dataBaseInstance)
        val crashlytics = FirebaseCrashlytics.getInstance()

        fullname = view.findViewById(R.id.fullname)
        email = view.findViewById(R.id.email)
        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        seed = view.findViewById(R.id.seedphrase)
        email_layout = view.findViewById(R.id.textInputLayoutEmail)
        progress_bar = view.findViewById(R.id.progress)

        logInBtn = view.findViewById(R.id.loginText)
        registerBtn = view.findViewById(R.id.buttonSignUp)
        generateSeedBtn = view.findViewById(R.id.buttongenerateSeed)

        generateSeedBtn.setOnClickListener {
            seed.setText(generateSeed())
        }

        registerBtn.setOnClickListener {

            crashlytics.setCustomKey("register", "Button Sign In")

            when {
                email.text.trim().toString().isEmpty() -> {
                    email_layout.error = "Email Required"
                    return@setOnClickListener
                }
                password.text.trim().toString().isEmpty() -> {
                    password.error = "Password Required"
                    return@setOnClickListener
                }
                username.text.trim().toString().isEmpty() -> {
                    username.error = "Username Required"
                    return@setOnClickListener
                }
                fullname.text.trim().toString().isEmpty() -> {
                    fullname.error = "Fullname Required"
                    return@setOnClickListener
                }
                seed.text.trim().toString().isEmpty() -> {
                    seed.error = "Seed Required"
                    return@setOnClickListener
                }
                !email.text.trim().toString().isEmpty() && !password.text.trim().toString().isEmpty() -> {

                    progress_bar.visibility = View.VISIBLE

                    createUser(email.text.trim().toString(), password.text.trim().toString())
                    saveData()
                    Toast.makeText(requireContext(), "Usuario Registrado", Toast.LENGTH_LONG).show()

                    CoroutineScope(Dispatchers.IO).launch{
                        (activity as MainActivity).customNotification()
                    }

                    /*val logInFragment = LoginFragment()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, logInFragment)*/

                    progress_bar.visibility = View.GONE
                    //transaction.commit()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment, null)
                }
            }

        }


        logInBtn.setOnClickListener {
            /*val logInFragment = LoginFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, logInFragment)
            transaction.commit()*/
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment, null)
        }

        email.setOnKeyListener { _, _, _ ->
            if (isEmailValid(email.text)) {
                email_layout.error = null //Clear the error
            }
            false
        }


        // Inflate the layout for this fragment
        return view
    }

    private fun createUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((context as Activity?)!!) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                //val user = auth.currentUser
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveData() {
        val name = fullname.text.trim().toString()
        val correo = email.text.trim().toString()
        val usuario = username.text.trim().toString()
        val pass = password.text.trim().toString()
        val seed = seed.text.trim().toString()

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(seed)) {
            Toast.makeText(requireContext(), "Please enter valid details", Toast.LENGTH_LONG).show()
        } else {

            val person = User(0, fullname = name, email = correo, username = usuario, password = pass, seed = seed)
            viewModel?.saveDataIntoDb(person)
            Toast.makeText(requireContext(), "Usuario Registrado", Toast.LENGTH_LONG).show()
        }
    }

    private fun generateSeed(): String{
        //var seed:String = ""
        //seed = "This is a seed test"
        val length = 15
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun isEmailValid(text: Editable?): Boolean {
        return text != null && text.length >= 1
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