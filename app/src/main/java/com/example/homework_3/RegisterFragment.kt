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

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val emailEt = view.findViewById<EditText>(R.id.register_email_edittext)
        val passwordEt = view.findViewById<EditText>(R.id.register_password_edittext)
        val registerBtn = view.findViewById<AppCompatButton>(R.id.register_button)
        val loginTv = view.findViewById<TextView>(R.id.already_registered_login_textview)

        // REGISTER
        registerBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    "Password must be at least 6 characters",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message ?: "Registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // BACK TO LOGIN
        loginTv.setOnClickListener {
            findNavController()
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}
