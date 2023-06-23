package app.alfian.yukzakat.ui.main.view.zakat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.model.Anggota
import app.alfian.yukzakat.databinding.ItemAnggotaListBinding

/**
 * Created by Zharfan on 6/23/2023.
 */

class AnggotaListAdapter(private val anggotaList : ArrayList<Anggota>,
                         private val adapterOnRemove: (position: Int) -> Unit) : RecyclerView.Adapter<AnggotaListAdapter.DataViewHolder>(){

    inner class DataViewHolder(private val binding : ItemAnggotaListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Anggota, position: Int){
            binding.anggota = item
            binding.root.findViewById<ImageButton>(R.id.buttonRemove).setOnClickListener {
                adapterOnRemove(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(ItemAnggotaListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return anggotaList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(anggotaList[position],position)
    }
}