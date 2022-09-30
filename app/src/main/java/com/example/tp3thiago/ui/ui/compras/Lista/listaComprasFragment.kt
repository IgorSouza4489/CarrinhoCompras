package com.example.tp3thiago.ui.ui.compras.Lista

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3thiago.R
import com.example.tp3thiago.databinding.FragmentListaComprasBinding
import com.example.tp3thiago.ui.Adapter.comprasRV
import com.example.tp3thiago.ui.Dao.authDao
import com.example.tp3thiago.ui.model.Compras
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class listaComprasFragment : Fragment(), comprasRV.ComprasClickInterface,
    comprasRV.ComprasClickDeleteInterface {

    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView : RecyclerView
    private lateinit var comprasArrayList : ArrayList<Compras>
    private lateinit var myAdapter: comprasRV
    private var _binding: FragmentListaComprasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListaComprasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaComprasBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ListaComprasViewModel::class.java)
        val view = binding.root
        binding.loginButton.visibility = View.GONE
        recyclerView = binding.comprasRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        comprasArrayList = arrayListOf()

        myAdapter = comprasRV(comprasArrayList, this, this)

        recyclerView.adapter = myAdapter

        viewModel.compras.observe(viewLifecycleOwner, { list -> list?.let {myAdapter.updateList(it) }} )



        auth = Firebase.auth

        val user = auth.currentUser

//        if (user != null) {
//            for (userInfo in user.getProviderData()) {
//                if (userInfo.providerId == "facebook.com") {
//                    binding.loginButton.visibility = View.VISIBLE
//                    binding.btnLogout.visibility = View.GONE
//                }
//                else
//                    binding.btnLogout.visibility = View.VISIBLE
//
//            }
//        }



        setup()
        return view
    }


    fun setup(){
        setupClickListener()
    }

    fun setupClickListener(){
        binding.btnLogout.setOnClickListener{
            signOut()
        }

        binding.floatingActionButton2.setOnClickListener{
            findNavController().navigate(R.id.comprasCreateFragment)
        }

        binding.floatingActionButtonFruit.setOnClickListener{
            findNavController().navigate(R.id.frutasListFragment)
        }
    }

    fun signOut(){
        viewModel.signOut()
        LoginManager.getInstance().logOut();
        findNavController().navigate(R.id.signInFragment)
    }

//    override fun onNoteClick(note: Note) {
//        val intent = Intent(this, EditNoteActivity::class.java)
//        intent.putExtra("noteTipo", "Edit")
//        intent.putExtra("noteTitulo", note.noteTitulo)
//        intent.putExtra("noteDescriçao", note.noteDescriçao)
//        intent.putExtra("noteId", note.id)
//        startActivity(intent)
//        this.finish()
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListaComprasViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onComprasClick(compras: Compras) {
        Toast.makeText(requireContext(), "item: ${compras.documentId}", Toast.LENGTH_LONG).show()
        findNavController()
            .navigate(
                R.id.comprasShowFragment, bundleOf(
                    "documentId" to compras.documentId
                )
            )

    }

    override fun onDeleteIconClick(compras: Compras) {
        //viewModel.excluir()
        Toast.makeText(requireContext(), "${compras.item} Excluído", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        val user = authDao.getCurrentUser()
        if (user == null) {
            findNavController()
                .navigate(R.id.signInFragment)
        }
    }

}