package `in`.umum.helper.Utils

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.ColorRes
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast


import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import `in`.umum.R

object Tools {

    val isLolipopOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /**
     * For device info parameters
     */
    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                model
            } else {
                "$manufacturer $model"
            }
        }

    val androidVersion: String
        get() = Build.VERSION.RELEASE + ""

    fun needRequestPermission(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    fun setSystemBarColor(act: Activity, color: Int) {
        if (isLolipopOrHigher) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = color
            }
        }
    }

    fun setSystemBarColor(act: Activity, color: String) {
        setSystemBarColor(act, Color.parseColor(color))
    }

    fun setSystemBarColorDarker(act: Activity, color: String) {
        setSystemBarColor(act, colorDarker(Color.parseColor(color)))
    }

    fun systemBarLolipop(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = act.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = act.resources.getColor(R.color.colorPrimaryDark)
        }
    }

    fun rateAction(activity: Activity) {
        val uri = Uri.parse("market://details?id=" + activity.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            activity.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.packageName)))
        }

    }

    fun showDialogAbout(activity: Activity) {
        val dialog = DialogUtils(activity).buildDialogInfo(R.string.title_about, R.string.content_about, R.string.OK, R.drawable.img_about, object : CallbackDialog {
            override fun onPositiveClick(dialog: Dialog) {
                dialog.dismiss()
            }

            override fun onNegativeClick(dialog: Dialog) {
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    fun getVersionCode(ctx: Context): Int {
        try {
            val manager = ctx.packageManager
            val info = manager.getPackageInfo(ctx.packageName, 0)
            return info.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            return -1
        }

    }

    fun getVersionNamePlain(ctx: Context): String {
        try {
            val manager = ctx.packageManager
            val info = manager.getPackageInfo(ctx.packageName, 0)
            return info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            return ctx.getString(R.string.version_unknown)
        }

    }

    fun getFormattedDate(dateTime: Long?): String {
        val newFormat = SimpleDateFormat("MMMM dd, yyyy hh:mm")
        return newFormat.format(Date(dateTime!!))
    }

    fun getFormattedDateSimple(dateTime: Long?): String {
        val newFormat = SimpleDateFormat("MMM dd, yyyy")
        return newFormat.format(Date(dateTime!!))
    }

    fun displayImageOriginal(ctx: Context, img: ImageView, url: String) {
        try {
            Glide.with(ctx).load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img)
        } catch (e: Exception) {
        }

    }

    fun displayImageThumbnail(ctx: Context, img: ImageView, url: String, thumb: Float) {
        try {
            Glide.with(ctx).load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .thumbnail(thumb)
                    .into(img)
        } catch (e: Exception) {

        }

    }


    fun colorDarker(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.9f // value component
        return Color.HSVToColor(hsv)
    }

    fun colorBrighter(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] /= 0.8f // value component
        return Color.HSVToColor(hsv)
    }
    fun getWidthScreen(activity: Activity):Float{
        val display = activity.windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels.toFloat()
        return screenWidth
    }
    fun getWidthScreenWindow(windowManager: WindowManager):Float{
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels.toFloat()
        return screenWidth
    }
    fun getGridSpanCount(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels.toFloat()
        val cellWidth = activity.resources.getDimension(R.dimen.item_product_width)
        return Math.round(screenWidth / cellWidth)
    }

    fun getFeaturedNewsImageHeight(activity: Activity): Int {
        val w_ratio = 2f
        val h_ratio = 1f // we use 2:1 ratio
        val display = activity.windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        val screenWidth = (displayMetrics.widthPixels - 10).toFloat()
        val resHeight = screenWidth * h_ratio / w_ratio
        return Math.round(resHeight)
    }

    fun tintMenuIcon(context: Context, item: MenuItem, @ColorRes color: Int) {
        val normalDrawable = item.icon
        val wrapDrawable = DrawableCompat.wrap(normalDrawable)
        DrawableCompat.setTint(wrapDrawable, context.resources.getColor(color))

        item.icon = wrapDrawable
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getBitmap(file: File): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

    fun getVersionName(ctx: Context): String {
        try {
            val manager = ctx.packageManager
            val info = manager.getPackageInfo(ctx.packageName, 0)
            return ctx.getString(R.string.app_version) + " " + info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            return ctx.getString(R.string.version_unknown)
        }

    }

    fun copyToClipboard(context: Context, data: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("clipboard", data)
        clipboard.primaryClip = clip
        Toast.makeText(context, R.string.msg_copied_clipboard, Toast.LENGTH_SHORT).show()
    }


    fun getDeviceID(context: Context): String? {
        var deviceID: String? = Build.SERIAL
        if (deviceID == null || deviceID.trim { it <= ' ' }.isEmpty() || deviceID == "unknown") {
            try {
                deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (e: Exception) {
            }

        }
        return deviceID
    }

}