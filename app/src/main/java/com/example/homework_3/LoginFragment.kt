package com.example.homework_3

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val emailEt = view.findViewById<EditText>(R.id.log_in_email_edit_text)
        val passwordEt = view.findViewById<EditText>(R.id.log_in_password_edit_text)
        val loginBtn = view.findViewById<AppCompatButton>(R.id.log_in_button)
        val registerNowTv = view.findViewById<TextView>(R.id.register_now_textview)
        val passwordResetTv = view.findViewById<TextView>(R.id.password_reset_textview)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_productsFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message ?: "Login failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        registerNowTv.setOnClickListener {
            findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        passwordResetTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }
}