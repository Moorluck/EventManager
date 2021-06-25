package be.bxl.eventsmanager

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.RemoteViews
import be.bxl.eventsmanager.api.HttpRequest
import be.bxl.eventsmanager.api.MemeParse
import be.bxl.eventsmanager.api.URLHelper
import be.bxl.eventsmanager.models.Meme
import com.bumptech.glide.Glide
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

            val views = RemoteViews(context.packageName, R.layout.meme_widget)

            requestMeme(context, views, appWidgetId)

            views.setOnClickPendingIntent(R.id.img_widget_meme, getPendingSelfIntent(context, ON_CLICK_MEME, appWidgetIds))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun getPendingSelfIntent(context: Context, action: String, appWidgetIds: IntArray): PendingIntent? {
        val intent = Intent(context, MemeWidget::class.java)
        intent.setAction(action)
        intent.putExtra("appWidgetIds", appWidgetIds)
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun requestMeme(context: Context, views: RemoteViews, appWidgetId: Int) {

        val requestScope = CoroutineScope(Dispatchers.IO).launch {
            val url = URLHelper.URLMeme
            val request = HttpRequest.getJsonFromRequest(url)
            if (request != null) {
                val meme = MemeParse.parseJson(request)
                updateMemeUI(context, views, appWidgetId, meme)
            }
        }

    }

    private suspend fun updateMemeUI(
        context: Context,
        views: RemoteViews,
        appWidgetId: Int,
        meme: Meme
    ) {
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


        if (ON_CLICK_MEME == intent?.action) {
            Log.d("CLICK", "CLICK")
            val appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(
                ComponentName(context.packageName, this::class.java.name)
            )
            val views = RemoteViews(context.packageName, R.layout.meme_widget)

            requestMeme(context, views, appWidgetId[0])
        }
        else {
            super.onReceive(context, intent)
        }
    }


}




