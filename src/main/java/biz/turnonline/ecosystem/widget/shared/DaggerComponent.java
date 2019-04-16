package biz.turnonline.ecosystem.widget.shared;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.place.shared.PlaceHistoryHandler;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface DaggerComponent
{
    AppEventBus getEventBus();

    PlaceHistoryHandler placeHistoryHandler();

    ActivityManager getActivityManager();
}
