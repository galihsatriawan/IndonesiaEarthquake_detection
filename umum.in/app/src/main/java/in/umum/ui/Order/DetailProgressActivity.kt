package `in`.umum.ui.Order

import `in`.umum.R
import `in`.umum.helper.Retrofit.RestAdapter
import `in`.umum.helper.Utils.NetworkUtils
import `in`.umum.model.OrderModel.OrderItem
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_progress.*
import kotlinx.android.synthetic.main.include_failed.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import org.jetbrains.anko.toast
import java.io.Serializable

class DetailProgressActivity : AppCompatActivity() {
    val WAITING_TUKANG = "w"
    val CHATTING_PROSES = "ch"
    val PERSETUJUAN_PRICE = "p"
    val SELESAI = "f"
    lateinit var status : String
    companion object {
        lateinit var order:OrderItem
    }

    private fun showProgress(){
        lyt_progress.visibility = View.VISIBLE

        msg_progress_bar.text = getString(R.string.txt_cari_vendor)
        hideTryAgain()
        hideContProgress()
    }
    private fun hideProgress(){
//        progress_daftar.progress = ProgressBar.GONE
//        cont_prog_daftar.visibility = View.GONE
        lyt_progress.visibility = View.GONE

    }
    private fun showTryAgain(msg : String){
//        cont_try_daftar.visibility = View.VISIBLE
//        btn_try_vendor.visibility = View.VISIBLE
        lyt_failed.visibility = View.VISIBLE
        failed_message.text = msg

        hideProgress()
        hideContProgress()
    }
    private  fun hideTryAgain(){
        lyt_failed.visibility = View.GONE
//        btn_try_vendor.visibility = View.GONE
    }
    private fun showContProgress(){
        conten_progress.visibility = View.VISIBLE
    }
    private fun hideContProgress(){
        conten_progress.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_progress)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Detail Progress"
        if(intent != null){
            //status = intent.getStringExtra("status")
//            order = intent.getSerializableExtra("order") as OrderItem
//            status =order.status!!

        }
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        initContent()

    }
    fun initContent(){
        if(NetworkUtils.isNetworkAvailable(applicationContext!!)){
            showProgress()
            //getVendor(servis!!.id_service!!)
            getDataOrder(order.id_order!!)

        }else{
            toast("Periksa Koneksi Anda")
            showTryAgain(getString(R.string.no_internet_text))
        }
    }
    private fun showDetail(){
            status = order.status!!
            showContProgress()


    }
    private fun getDataOrder(id:String ){


        RestAdapter.retrofit.getOrderById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        {orders ->
                            if(orders.status=="success"){

                                showDetail()
                                //showDataHistory()
//                                hideProgressDialog()
                                hideProgress()
                                hideTryAgain()

                            }else{
                                toast("Periksa Koneksi Anda")
                                showTryAgain(getString(R.string.msg_failed_load_data))
//                                hideProgressDialog()
                                Log.d("Galih",orders.status)
                                hideProgress()
                            }

                        },
                        {error ->
                            Log.e("Galih",error.message)
                            toast("Periksa Koneksi Anda")
                            showTryAgain(getString(R.string.msg_failed_load_data))
                            hideProgress()
//                            hideProgressDialog()
//                            hideTryAgainHistory()
                        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun openFragment(fragment: Fragment, addBackStack:String?){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_main_dashboard,fragment) // Like trans.add
        //trans.addToBackStack(addBackStack)
        trans.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_refresh,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item!!.itemId){
        R.id.refresh_menu -> {
            initContent()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
