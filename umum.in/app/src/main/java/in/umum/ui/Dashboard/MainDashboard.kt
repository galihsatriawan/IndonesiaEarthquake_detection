package `in`.umum.ui.Dashboard

import `in`.umum.R
import `in`.umum.ui.Dashboard.FragmentGempa.DetailGempa
import `in`.umum.ui.Dashboard.FragmentGempa.ListGempaDirasakan
import `in`.umum.ui.Video.ListVideoTips
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main_dashboard.*
import org.jetbrains.anko.toast

class MainDashboard : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(DetailGempa.newInstance(),null)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_progress -> {
                openFragment(ListGempaDirasakan.newInstance(),null)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                openFragment(ListVideoTips.newInstance(),null)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    fun openFragment(fragment: Fragment, addBackStack:String?){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame_main_dashboard,fragment) // Like trans.add
        //trans.addToBackStack(addBackStack)
        trans.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .add(R.id.frame_main_dashboard, DetailGempa.newInstance())
//                    .addToBackStack("MainFragment")
                    .commit()

        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //openFragment(DashboardFragment.newInstance(),null)
    }
    private var exitTime:Long = 0
    override fun onBackPressed() {
//        super.onBackPressed()
        when {
            supportFragmentManager.backStackEntryCount > 0 -> {
//                supportFragmentManager.popBackStack()
                super.onBackPressed()
            }
            (System.currentTimeMillis() - exitTime) > 2000 -> {
                Log.d("Galih System" ,"${(System.currentTimeMillis() - exitTime)} -- ${System.currentTimeMillis()}")
                toast("Press again to exit")
                exitTime = System.currentTimeMillis()
            }
            else -> this.finish()
        }

    }

}
