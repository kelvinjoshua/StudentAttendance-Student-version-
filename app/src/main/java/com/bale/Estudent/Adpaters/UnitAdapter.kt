package com.bale.Estudent.Adpaters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bale.Estudent.Models.JoinedEntries
import com.bale.Estudent.Models.lecturerUnitEntry
import com.bale.Estudent.R
import com.bale.Estudent.Views.MarkRegister
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

open class UnitAdapter(private val query: Query, private val c: Context):FireStoreAdapter<UnitAdapter.ItemViewHolder>(query){
     class ItemViewHolder( val view: View): RecyclerView.ViewHolder(view){
        val unitName:MaterialTextView = view.findViewById(R.id.unitName)
        val lecturer:MaterialTextView = view.findViewById(R.id.Lecturer)
        val unitCode:MaterialTextView = view.findViewById(R.id.unitCode)
        val card:MaterialCardView = view.findViewById(R.id.unitCard)
        fun bind(
            snapshot: DocumentSnapshot
        ) {
            val unit = snapshot.toObject<JoinedEntries>() ?: return
            unitName.text = unit.unitName
            lecturer.text = unit.lectrurerName
            unitCode.text = unit.unitCode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val newView = LayoutInflater.from(parent.context).inflate(R.layout.unit,parent,false)
        return ItemViewHolder(newView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getSnapshot(position))
        holder.card.setOnClickListener {
            val intent = Intent(holder.view.context,MarkRegister::class.java)
            val item = getSnapshot(position)
            intent.putExtra("unit_Name",item.get("unitName").toString())
            intent.putExtra("unit_code",item.get("unitCode").toString())
            intent.putExtra("lecturer",item.get("lecturerName").toString())
            holder.view.context.startActivity(intent)
        }
    }

}