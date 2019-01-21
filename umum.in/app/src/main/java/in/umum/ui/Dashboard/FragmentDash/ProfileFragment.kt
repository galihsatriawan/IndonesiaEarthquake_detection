package `in`.umum.ui.Dashboard.FragmentDash


import `in`.umum.R
import `in`.umum.database.AppDatabase
import `in`.umum.helper.Constants
import `in`.umum.helper.SharedPref
import `in`.umum.helper.SharedPref.get
import `in`.umum.helper.SharedPref.set
import `in`.umum.helper.Utils.CallbackDialog
import `in`.umum.helper.Utils.DialogUtils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(){
    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    private val prefs : SharedPreferences by lazy {
        SharedPref.defaultPrefs(context!!)
    }
    private lateinit var db : AppDatabase

    var phone_num : String? = null
    //First
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        phone_num = prefs[Constants.PHONE_NUMBER]
    }
    //Second
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    //Third
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile,container,false)

        //Testing
        if(savedInstanceState==null){

        }
        return view
    }
    //Fourth
    lateinit var title :String
    lateinit var content :String
    var pos :String ="OK"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        panduanPenggunaan.setOnClickListener { v->
            title = "Panduan Penggunaan"
            content = getString(R.string.content_term)
            pos = "OK"
            val dialogUtils = DialogUtils(activity!!).buildDialogInfo(title,content,pos,R.drawable.ic_help,object : CallbackDialog {
                override fun onPositiveClick(dialog: Dialog?) {
                    dialog?.dismiss()
                }

                override fun onNegativeClick(dialog: Dialog?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            dialogUtils.show()
        }
        syarat.setOnClickListener { v->
            title = "Syarat dan Ketentuan"
            content = getString(R.string.content_term)
            val dialogUtils = DialogUtils(activity!!).buildDialogInfo(title,content,pos,R.drawable.ic_syarat,object : CallbackDialog {
                override fun onPositiveClick(dialog: Dialog?) {
                    dialog?.dismiss()
                }

                override fun onNegativeClick(dialog: Dialog?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            dialogUtils.show()
        }
        hubungiKami.setOnClickListener { v->
            title = "Hubungi Kami"
            content = getString(R.string.content_term)
            val dialogUtils = DialogUtils(activity!!).buildDialogInfo(title,content,pos,R.drawable.ic_cs,object : CallbackDialog {
                override fun onPositiveClick(dialog: Dialog?) {
                    dialog?.dismiss()
                }

                override fun onNegativeClick(dialog: Dialog?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
            dialogUtils.show()
        }
        rate.setOnClickListener {v->

        }

    }
    //Fifth
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }



}