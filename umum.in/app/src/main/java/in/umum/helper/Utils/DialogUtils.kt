package `in`.umum.helper.Utils

import android.app.Activity
import android.app.Dialog
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import `in`.umum.R


class DialogUtils(private val activity: Activity) {

    private fun buildDialogView(@LayoutRes layout: Int): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        dialog.setCancelable(false)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp

        return dialog
    }


    fun buildDialogInfo(@StringRes title: Int, @StringRes content: Int, @StringRes bt_text_pos: Int, @DrawableRes icon: Int, callback: CallbackDialog): Dialog {
        return buildDialogInfo(activity.getString(title), activity.getString(content), activity.getString(bt_text_pos), icon, callback)
    }

    // dialog info
    fun buildDialogInfo(title: String, content: String, bt_text_pos: String, @DrawableRes icon: Int, callback: CallbackDialog): Dialog {
        val dialog = buildDialogView(R.layout.dialog_info)

        (dialog.findViewById<View>(R.id.title) as TextView).text = title
        (dialog.findViewById<View>(R.id.content) as TextView).text = content
        (dialog.findViewById<View>(R.id.bt_positive) as Button).text = bt_text_pos
        (dialog.findViewById<View>(R.id.icon) as ImageView).setImageResource(icon)

        (dialog.findViewById<View>(R.id.bt_positive) as Button).setOnClickListener { callback.onPositiveClick(dialog) }
        return dialog
    }

    fun buildDialogWarning(@StringRes title: Int, @StringRes content: Int, @StringRes bt_text_pos: Int, @StringRes bt_text_neg: Int, @DrawableRes icon: Int, callback: CallbackDialog): Dialog {
        var _title: String? = null
        var _content: String? = null
        var _bt_text_neg: String? = null

        if (title != -1) _title = activity.getString(title)
        if (content != -1) _content = activity.getString(content)
        if (bt_text_neg != -1) _bt_text_neg = activity.getString(bt_text_neg)

        return buildDialogWarning(_title, _content, activity.getString(bt_text_pos), _bt_text_neg, icon, callback)
    }

    fun buildDialogWarning(@StringRes title: Int, @StringRes content: Int, @StringRes bt_text_pos: Int, @DrawableRes icon: Int, callback: CallbackDialog): Dialog {
        var _title: String? = null
        var _content: String? = null

        if (title != -1) _title = activity.getString(title)
        if (content != -1) _content = activity.getString(content)

        return buildDialogWarning(_title, _content, activity.getString(bt_text_pos), null, icon, callback)
    }

    // dialog warning
    fun buildDialogWarning(title: String?, content: String?, bt_text_pos: String, bt_text_neg: String?, @DrawableRes icon: Int, callback: CallbackDialog): Dialog {
        val dialog = buildDialogView(R.layout.dialog_warning)

        // if id = -1 view will gone
        if (title != null) {
            (dialog.findViewById<View>(R.id.title) as TextView).text = title
        } else {
            (dialog.findViewById<View>(R.id.title) as TextView).visibility = View.GONE
        }

        // if id = -1 view will gone
        if (content != null) {
            (dialog.findViewById<View>(R.id.content) as TextView).text = content
        } else {
            (dialog.findViewById<View>(R.id.content) as TextView).visibility = View.GONE
        }
        (dialog.findViewById<View>(R.id.bt_positive) as Button).text = bt_text_pos
        if (bt_text_neg != null) {
            (dialog.findViewById<View>(R.id.bt_negative) as Button).text = bt_text_neg
        } else {
            (dialog.findViewById<View>(R.id.bt_negative) as Button).visibility = View.GONE
        }
        (dialog.findViewById<View>(R.id.icon) as ImageView).setImageResource(icon)

        (dialog.findViewById<View>(R.id.bt_positive) as Button).setOnClickListener { callback.onPositiveClick(dialog) }

        (dialog.findViewById<View>(R.id.bt_negative) as Button).setOnClickListener { callback.onNegativeClick(dialog) }
        return dialog
    }
    // dialog Summary Order
    fun buildDialogSummary(title: String?, location: String?, jasa: String?, detail: String?, vendor: String?, note: String?, bt_text_pos: String, bt_text_neg: String?, @DrawableRes icon: Int, callback: CallbackDialog): Dialog {
        val dialog = buildDialogView(R.layout.dialog_sum_order)

        // if id = -1 view will gone
        if (title != null) {
            (dialog.findViewById<View>(R.id.title) as TextView).text = title
        } else {
            (dialog.findViewById<View>(R.id.title) as TextView).visibility = View.GONE
        }

        // if id = -1 view will gone
        if (location != null) {
            (dialog.findViewById<View>(R.id.SumTxtAlamat) as TextView).text = location
        } else {
            (dialog.findViewById<View>(R.id.SumTxtAlamat) as TextView).visibility = View.GONE
        }

        // if id = -1 view will gone
        if (jasa != null) {
            (dialog.findViewById<View>(R.id.SumTxtJasa) as TextView).text = jasa
        } else {
            (dialog.findViewById<View>(R.id.SumTxtJasa) as TextView).visibility = View.GONE
        }

        // if id = -1 view will gone
        if (detail != null) {
            (dialog.findViewById<View>(R.id.SumTxtDeskripsi) as TextView).text = detail
        } else {
            (dialog.findViewById<View>(R.id.SumTxtDeskripsi) as TextView).visibility = View.GONE
        }

        // if id = -1 view will gone
        if (vendor != null) {
            (dialog.findViewById<View>(R.id.SumTxtVendor) as TextView).text = vendor
        } else {
            (dialog.findViewById<View>(R.id.SumTxtVendor) as TextView).visibility = View.GONE
        }

        // if id = -1 view will gone
        if (note != null) {
            (dialog.findViewById<View>(R.id.SumTxtCatatan) as TextView).text = note
        } else {
            (dialog.findViewById<View>(R.id.SumTxtCatatan) as TextView).visibility = View.GONE
        }


        (dialog.findViewById<View>(R.id.bt_positive) as Button).text = bt_text_pos
        if (bt_text_neg != null) {
            (dialog.findViewById<View>(R.id.bt_negative) as Button).text = bt_text_neg
        } else {
            (dialog.findViewById<View>(R.id.bt_negative) as Button).visibility = View.GONE
        }
        (dialog.findViewById<View>(R.id.icon) as ImageView).setImageResource(icon)

        (dialog.findViewById<View>(R.id.bt_positive) as Button).setOnClickListener { callback.onPositiveClick(dialog) }

        (dialog.findViewById<View>(R.id.bt_negative) as Button).setOnClickListener { callback.onNegativeClick(dialog) }
        return dialog
    }

}
