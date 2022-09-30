package com.example.tp3thiago.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp3thiago.R
import com.example.tp3thiago.ui.model.Compras

class comprasRV(private val reuniaoList : ArrayList<Compras>, val comprasClickInterface: ComprasClickInterface, val comprasClickDeleteInterface: ComprasClickDeleteInterface) : RecyclerView.Adapter<comprasRV.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): comprasRV.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comprasrecyclerviewitem, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: comprasRV.ViewHolder, position: Int) {
        val compras: Compras = reuniaoList[position]
        holder.item.text = compras.item
        holder.quantidade.text = compras.quantidade
        holder.preço.text = compras.preco
        holder.categoria.text = compras.categoria


//        holder.btndelete.setOnClickListener {
//
//            comprasClickDeleteInterface.onDeleteIconClick(reuniaoList.get(position))
//
//        }

        holder.itemView.setOnClickListener {

            comprasClickInterface.onComprasClick(reuniaoList.get(position))
        }

    }

    override fun getItemCount(): Int {
        return reuniaoList.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val item : TextView = itemView.findViewById(R.id.idTVItem)
        val quantidade : TextView = itemView.findViewById(R.id.idTVQuantidade)
        val preço : TextView = itemView.findViewById(R.id.idTVPreco)
        val categoria: TextView = itemView.findViewById(R.id.idTVCategoria)
//        val btndelete = itemView.findViewById<Button>(R.id.IdTvBtn)
    }

    fun updateList(newList: List<Compras>) {

        reuniaoList.clear()

        reuniaoList.addAll(newList)


        notifyDataSetChanged()
        //https://stackoverflow.com/questions/71980733/recycler-view-doesnt-update-list-after-notifydatasetchanged
    }

    interface ComprasClickInterface {
        fun onComprasClick(compras: Compras)
    }

    interface ComprasClickDeleteInterface {

        fun onDeleteIconClick(compras: Compras)
    }

}