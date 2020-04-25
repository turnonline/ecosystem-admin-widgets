package biz.turnonline.ecosystem.widget.shared.ui;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.LoaderType;
import gwt.material.design.client.constants.OverlayOption;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.incubator.client.infinitescroll.InfiniteScrollPanel;

/**
 * Overridden material InfinteScrollLoader due to black background color
 *
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class InfiniteScrollLoader
        extends gwt.material.design.incubator.client.infinitescroll.InfiniteScrollLoader
{
    private static MaterialLoader loader = new MaterialLoader( LoaderType.CIRCULAR);

    private InfiniteScrollPanel<?> parent;

    public InfiniteScrollLoader(String message) {
        super(message);

        OverlayOption overlayOption = OverlayOption.create();
        overlayOption.setBackgroundColor( Color.WHITE );
        loader.setOverlayOption( overlayOption );
    }

    /**
     * Will attach a loader to it's parent
     */
    public void show() {
        loader.setContainer(this);
        loader.show();

        parent.add(this);
    }

    /**
     * Will detach a loader to it's parent
     */
    public void hide() {
        loader.setContainer(this);
        loader.hide();

        removeFromParent();
    }

    /**
     * Will check if the loading indicator still attached to it's parent
     */
    public boolean isLoading() {
        return isAttached();
    }

    public void setParent(InfiniteScrollPanel parent) {
        this.parent = parent;
    }
}
