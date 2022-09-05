package com.peterchege.pchat.ui.screens.sign_in

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.peterchege.pchat.api.requests.AddUser
import com.peterchege.pchat.models.User
import com.peterchege.pchat.repositories.UserRepository
import com.peterchege.pchat.util.Constants
import com.peterchege.pchat.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userRepository: UserRepository,

):ViewModel() {
    private var _user = mutableStateOf<User?>(null)
    var user: State<User?> = _user

    private var _text = mutableStateOf("")
    var text:State<String> = _text

    fun onChangeUser(user:User,navController: NavController){
        val userInfoEditor = sharedPreferences.edit()
        userInfoEditor.apply{
            putString(Constants.USER_DISPLAY_NAME,user.displayName)
            putString(Constants.USER_ID,user.userId)
            putString(Constants.USER_EMAIL,user.email)
            putString(Constants.USER_IMAGE_URL,user.imageUrl)
//            putString(Constants.FCM_TOKEN,token)
            apply()
        }
        addUserToDatabase(user = user,navController = navController)

    }

    private  fun addUserToDatabase(user:User,navController:NavController){
        Log.e("TEST","TEST")
        viewModelScope.launch {
            try{
                val addUser = AddUser(
                    displayName = user.displayName ?: "",
                    email = user.email ?: "",
                    imageUrl = user.imageUrl ,
                    userId = user.userId
                )
                val response = userRepository.addUser(addUser = addUser)
                navController.navigate(Screens.DASHBOARD_SCREEN)
            }catch (e: HttpException){
                Log.e("HTTP ERROR",e.localizedMessage ?: "Http error")

            }catch (e:IOException){
                Log.e("IO ERROR",e.localizedMessage ?: "IO error")

            }
        }


    }
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val signInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(Constants.CLIENT_ID)
            .requestId()
            .requestProfile()
            .build()

        return GoogleSignIn.getClient(context, signInOption)
    }



}