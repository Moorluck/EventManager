package be.bxl.eventsmanager

import android.app.Dialog
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TextView
import be.bxl.eventsmanager.api.HttpRequest
import be.bxl.eventsmanager.api.MemeParse
import be.bxl.eventsmanager.api.URLHelper
import be.bxl.eventsmanager.models.Meme
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*

/**
 * Implementation of App Widget functionality.
 */
class MemeWidget : AppWidgetProvider() {

    companion object {
        val ON_CLICK_MEME = "onClickMeme"
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.meme_widget)
        requestMeme(context, views, appWidgetId)

        views.setOnClickPendingIntent(R.id.img_widget_meme, getPendingSelfIntent(context, ON_CLICK_MEME, appWidgetId, appWidgetManager))
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getPendingSelfIntent(context: Context, action : String, appWidgetId: Int, appWidgetManager : AppWidgetManager): PendingIntent? {
        val intent = Intent(context, MemeWidget::class.java)
        intent.setAction(action)
        intent.putExtra("APP_WIDGET_ID", appWidgetId)
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun requestMeme(context: Context, views: RemoteViews, appWidgetId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URLHelper.URLMeme
            val request = HttpRequest.getJsonFromRequest(url)
            if (request != null) {
                val meme = MemeParse.parseJson(request)
                updateMemeUI(context, views, appWidgetId, meme)
            }
        }
    }


    private suspend fun updateMemeUI(context: Context, views : RemoteViews, appWidgetId: Int, meme: Meme) {
        withContext(Dispatchers.Main) {

            val awt : AppWidgetTarget = object : AppWidgetTarget(context, R.id.img_widget_meme, views, appWidgetId) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    super.onResourceReady(resource, transition)
                }
            }

            Glide.with(context)
                .asBitmap()
                .load(meme.imgUrl)
                .into(awt)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        if (ON_CLICK_MEME == intent?.action) {
            val views = RemoteViews(context.packageName, R.layout.meme_widget)
            requestMeme(context, views, intent.getIntExtra("APP_WIDGET_ID", 0))
        }
    }


}




