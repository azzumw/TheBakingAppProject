package com.example.macintosh.thebakingappproject;

import android.app.IntentService;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WidgetUpdateService extends IntentService {

    public final static String ACTION_UPDATE_APP_WIDGETS = "com.example.macintosh.thebakingappproject.widgetupdateservice.update_app_widget";
    public static final String ACTION_UPDATE_LIST_VIEW = "com.example.macintosh.thebakingappproject.widgetupdateservice.update_app_widget_list";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_APP_WIDGETS.equals(action)) {
                handleActionUpdateAppWidgets();
            }else if(ACTION_UPDATE_LIST_VIEW.equals(action)){
                handleActionUpdateListView();
            }
        }
    }

    private void handleActionUpdateListView(){

        //get the recipe data

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateAllAppWidget(this, appWidgetManager,appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
    }

    private void handleActionUpdateAppWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateAllAppWidget(this, appWidgetManager,appWidgetIds);
    }

    public static void startActionUpdateAppWidgets(Context context, boolean forListView) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        if(forListView){
            intent.setAction(ACTION_UPDATE_LIST_VIEW);
        }else {
            intent.setAction(ACTION_UPDATE_APP_WIDGETS);
        }

        context.startService(intent);
    }
}
