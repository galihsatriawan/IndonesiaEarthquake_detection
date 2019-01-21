package `in`.umum.ui.Dashboard.FragmentGempa




import `in`.umum.R
import `in`.umum.database.AppDatabase
import `in`.umum.helper.Retrofit.RestAdapter
import `in`.umum.helper.SharedPref
import `in`.umum.helper.Utils.NetworkUtils
import `in`.umum.helper.Utils.Tools
import `in`.umum.model.Gempa.GempaModel
import android.app.ProgressDialog

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragmen_detail_gempa.*
import kotlinx.android.synthetic.main.include_failed.*
import kotlinx.android.synthetic.main.include_progress_gif.*
import org.jetbrains.anko.support.v4.toast
import pl.droidsonroids.gif.GifDrawable

class DetailGempa : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    var latitude :Double = 3.9052269
    var longitude : Double = 124.4053002
    companion object {
        fun newInstance(): DetailGempa {
            return DetailGempa()
        }
    }
    private val prefs : SharedPreferences by lazy {
        SharedPref.defaultPrefs(context!!)
    }
    private lateinit var db : AppDatabase

    //First
    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }
    //Second
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    //Third
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragmen_detail_gempa,container,false)

        //Testing
        if(savedInstanceState==null){

        }
        return view
    }
    //Fourth
    lateinit var title :String
    lateinit var content :String
    var pos :String ="OK"
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
        //cont_detail_gempa.visibility = View.GONE

        //startGif()
//        cont_progress_fragment_progress.visibility = View.VISIBLE
//        progressBarFP.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        //cont_detail_gempa.visibility = View.VISIBLE
//        stopGif()
        progressDialog.dismiss()

//        cont_progress_fragment_progress.visibility = View.GONE
//        progressBarFP.visibility = View.GONE
    }
    private fun showTryAgainProgress(){
        lyt_failed.visibility = View.VISIBLE
        cont_detail_gempa.visibility = View.GONE


    }
    private fun hideTryAgainProgress(){
        lyt_failed.visibility = View.GONE
        cont_detail_gempa.visibility = View.VISIBLE

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        if(NetworkUtils.isNetworkAvailable(context!!)){

//            showProgressDialog(context!!)
            //showDetail(context!!)
            showProgress(context!!)
            getDataGempa()
        }else{
            showTryAgainProgress()
        }

        btn_failed_retry.setOnClickListener {
            if(NetworkUtils.isNetworkAvailable(context!!)){
//                showProgressDialog(context!!)
               // showDetail(context!!)
                getDataGempa()
            }else{
                toast("Periksa Koneksi Anda")
                showTryAgainProgress()
            }
        }


    }
    lateinit  var gempa : GempaModel
    private fun getDataGempa(){

        RestAdapter.retrofit.getNewGempa()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        {gempas ->
                            if(gempas.status=="success"){
                                gempa = gempas.data[0]

                                hideTryAgainProgress()
//                                hideProgressDialog()
                                hideProgress()
                                showDetail()


                            }else{

                                toast("Periksa Koneksi Anda")
                                showTryAgainProgress()
//                                hideProgressDialog()
                                hideProgress()
                            }

                        },
                        {error ->

                            toast("Periksa Koneksi Anda")
                            showTryAgainProgress()
//                            hideProgressDialog()
                            hideProgress()

                        })
    }
    //Fifth
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
    fun showDetail(){

        txt_tgl.text = gempa.tgl_kejadian
        txt_jam.text = gempa.jam_kejadian
        txt_potensi_tsunami.text = gempa.potensi_tsunami
        txt_wilayah.text = gempa.wilayah
        txt_magnitude.text = gempa.magnitude.toString()
        txt_kedalaman.text = gempa.kedalaman.toString()
        txt_koordinat.text = "${gempa.latitude}, ${gempa.longitude}"
        txt_waktu_jeda.text = "1 Jam yang Lalu"
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        // Add a marker in Sydney and move the camera`
        val sydney = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title(gempa.wilayah))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


}