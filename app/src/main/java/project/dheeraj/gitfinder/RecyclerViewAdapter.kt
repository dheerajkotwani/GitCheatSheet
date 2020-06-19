package project.dheeraj.gitfinder

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val context: Context, val list: ArrayList<Model>)  : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)

    }

    override fun getItemCount(): Int =  list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        holder.itemTitle.text = list[position].usage
        holder.itemContent.text = list[position].label

    }


}

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
    val itemContent: TextView = itemView.findViewById(R.id.itemContent)

}