package com.example.wallet2.ui.user

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.wallet2.MainActivity
import com.example.wallet2.R
import com.example.wallet2.data.userDb
import com.example.wallet2.ui.dashboard.DashboardFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
const val PREF_NAME = "PREF_NAME"
const val USERNAME = "USERNAME"
const val EMAIL = "EMAIL"
const val IS_LOGGED = "IS_LOGGED"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var viewModel: UserViewModel? = null

    private lateinit var next_button: Button
    private lateinit var google_button: Button
    private lateinit var email: EditText
    private lateinit var password_text: EditText
    private lateinit var password_layout: TextInputLayout
    private lateinit var signUpBtn: TextView
    private lateinit var progress_bar: ProgressBar

    private lateinit var preferences: SharedPreferences

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        preferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        val dataBaseInstance = userDb.getDatabasenIstance(requireContext())
        viewModel?.setInstanceOfDb(dataBaseInstance)

        //Firebase
        auth = FirebaseAuth.getInstance()
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient((context as Activity?)!!, gso)
        val crashlytics = FirebaseCrashlytics.getInstance()


        next_button = view.findViewById(R.id.next_button)
        google_button = view.findViewById(R.id.sign_in_btn)
        password_text = view.findViewById(R.id.password_edit_text)
        email = view.findViewById(R.id.email)
        password_layout = view.findViewById(R.id.password_text_input)
        signUpBtn = view.findViewById(R.id.signUpText)
        progress_bar = view.findViewById(R.id.progress)

        signUpBtn.setOnClickListener {
            /*val registerFragment = RegisterFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, registerFragment)
            transaction.commit()*/
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null)
        }

        google_button.setOnClickListener {
            signin()
        }



        next_button.setOnClickListener {


            if (!isPasswordValid(password_text.text)) {
                password_layout.error = getString(R.string.astr_error_password)
            } else {
                crashlytics.setCustomKey("email", email.text.trim().toString())

                signInUser(email.text.trim().toString(), password_text.text.trim().toString())
                //val init = log()
                //Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()

                    /*
                if(init){
                    Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()
                    val dashboardFragment = DashboardFragment()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, dashboardFragment)
                    transaction.commit()
                }else{
                    Toast.makeText(requireContext(), "Try again", Toast.LENGTH_LONG).show()
                }
                     */
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

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            /*val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit()*/
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, null)
        }
    }

    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener((context as Activity?)!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    //val user = auth.currentUser

                    /*val dashboardFragment = DashboardFragment()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, dashboardFragment)
                    transaction.commit()*/
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, null)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun log(): Boolean {

        var flag = false

        val usuario = email.text.trim().toString()
        val pass = password_text.text.trim().toString()

        if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(pass)) {
            Toast.makeText(requireContext(), "Please enter valid details", Toast.LENGTH_LONG).show()
        } else {

            CoroutineScope(IO).launch {
                val dataBaseInstance = userDb.getDatabasenIstance(requireContext())
                val query = dataBaseInstance.personDataDao().getUser(usuario, pass)
                //setValues()
                //saveValues(query.username, query.email)
                if (query != null || !query.equals(null)){
                    flag = true
                }
                //saveValues(query.username)
            }
        }
        Thread.sleep(1500)
        return flag
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 1
    }

    private fun signin(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Funciones nuevas
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener((context as Activity?)!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val dashboardFragment = DashboardFragment()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, dashboardFragment)
                    transaction.commit()
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, null)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }



    companion object {

        private const val RC_SIGN_IN = 120
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

    /*
    private fun saveValues(username: String) {

        preferences.edit()
            .putString("USERNAME", username)
            .apply()
    }

    private fun setValues() {
        preferences.getString("USERNAME", "")
    }
     */

}