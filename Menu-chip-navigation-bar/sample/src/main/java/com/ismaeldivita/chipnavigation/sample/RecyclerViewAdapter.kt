package com.ismaeldivita.chipnavigation.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import java.util.*

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var TIPOCLIMA: TextView? = null
    var RESUMEN: TextView? = null
    var TEMPERATURA: TextView? = null
    var HUMEDAD: TextView? = null
    var tarjeta: CardView? = null

    fun RecyclerViewHolder(itemView: View) {
        TIPOCLIMA = itemView.findViewById<TextView>(R.id.TIPOCLIMA)
        RESUMEN = itemView.findViewById<TextView>(R.id.RESUMEN)
        TEMPERATURA = itemView.findViewById<TextView>(R.id.TEMPERATURA)
        HUMEDAD = itemView.findViewById<TextView>(R.id.HUMEDAD)
        tarjeta = itemView.findViewById(R.id.tarjeta)
    }
}

class RecyclerViewAdapter(listadata: MutableList<Data>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    private var listadata: MutableList<Data> = ArrayList<Data>()

    fun RecyclerViewAdapter(listadata: MutableList<Data>) {
        this.listadata = listadata
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemview = inflater.inflate(R.layout.item, parent, false)
        return RecyclerViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return listadata.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        YoYo.with(Techniques.FadeIn).playOn(holder.tarjeta)
        holder.TIPOCLIMA!!.text = listadata[position].getS()
        holder.RESUMEN!!.text = listadata[position].getS1()
        holder.TEMPERATURA!!.text = listadata[position].getS2()
        holder.HUMEDAD!!.text = listadata[position].getS3()
    }


}