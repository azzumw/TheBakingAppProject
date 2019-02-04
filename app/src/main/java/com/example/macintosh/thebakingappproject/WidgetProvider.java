package com.example.macintosh.thebakingappproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_listview);

        Intent intent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.widgetListView, intent);
        // Instruct the widget manager to update the widget

        Intent intentUpdate = new Intent(context, WidgetProvider.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.updatebtn, pendingUpdate);


        views.setEmptyView(R.id.widgetListView,R.id.emptyview);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
