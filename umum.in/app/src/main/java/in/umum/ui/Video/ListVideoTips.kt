package `in`.umum.ui.Video



import `in`.umum.R
import `in`.umum.helper.Constants
import `in`.umum.helper.Retrofit.RestAdapter
import `in`.umum.helper.Utils.NetworkUtils
import `in`.umum.helper.Utils.Tools
import `in`.umum.model.OrderModel.OrderItem
import `in`.umum.model.Video.VideoModel
import `in`.umum.model.Video.VideoResponse
import `in`.umum.ui.Adapter.VideoAdapter
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_video.*

import kotlinx.android.synthetic.main.include_failed.*
import kotlinx.android.synthetic.main.include_progress_gif.*
import org.jetbrains.anko.support.v4.toast
import pl.droidsonroids.gif.GifDrawable


class ListVideoTips: Fragment(){
    companion object {
        fun newInstance(): ListVideoTips {
            return ListVideoTips()
        }
        var success :Boolean = false
        var hasLoad : Boolean = false
    }
    lateinit var gifDrawable : GifDrawable
    lateinit var progressDialog : ProgressDialog

    private fun startGif(){
        gifDrawable = img_progress_bar_gif.drawable as GifDrawable
//        gifDrawable.start()
    }
    private fun stopGif(){
        gifDrawable.stop()
    }
    private fun showProgress(ctx: Context){
        progressDialog = ProgressDialog(ctx)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        cont_fragment_list_video.visibility = View.GONE
        //lyt_progress.visibility = View.VISIBLE
        //img_progress_bar_gif.minimumWidth = Tools.getWidthScreen(requireActivity()).toInt()
        //msg_progress_gif.text = getString(R.string.txt_order)
        //startGif()
//        cont_progress_fragment_progress.visibility = View.VISIBLE
//        progressBarFP.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        cont_fragment_list_video.visibility = View.VISIBLE
//        stopGif()
        progressDialog.dismiss()
        lyt_progress.visibility = View.GONE

//        cont_progress_fragment_progress.visibility = View.GONE
//        progressBarFP.visibility = View.GONE
    }
    var listVideo: ArrayList<VideoModel> = ArrayList()
    lateinit var progressAdapter: VideoAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getDataOrder()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_list_video,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gifDrawable = img_progress_bar_gif.drawable as GifDrawable
        gifDrawable.start()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*  Manual
        listVideo.add(OrderItemFirst(1,3))
        listVideo.add(OrderItemFirst(2,4))

        // Create adapter
        progressAdapter= OrderAdapter(listVideo,OrderAdapter.ORDER)

        // Layout manager
        recylerProgress.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)

        //set Adapter
        recylerProgress.adapter = progressAdapter
        */
        if(NetworkUtils.isNetworkAvailable(context!!)){
//            showProgressDialog(context!!)
            showProgress(context!!)
            getDataOrder()
        }else{
            showTryAgainProgress()
        }

        btn_failed_retry.setOnClickListener {
            if(NetworkUtils.isNetworkAvailable(context!!)){
//                showProgressDialog(context!!)
                showProgress(context!!)

            }else{
                toast("Periksa Koneksi Anda")
                showTryAgainProgress()
            }
        }

    }
    private fun showTryAgainProgress(){
        lyt_failed.visibility = View.VISIBLE
        recylerListVideo.visibility = View.GONE
        lyt_no_item.visibility = View.GONE

    }
    private fun hideTryAgainProgress(){
        lyt_failed.visibility = View.GONE
        recylerListVideo.visibility = View.VISIBLE
        lyt_no_item.visibility = View.GONE
    }
    private fun showNoItem(){
        lyt_no_item.visibility = View.VISIBLE
    }
    private fun hideNoItem(){
        lyt_no_item.visibility = View.GONE
        recylerListVideo.visibility = View.VISIBLE
    }
    private fun getDataOrder(){
        //OrderFragment.pref[Constants.PHONE_NUMBER] = "+6281231391835"

        RestAdapter.retrofit.getListVideo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        {videos ->
                            if(videos.status=="success"){
                                listVideo = videos.data as ArrayList<VideoModel>

                                hideTryAgainProgress()
//                                hideProgressDialog()
                                hideProgress()
                                showDataProgress()


                            }else{
                                success = false
                                hasLoad = true
                                toast("Periksa Koneksi Anda")
                                showTryAgainProgress()

//                                hideProgressDialog()
                                hideProgress()
                            }

                        },
                        {error ->
                            Log.e("Galih",error.message)
                            success = false
                            hasLoad = true
                            toast("Periksa Koneksi Anda")
                            showTryAgainProgress()
//                            hideProgressDialog()
                            hideProgress()

                        })
    }

    private fun refreshAdapter(){
        progressAdapter.notifyDataSetChanged()
        if(listVideo.isEmpty()){
            showNoItem()
        }else{
            hideNoItem()
        }
    }
    private fun showDataProgress(){

        //Create Adapter
        progressAdapter = VideoAdapter(listVideo, VideoAdapter.VIDEO)
        //Layout manager
        recylerListVideo.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        //set Adapter
        recylerListVideo.adapter = progressAdapter
        refreshAdapter()
    }
    override fun onDestroy() {
        super.onDestroy()
        //stopGif()
    }
}