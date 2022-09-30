package com.example.tp3thiago

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.tp3thiago.ui.api.FrutasService

class frutasListFragment : Fragment() {


    private lateinit var viewModel: FrutasListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(FrutasListViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_frutas_list, container, false)

        viewModel.frutas.observe(viewLifecycleOwner){


            if (it != null) {
                if (it.isNotEmpty())
                    view.findViewById<ListView>(R.id.listViewFrutas).adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            }
        }





        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FrutasListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}