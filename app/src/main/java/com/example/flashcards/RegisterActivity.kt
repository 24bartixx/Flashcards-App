package com.example.flashcards

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityRegisterBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var auth: FirebaseAuth

    private var email = ""
    private var password = ""

    private companion object {
        private const val TAG = "Register Activity"
        private const val REGISTER_TAG = "Register: "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure ActionBar, enable back button
        actionBar = supportActionBar!!
        actionBar.title = "SignUp"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Waiting")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)

        auth = FirebaseAuth.getInstance()

        // handle click, begin sign up
        binding.registerButton.setOnClickListener {
            validateData()
        }

    }

    /** Validate sign up data */
    private fun validateData(){
        // get input email and password
        email = binding.emailEditText.text.toString().trim()
        password = binding.passwordEditText.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // check if email is correctly formed
                Log.e(TAG, "${REGISTER_TAG}Incorrect email")
                binding.emailEditText.error = "Incorrect email"
            } else if(password.isEmpty()){
                // check if password is not empty
                Log.e(TAG, "${REGISTER_TAG}Empty password")
                binding.passwordEditText.error = "Please enter password"
            } else if(password.length < 7){
                // check if password is longer than 6 letters
                Log.e(TAG, "${REGISTER_TAG}Too short password")
                binding.passwordEditText.error = "Password must have at least 7 characters"
            } else {
                passwordSignUp()
            }
        }

    /** Go to previous activity support function */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity, when back button of actionbar is clicked
        return super.onSupportNavigateUp()
    }

    /** Sign up with Firebse */
    private fun passwordSignUp(){
        // show progress dialog
        progressDialog.show()

        // create account
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // sign up success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                Log.d(TAG, "${REGISTER_TAG}Account created with email $email")
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                // sign up failure
                progressDialog.dismiss()
                Log.e(TAG, "${REGISTER_TAG}Signup failed due to ${e.message}")
                Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
            }
    }
}