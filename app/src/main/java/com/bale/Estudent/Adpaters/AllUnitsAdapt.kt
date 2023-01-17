package com.bale.Estudent.Adpaters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bale.Estudent.Models.JoinedEntries
import com.bale.Estudent.R
import com.bale.Estudent.Views.MarkRegister
import com.bale.Estudent.Views.ValidateCode
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject

class AllUnitsAdapt(val ls: List<JoinedEntries>):RecyclerView.Adapter<AllUnitsAdapt.ItemViewHolder>() {
    class ItemViewHolder( val view: View): RecyclerView.ViewHolder(view){
        val unitName: MaterialTextView = view.findViewById(R.id.unitName)
        val lecturer: MaterialTextView = view.findViewById(R.id.Lecturer)
        val unitCode: MaterialTextView = view.findViewById(R.id.unitCode)
        val card: MaterialCardView = view.findViewById(R.id.unitCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.unit,parent,false)
        return ItemViewHolder(newView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = ls[position]
        holder.unitName.text = item.unitName
        holder.unitCode.text = item.unitCode
        holder.lecturer.text = item.lectrurerName
        holder.card.setOnClickListener {
            val intent = Intent(holder.view.context, MarkRegister::class.java)
            intent.putExtra("unit_Name",item.unitName)
            intent.putExtra("unit_code",item.unitCode)
            intent.putExtra("lecturer",item.lectrurerName)
            holder.view.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return  ls.size
    }
}