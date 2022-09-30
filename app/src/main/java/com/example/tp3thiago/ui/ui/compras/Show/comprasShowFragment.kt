package com.example.tp3thiago.ui.ui.compras.Show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tp3thiago.R
import com.example.tp3thiago.databinding.FragmentComprasShowBinding
import com.example.tp3thiago.ui.Dao.authDao
import com.google.android.material.snackbar.Snackbar

class comprasShowFragment : Fragment() {

    var _binding: FragmentComprasShowBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: ComprasShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComprasShowBinding.inflate(inflater, container, false)
        val view = binding.root

        val documentId = arguments?.getString("documentId")
        val factory = ComprasShowViewModelFactory(documentId!!)
        viewModel = ViewModelProvider(this, factory).get(ComprasShowViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner) {
            if (it)
                findNavController().popBackStack()
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it.isNotBlank())
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        viewModel.compras.observe(viewLifecycleOwner){
            if (it != null){
                //view.findViewById<TextView>(R.id.input_categoria).text = it.categoria
                view.findViewById<TextView>(R.id.tvPreco).text = it.preco
                view.findViewById<TextView>(R.id.tvQuantidade).text = it.quantidade
                view.findViewById<TextView>(R.id.tvNomeItem).text = it.item
            }
        }

        binding.btnSalvar.setOnClickListener {
            val categoria = binding.inputCategoria.text.toString()
            val preco = binding.tvPreco.text.toString()
            val quantidade = binding.tvQuantidade.text.toString()
            val item = binding.tvNomeItem.text.toString()

            if (item.isNotEmpty() && quantidade.isNotEmpty() && preco.isNotEmpty() && categoria.isNotEmpty()){
                viewModel.atualizar(item, quantidade, preco, categoria)

            }


        }


        setup()
        return view
    }

    fun setup(){
        configuraPapeis()
        setupClickListener()
    }

    fun setupClickListener(){
        binding.imageBackstack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnExcluir.setOnClickListener {
            viewModel.excluir()
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComprasShowViewModel::class.java)
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