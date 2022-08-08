package com.example.flashcards

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcards.databinding.ActivityLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityLoginBinding

    // FirebaseAuth
    private lateinit var auth: FirebaseAuth

    private var email = ""
    private var password = ""

    // constants
    private companion object {
        private const val TAG = "LoginActivity"
        private const val PASSWORD_LOGIN_TAG = "PasswordLogin: "
        private const val GOOGLE_LOGIN_TAG = "GoogleLogin: "
        private const val FACEBOOK_LOGIN_TAG = "FacebookLogin: "
        private const val RC_SIGN_IN = 100
    }

    /** Password login variables */
    // Progress Dialog
    private lateinit var progressDialog: ProgressDialog

    /** Google login variables */
    // client for interacting with GoogleSignIn API
    private lateinit var googleSignInClient: GoogleSignInClient

    /** Facebook variables */
    private lateinit var callbackManager: CallbackManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Waiting")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        // check if the user is already logged in
        checkIfLogged()

        /** Login and password authorization */
        // handle click, login button
        binding.loginButton.setOnClickListener {
            validateData()
        }

        // handle click, create new account, go to RegisterActivity
        binding.notHaveAccountTextLabel.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        /** Google authorization */
        // GoogleSignInOptions contains options to configure Google Sign In API
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // will be resolved when build first time
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // handle click, begin Google Sign In
        binding.googleLoginButton.setOnClickListener {
            // Begin Google SignIn
            Log.d(TAG, "${GOOGLE_LOGIN_TAG}: begin Google SignIn")
            // intent used to start sign-in flow
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

        /** Facebook authorization */
        // callbackManager manages callbacks into Facebook SDK from onActivityResult() method
        callbackManager = CallbackManager.Factory.create()

        binding.facebookLoginButton.setPermissions("email")
        binding.facebookLoginButton.setOnClickListener{
            facebookSignIn()
        }


    }

    /** check if logged function */
    private fun checkIfLogged(){
        // get current user
        val authUser = auth.currentUser
        if(authUser != null){
            // user logged in, go to to the app
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    /** Password login function */
    // validate data
    private fun validateData(){

        // get input email and password
        email = binding.emailEditText.text.toString().trim()
        password = binding.passwordEditText.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // check if email is correctly formed
            Log.e(TAG, "${PASSWORD_LOGIN_TAG}Incorrect email")
            binding.emailEditText.error = "Incorrect email"
        } else if(password.isEmpty()){
            // check if password is not empty
            Log.e(TAG, "${PASSWORD_LOGIN_TAG}Empty password")
            binding.passwordEditText.error = "Please enter password"
        } else {
            passwordLogin()
        }
    }

    /** Password login function */
    private fun passwordLogin(){

        // show ProgressDialog
        progressDialog.show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                // hide ProgressDialog
                progressDialog.dismiss()
                Log.e(TAG, "${PASSWORD_LOGIN_TAG}User login: ${auth.currentUser!!.email}")
                Toast.makeText(this, "Logged with email: ${auth.currentUser!!.email}", Toast.LENGTH_SHORT).show()

                // go to the app
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                // hide ProgressDialog
                progressDialog.dismiss()
                Log.e(TAG, "${PASSWORD_LOGIN_TAG}Login failed due to ${e.message}")
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
    }

    /** Google and Facebook function */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /** Google */
        if(requestCode == RC_SIGN_IN) {
            Log.d(TAG, "${GOOGLE_LOGIN_TAG}onActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google sign in success, now auth with firebase
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch(e: Exception) {
                // failed Google SignIn
                Log.d(TAG, "${GOOGLE_LOGIN_TAG}onActivityResult: ${e.message}")
            }
        } else {
            /** Facebook */
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    /** Google Sign In function */
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {

        Log.d(TAG, "${GOOGLE_LOGIN_TAG}Begin firebase auth with Google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                // login success
                Log.d(TAG, "${GOOGLE_LOGIN_TAG}Logged in")

                // get loggedIN user
                val firebaseUser = auth.currentUser
                // get user info
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email

                Log.d(TAG, "${GOOGLE_LOGIN_TAG}Uid $uid")
                Log.d(TAG, "${GOOGLE_LOGIN_TAG}Email $email")

                if(authResult.additionalUserInfo!!.isNewUser){
                    // user is new
                    Log.d(TAG, "${GOOGLE_LOGIN_TAG}Account created... \n$email")
                    Toast.makeText(this,"Account created.. \n$email", Toast.LENGTH_SHORT).show()
                }
                else {
                    // existing user
                    Log.d(TAG, "${GOOGLE_LOGIN_TAG}Existing user... \n$email")
                    Toast.makeText(this, "Logged in... \n$email", Toast.LENGTH_SHORT).show()
                }

                // start app activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }
            .addOnFailureListener { e ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Login Failed due to ${e.message}")
                Toast.makeText(this, "Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    /** Facebook Sign In function */
    private fun facebookSignIn(){
        // register a callback to the given callback manager
        binding.facebookLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "${FACEBOOK_LOGIN_TAG}onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "${FACEBOOK_LOGIN_TAG}onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "${FACEBOOK_LOGIN_TAG}onError", error)
            }
        })
    }

    /** Facebook Sign In function */
    private fun handleFacebookAccessToken(accessToken: AccessToken){

        Log.d(TAG, "${FACEBOOK_LOGIN_TAG}handleFacebookAccessToken() function: $accessToken")

        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        // sign in
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "${FACEBOOK_LOGIN_TAG}signInWithCredential: success")
                    val user = auth.currentUser
                    // start app activity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user
                    Log.w(TAG, "${FACEBOOK_LOGIN_TAG}signInWithCredential: failure ", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }

    }
}