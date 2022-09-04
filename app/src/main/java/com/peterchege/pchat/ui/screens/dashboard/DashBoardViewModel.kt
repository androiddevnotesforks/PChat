package com.peterchege.pchat.ui.screens.dashboard

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.peterchege.pchat.util.Constants
import com.peterchege.pchat.util.Screens
import com.peterchege.pchat.util.getGoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
):ViewModel() {
    val displayName = sharedPreferences.getString(Constants.USER_DISPLAY_NAME,null)
    val imageUrl = sharedPreferences.getString(Constants.USER_IMAGE_URL,null)


    fun logoutUser(navController:NavController,context: Context){
        val signInClient = getGoogleSignInClient(context = context)

        sharedPreferences.edit().remove(Constants.USER_ID).commit()
        sharedPreferences.edit().remove(Constants.USER_DISPLAY_NAME).commit()
        sharedPreferences.edit().remove(Constants.USER_EMAIL).commit()
        sharedPreferences.edit().remove(Constants.USER_IMAGE_URL).commit()
        signInClient.signOut()
        navController.navigate(Screens.SIGN_IN_SCREEN)


    }

}