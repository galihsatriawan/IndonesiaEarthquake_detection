package `in`.umum.ui.Dashboard

import `in`.umum.R
import `in`.umum.model.Gempa.GempaModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragmen_detail_gempa.*



class DetailGempa : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        // Add a marker in Sydney and move the camera
        val loc = LatLng(gempa.latitude, gempa.longitude)
        mMap.addMarker(MarkerOptions().position(loc).title(gempa.wilayah))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }
    lateinit var gempa : GempaModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gempa)
        if(intent!=null){
            gempa = intent.getSerializableExtra("gempa") as GempaModel
        }
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        showDetail()

    }
    fun showDetail(){
        txt_tgl.text = gempa.tgl_kejadian
        txt_jam.text = gempa.jam_kejadian

        txt_wilayah.text = gempa.wilayah
        txt_magnitude.text = gempa.magnitude.toString()
        txt_kedalaman.text = gempa.kedalaman.toString()
        txt_koordinat.text = "${gempa.latitude}, ${gempa.longitude}"
        txt_waktu_jeda.text = "1 Jam yang Lalu"
    }
}
