package com.example.tp3thiago.ui.Dao

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tp3thiago.ui.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class authDao {

    companion object{

        private val dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val auth = Firebase.auth
        fun getCurrentUser() = auth.currentUser

        fun deslogar(){
            auth.signOut()
        }

        fun cadastrarUsuario(email: String, senha: String) : Task<AuthResult> {
            return  auth.createUserWithEmailAndPassword(email, senha)
        }

        fun validarUsuario(email: String ,senha: String): Task<AuthResult> {

            return auth.signInWithEmailAndPassword(email, senha)
        }


        fun profilecreateFirestore(email: String, nome: String){
            val user = Usuario()
            user.email = email
            user.nome = nome

            insertProfile(user)
        }

        fun profilecreateFacebook(userUid: String){
            //val currentUser = auth.currentUser
            val userRef = dbFirestore.collection("profile")
            val user = Usuario()
            user.nome = userUid
            user.email = ""

            userRef.document(userUid).set(user).addOnSuccessListener {
                Log.d("user", "Collection Criada")
            }.addOnFailureListener{
                Log.d("user", "Erro na criação da Collection")
            }
        }

        fun insertProfile(user: Usuario){
            val currentUser = auth.currentUser
            val userRef = dbFirestore.collection("profile")

            userRef.document(currentUser?.uid!!).set(user).addOnSuccessListener {
                Log.d("user", "Collection Criada")
            }.addOnFailureListener{
                Log.d("user", "Erro na criação da Collection")
            }

        }

        fun recuperarSenha(email: String): Task<Void> {
            return auth.sendPasswordResetEmail(email)
        }

        fun signOut(){
            auth.signOut()
        }

    }


}