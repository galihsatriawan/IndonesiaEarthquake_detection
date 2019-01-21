package `in`.umum.ui.Adapter

import `in`.umum.R
import `in`.umum.model.Gempa.GempaModel
import `in`.umum.model.OrderModel.OrderItem
import `in`.umum.model.Video.VideoModel
import `in`.umum.ui.Dashboard.DetailGempa

import `in`.umum.ui.Order.DetailHistoryActivity
import `in`.umum.ui.Order.DetailProgressActivity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.Serializable

/**
 * Created by root on 10/6/18.
 */
class GempaAdapter (var gempasList:ArrayList<GempaModel>, var tipe : Int) : RecyclerView.Adapter<GempaAdapter.ViewHolder>(){
    companion object {
        const val HISTORY = 1
        const val GEMPA = 2
    }
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.txtMagnitude.text= gempasList[position].magnitude.toString()
        holder.txtKedalaman.text= gempasList[position].kedalaman.toString()
        holder.txtWaktu.text= "${gempasList[position].tgl_kejadian} ${gempasList[position].jam_kejadian}"
        holder.txtLokasi.text= gempasList[position].wilayah
//
//        holder?.itemView.setOnClickListener {
//            holder?.itemView.context.startActivity(Intent(holder?.itemView.context,LoginActivity::class.java))
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.gempa_item,parent,false)
        return ViewHolder(v).listen { pos, _ ->
            // What you wanna do
            when (tipe){
                GEMPA -> {
                    var orderIntent = Intent(parent.context,DetailGempa::class.java)
                    orderIntent.putExtra("gempa",gempasList[pos] as Serializable)

                    parent.context.startActivity(orderIntent)
                }
            }
        }
    }
    fun <T: RecyclerView.ViewHolder> T.listen(event: (position: Int,type: Int)->Unit):T{
        itemView.setOnClickListener{
            event.invoke(adapterPosition,itemViewType)
        }
        return this
    }


    override fun getItemCount(): Int {
        return gempasList.size
    }

    //Holder Recyle View
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtMagnitude = itemView.findViewById<TextView>(R.id.txt_magnitude)
        val txtKedalaman = itemView.findViewById<TextView>(R.id.txt_kedalaman)
        val txtLokasi= itemView.findViewById<TextView>(R.id.txt_lokasi)
        val txtWaktu = itemView.findViewById<TextView>(R.id.txt_waktu)

    }

}