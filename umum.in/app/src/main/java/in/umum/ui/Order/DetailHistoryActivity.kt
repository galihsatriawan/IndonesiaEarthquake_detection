package `in`.umum.ui.Order

import `in`.umum.R
import `in`.umum.model.OrderModel.OrderItem
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_history.*

class DetailHistoryActivity : AppCompatActivity() {
    lateinit var order:OrderItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Detail Riwayat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        if(intent!=null){
            order = intent.getSerializableExtra("order") as OrderItem
            showDetail()
        }


    }
    fun showDetail(){
        ActProgTxtIdOrder.text = order.id_order
        ActProgTxtAlamat.text = order.alamat_detail_order
        ActProgTxtDeskripsi.text = order.detail_deskripsi_order
        ActProgTxtBiaya.text = order.harga_total.toString()
        ActProgTxtDateWork.text = order.tgl_order_dibuat.toString()
        ActProgTxtDateSelesai.text = order.tgl_selesai.toString()
        ActProgTxtVendor.text = order.nama_vendor
        ActProgTxtSaran.text = order.saran.toString()
        ActProgTxtNamaTukang.text = order.nama_staff
        ActProgTxtVendor.text = order.nama_vendor
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
