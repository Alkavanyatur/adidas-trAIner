package es.trainer.trainer.data.repository.signin

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import es.trainer.trainer.R
import es.trainer.trainer.data.model.UserDto
import es.trainer.trainer.ui.application.App

class SignInRepository: SignInDataSource {

    private lateinit var loginListener: SignInDataSource.OnLoginRequestListener
    private lateinit var registerListener: SignInDataSource.OnRegisterRequestListener
    private lateinit var controlUserListener: SignInDataSource.OnControlUserRequestListener

    private var mDatabaseReference: DatabaseReference? = FirebaseDatabase.getInstance().reference
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var user: FirebaseUser


    private lateinit var dateBird: String
    private var deltaN: Float = 0F
    private lateinit var name: String
    private var vO2: Float = 0F
    private var weight: Float = 0F

    private lateinit var emailUser: String
    private lateinit var identifierAlbumUser: String
    private lateinit var passwordUser: String

    override fun loginWithFirebase(loginRequestListener: SignInDataSource.OnLoginRequestListener, emailStr: String, passwordStr: String) {
        loginListener = loginRequestListener

        mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful){

                user = mAuth.currentUser!!
                obtainIdentifierUserFirebase(user)
            }
            else{
                loginListener.onLoginError(R.string.error_login)
            }
        }
    }

    private fun obtainIdentifierUserFirebase(user: FirebaseUser){
        mDatabaseReference!!.child("users").child(user.uid).child("personal").addListenerForSingleValueEvent(obtainIdentifierUserEventListener)
    }

    private var obtainIdentifierUserEventListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {

            val userDto = dataSnapshot.getValue(UserDto::class.java)
            updatePreferences(userDto!!.dateBirth,userDto!!.deltaN,userDto!!.name,userDto!!.vO2,userDto!!.weight)

            loginListener.onLoginSuccess()
        }

        override fun onCancelled(databaseError: DatabaseError) {
            loginListener.onLoginError(R.string.error_login)
        }

    }

    override fun registerWithFirebase(registerRequestListener: SignInDataSource.OnRegisterRequestListener, emailStr: String, passwordStr: String, identifierStr: String) {
        registerListener = registerRequestListener

        emailUser = emailStr
        passwordUser = passwordStr
        identifierAlbumUser = identifierStr

        mDatabaseReference!!.child("album").child(identifierStr).addListenerForSingleValueEvent(checkIdentifierUserEventListener)
    }

    private var checkIdentifierUserEventListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {

            if (dataSnapshot.value != null) {
                registerUserClient()
            }
            else{
                registerListener.onRegisterError(R.string.error_register)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            registerListener.onRegisterError(R.string.error_register)
        }
    }

    private fun registerUserClient(){
        mAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener{ task: Task<AuthResult> ->

            if (task.isSuccessful){
                user = mAuth.currentUser!!
                registerUserInFirebase(user)
            }
            else{
                registerListener.onRegisterError(R.string.error_register)
            }
        }
    }

    private fun registerUserInFirebase(user: FirebaseUser) {

        val currentUserDB = mDatabaseReference!!.child("users").child(user.uid).child("personal")
        currentUserDB.child("email").setValue(emailUser)

        updatePreferences(dateBird,deltaN,name,vO2,weight)

        registerListener.onRegisterSuccess()
    }

    override fun controlUserWithFirebase(controlUserRequestListener: SignInDataSource.OnControlUserRequestListener) {

        controlUserListener = controlUserRequestListener

        if (mAuth.currentUser != null){
            user = mAuth.currentUser!!
            controlUserListener.onControlUserSuccess()
        }
        else {
            controlUserListener.onControlUserError()
        }
    }


    private fun updatePreferences(_dateBird: String,_deltaN: Float,_name: String,_vO2: Float,_weight: Float){
        App.prefs!!.updatePreferences(_dateBird,_deltaN,_name,_vO2,_weight)
    }




}