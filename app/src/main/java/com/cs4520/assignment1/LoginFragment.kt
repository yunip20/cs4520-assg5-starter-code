package com.cs4520.assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.FragmentLoginBinding

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
    // TODO: Rename and change types of parameters
    private lateinit var etUsername: EditText;
    private lateinit var etPassword:EditText;
    private lateinit var btnLogin: Button;
    private var _binding: FragmentLoginBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        etUsername = binding.etUsername
        etPassword = binding.etPassword
        btnLogin = binding.btnLogin

        btnLogin.setOnClickListener {
            attemptLogin()
        }
        return view;
    }

    private fun attemptLogin() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        if (username == "admin" && password == "admin") {
            // Clear fields before navigation
            etUsername.text.clear()
            etPassword.text.clear()

            // Navigate to ProductListFragment
            findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
        } else {
            // Display a toast message for incorrect credentials
            Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment LoginFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            LoginFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}