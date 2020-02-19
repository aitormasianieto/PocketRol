package org.ieselcaminas.aitor.pocketrol

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import org.ieselcaminas.aitor.pocketrol.database.Repo
import org.ieselcaminas.aitor.pocketrol.databinding.ActivityLoginBinding
import kotlin.Exception


class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"

    val SIGN_IN_REQUEST_CODE = 1001
    val binding: ActivityLoginBinding by lazy { DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.LoginTheme) //Changes Theme

        binding.authLayout.setOnClickListener { loginClick() }
    }

    private fun loginClick() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            launchSignInFlow()
        }
        else {
            logIn()
        }
    }

    private fun logIn() {
        val repo = Repo()
        repo.checkUserExistence()
        startActivity(Intent(this, MainActivity::class.java))
        finish() //I want to prevent back to this activity
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email, they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
            // This is where you can provide more ways for users to register and sign in.
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.NewLoginAuthTheme) //Puts custom theme
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Log.i(ContentValues.TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")

                logIn()
            }
            else {
                /* Sign in failed.
                 * If response is null the user canceled the sign-in flow using the back button.
                 * Otherwise check response.getError().getErrorCode() and handle the error.*/
                Log.i(ContentValues.TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }


    }
}

