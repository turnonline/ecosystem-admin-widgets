/*
 *  Copyright (c) 2020 TurnOnline.biz s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package biz.turnonline.ecosystem.widget.shared.event;

import com.google.gwt.event.shared.GwtEvent;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;

import javax.annotation.Nullable;

/**
 * This event is only a workaround how to deliver an associated ID to the uploader.
 * It's needed to be delivered before onAttach event of the {@link MaterialFileUploader}.
 * So make sure this event will be fired right before event that will render widget with uploader component.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class UploaderAssociatedIdChangeEvent
        extends GwtEvent<UploaderAssociatedIdChangeEventHandler>
{
    public static Type<UploaderAssociatedIdChangeEventHandler> TYPE = new Type<>();

    private final Long id;

    public UploaderAssociatedIdChangeEvent( @Nullable Long id )
    {
        this.id = id;
    }

    public Type<UploaderAssociatedIdChangeEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    /**
     * Returns the ID as an identification of a record that's being associated with the uploaded data.
     * If {@code null} it represents a new record.
     *
     * @return the associated ID or {@code null}
     */
    public Long getId()
    {
        return id;
    }

    protected void dispatch( UploaderAssociatedIdChangeEventHandler handler )
    {
        handler.onUploaderAssociatedIdChange( this );
    }
}
