package `in`.umum.ui.Dashboard.FragmentGempa

import `in`.umum.R
import `in`.umum.helper.Retrofit.RestAdapter
import `in`.umum.helper.Utils.NetworkUtils
import `in`.umum.helper.Utils.Tools
import `in`.umum.model.Gempa.GempaModel
import `in`.umum.ui.Adapter.GempaAdapter
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
import kotlinx.android.synthetic.main.fragment_list_gempa.*
import kotlinx.android.synthetic.main.include_failed.*
import kotlinx.android.synthetic.main.include_progress_gif.*
import org.jetbrains.anko.support.v4.toast
import pl.droidsonroids.gif.GifDrawable


class ListGempaDirasakan: Fragment(){
    companion object {
        fun newInstance(): ListGempaDirasakan {
            return ListGempaDirasakan()
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
        cont_fragment_list_gempa.visibility = View.GONE
        lyt_progress.visibility = View.VISIBLE
        img_progress_bar_gif.minimumWidth = Tools.getWidthScreen(requireActivity()).toInt()
        msg_progress_gif.text = getString(R.string.txt_order)
        //startGif()
//        cont_progress_fragment_progress.visibility = View.VISIBLE
//        progressBarFP.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        cont_fragment_list_gempa.visibility = View.VISIBLE
//        stopGif()
        progressDialog.dismiss()
        lyt_progress.visibility = View.GONE

//        cont_progress_fragment_progress.visibility = View.GONE
//        progressBarFP.visibility = View.GONE
    }
    var listGempa: ArrayList<GempaModel> = ArrayList()
    lateinit var progressAdapter: GempaAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_list_gempa,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gifDrawable = img_progress_bar_gif.drawable as GifDrawable
        gifDrawable.start()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*  Manual
        listGempa.add(OrderItemFirst(1,3))
        listGempa.add(OrderItemFirst(2,4))

        // Create adapter
        progressAdapter= OrderAdapter(listGempa,OrderAdapter.ORDER)

        // Layout manager
        recylerProgress.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)

        //set Adapter
        recylerProgress.adapter = progressAdapter
        */
        if(NetworkUtils.isNetworkAvailable(context!!)){
//            showProgressDialog(context!!)
            showProgress(context!!)
            getAllDataGempa()
        }else{
            showTryAgainProgress()
        }

        btn_failed_retry.setOnClickListener {
            if(NetworkUtils.isNetworkAvailable(context!!)){
//                showProgressDialog(context!!)
                showProgress(context!!)
                getAllDataGempa()
            }else{
                toast("Periksa Koneksi Anda")
                showTryAgainProgress()
            }
        }

    }
    private fun showTryAgainProgress(){
        lyt_failed.visibility = View.VISIBLE
        recylerListGempa.visibility = View.GONE
        lyt_no_item.visibility = View.GONE

    }
    private fun hideTryAgainProgress(){
        lyt_failed.visibility = View.GONE
        recylerListGempa.visibility = View.VISIBLE
        lyt_no_item.visibility = View.GONE
    }
    private fun showNoItem(){
        lyt_no_item.visibility = View.VISIBLE
    }
    private fun hideNoItem(){
        lyt_no_item.visibility = View.GONE
        recylerListGempa.visibility = View.VISIBLE
    }
    private fun getAllDataGempa(){

        RestAdapter.retrofit.getListGempa()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        {gempas ->
                            if(gempas.status=="success"){

                                listGempa = gempas.data as ArrayList<GempaModel>
                                hideTryAgainProgress()
//                                hideProgressDialog()
                                hideProgress()
                                showDataProgress()

                                hasLoad = true
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
        if(listGempa.isEmpty()){
            showNoItem()
        }else{
            hideNoItem()
        }
    }
    private fun showDataProgress(){

        //Create Adapter
        progressAdapter = GempaAdapter(listGempa, GempaAdapter.GEMPA)
        //Layout manager
        recylerListGempa.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        //set Adapter
        recylerListGempa.adapter = progressAdapter
        refreshAdapter()
    }
    override fun onDestroy() {
        super.onDestroy()
        //stopGif()
    }
}