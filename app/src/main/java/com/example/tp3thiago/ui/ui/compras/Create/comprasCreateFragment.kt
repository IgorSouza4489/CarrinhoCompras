package com.example.tp3thiago.ui.ui.compras.Create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tp3thiago.R
import com.example.tp3thiago.databinding.FragmentComprasCreateBinding
import com.example.tp3thiago.ui.Dao.authDao
import com.google.android.material.snackbar.Snackbar

class comprasCreateFragment : Fragment() {


    private var _binding: FragmentComprasCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ComprasCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComprasCreateBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ComprasCreateViewModel::class.java)
        val view = binding.root
        binding.loadBar.visibility = View.GONE


        viewModel.msg
            .observe(viewLifecycleOwner){
                if ( it.isNotBlank()) {
                    showSnackbar(view, it)
                }
            }

        viewModel.status.observe(viewLifecycleOwner){
            if (it){

                findNavController().popBackStack()
            }
//            if (it){
//                binding.inputMarca.setText("")
//                binding.inputModelo.setText("")
//                binding.inputPlaca.setText("")
//            }
        }

        setup()
        return view
    }



    fun createCompras(){
        val item = binding.tvNomeItem.text.toString()
        val quantidade = binding.tvQuantidade.text.toString()
        val preco = binding.tvPreco.text.toString()
        val categoria = binding.inputCategoria.text.toString()

        if (item.isNotEmpty() && quantidade.isNotEmpty() && preco.isNotEmpty() && categoria.isNotEmpty()){



            viewModel.inserir(item, quantidade, preco, categoria)
            binding.loadBar.visibility = View.VISIBLE
        }

        else Toast.makeText(requireContext(), "Insira os campos", Toast.LENGTH_LONG).show()

    }

    fun setup(){
        configuraPapeis()
        setupClickListener()
    }

    fun setupClickListener(){
        binding.btnCreate.setOnClickListener {
            createCompras()
        }

        binding.imageBackstack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    var listaDePapeis = arrayOf(
        "Açúcares e doces",
        "Óleos e gorduras",
        "Leite e derivados",
        "Leguminosas e oleaginosas",
        "Carnes e Ovos",
        "Frutas",
        "Verduras e Legumes",
        "Carboidratos"

    )

    fun configuraPapeis(){
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.categorias_item_list, listaDePapeis)
        binding.inputCategoria.setAdapter(adapter)
    }


    private fun showSnackbar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComprasCreateViewModel::class.java)
        // TODO: Use the ViewModel
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