package com.realy.blink_box.gridAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realy.blink_box.databinding.BoxLayoutBinding

class BlinkAdapter(val boxes:List<BoxItem>,val iOnItemClicked: IOnItemClicked) : RecyclerView.Adapter<BlinkAdapter.AppViewHolder>() {

    inner class AppViewHolder(val binding:BoxLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(item: BoxItem){
            binding.item = item
            binding.container.setOnClickListener {
                iOnItemClicked.onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = BoxLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)


        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = boxes.get(position)
        holder.bindData(app)
    }

    override fun getItemCount(): Int = boxes.size
}