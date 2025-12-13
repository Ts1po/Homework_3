package com.example.homework_3

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val emailEt = view.findViewById<EditText>(R.id.forgot_password_email_edittext)
        val sendButton = view.findViewById<AppCompatButton>(R.id.forgot_password_send_button)

        sendButton.setOnClickListener {
            val email = emailEt.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), task.exception?.message ?: "Failed to send reset link", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}