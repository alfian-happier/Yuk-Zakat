package app.alfian.yukzakat.ui.main.view.home.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import app.alfian.yukzakat.BR
import app.alfian.yukzakat.R
import app.alfian.yukzakat.data.model.Zakat

/**
 * Created by Alfian on 6/22/2023.
 */

class HistoryListAdapter(private val items : List<Zakat>,
                         private val adapterOnClick: (Zakat?, position: Int) -> Unit) : RecyclerView.Adapter<HistoryListAdapter.DataViewHolder>(){

    inner class DataViewHolder(private val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Zakat, position: Int){
            binding.setVariable(BR.zakat,item)
            binding.root.setOnClickListener {
                adapterOnClick(item,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_history_list,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(items[position],position)
    }

}