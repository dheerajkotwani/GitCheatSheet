package project.dheeraj.gitfinder.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.dheeraj.gitfinder.ClickInterface
import project.dheeraj.gitfinder.Model.SecondaryModel
import project.dheeraj.gitfinder.R

class RecyclerViewAdapter(
    val context: Context,
    val list: List<SecondaryModel>,
    val clickInterface: ClickInterface
    )  : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int =  list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemTitle.text = list[position].usage
        holder.itemContent.text = list[position].label.capitalize()

        holder.itemView.setOnClickListener {
            clickInterface.clickListener(position)
        }

    }
}

class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
    val itemContent: TextView = itemView.findViewById(R.id.itemContent)

}