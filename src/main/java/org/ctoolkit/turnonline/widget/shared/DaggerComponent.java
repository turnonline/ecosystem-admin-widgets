package org.ctoolkit.turnonline.widget.shared;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.place.shared.PlaceHistoryHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface DaggerComponent
{
    PlaceHistoryHandler placeHistoryHandler();

    ActivityManager getActivityManager();
}
