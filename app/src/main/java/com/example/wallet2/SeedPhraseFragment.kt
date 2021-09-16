package com.example.wallet2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeedPhraseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeedPhraseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var toolbar: Toolbar
    private lateinit var show_button: MaterialButton
    private lateinit var copy_button: MaterialButton
    private var show_boolean = true
    private lateinit var seed_text: TextView


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

        val view = inflater.inflate(R.layout.fragment_seed_phrase, container, false)

        toolbar = view.findViewById(R.id.app_bar)
        seed_text = view.findViewById(R.id.seed)
        show_button = view.findViewById(R.id.show)
        copy_button = view.findViewById(R.id.copy)

        toolbar.setNavigationOnClickListener {
            val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right)
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit() }

        show_button.setOnClickListener {
            if (show_boolean) {
                seed_text.setText(R.string.astr_seed_show)
                show_button.setText(R.string.astr_show_false)
                show_boolean = !show_boolean
            }
            else {
                seed_text.setText(R.string.astr_seed_hide)
                show_button.setText(R.string.astr_show_true)
                show_boolean = !show_boolean
            }
        }

        copy_button.setOnClickListener {
            Toast.makeText(context, "Seed phrase copied", Toast.LENGTH_LONG).show()
        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeedPhraseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeedPhraseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}