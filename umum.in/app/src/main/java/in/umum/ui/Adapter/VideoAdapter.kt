package `in`.umum.ui.Adapter

import `in`.umum.R
import `in`.umum.model.OrderModel.OrderItem
import `in`.umum.model.Video.VideoModel

import `in`.umum.ui.Order.DetailHistoryActivity
import `in`.umum.ui.Order.DetailProgressActivity
import `in`.umum.ui.Video.DetailVideoTips
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.io.Serializable

/**
 * Created by root on 10/6/18.
 */
class VideoAdapter (var videosList:ArrayList<VideoModel>, var tipe : Int) : RecyclerView.Adapter<VideoAdapter.ViewHolder>(){
    companion object {
        const val HISTORY = 1
        const val VIDEO = 2
    }
    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        //holder.txtName?.text = ordersList[position].kategori.toString()
        //holder.txtTitle?.text = "Bantuin Team"
        val video = this.videosList[position]
        holder.txtJudul.text = video.judul_video
        holder.txtCreator.text = video.creator
        Glide.with(holder.itemView.context).load(video.gambar_video).into(holder.gmbr)

//
//        holder?.itemView.setOnClickListener {
//            holder?.itemView.context.startActivity(Intent(holder?.itemView.context,LoginActivity::class.java))
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.video_item,parent,false)
        return ViewHolder(v).listen { pos, _ ->
            // What you wanna do
            when (tipe){
                VIDEO -> {
                    var videoIntent = Intent(parent.context,DetailVideoTips::class.java)
                    videoIntent.putExtra("video",videosList[pos] as Serializable)
                    videoIntent.putExtra("videoId",videosList[pos].id_video)
                    parent.context.startActivity(videoIntent)
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
        return videosList.size
    }

    //Holder Recyle View
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtJudul = itemView.findViewById<TextView>(R.id.txt_judul_video)
        val txtCreator = itemView.findViewById<TextView>(R.id.txt_creator)
        val btnBuka = itemView.findViewById<Button>(R.id.btn_buka)
        val gmbr = itemView.findViewById<ImageView>(R.id.img_video)

    }

}