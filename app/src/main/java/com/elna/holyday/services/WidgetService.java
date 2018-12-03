package com.elna.holyday.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.elna.holyday.ui.WidgetListAdapter;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetListAdapter(this);
    }
}
