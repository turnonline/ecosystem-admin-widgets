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

package biz.turnonline.ecosystem.widget.myaccount;

import biz.turnonline.ecosystem.widget.myaccount.presenter.MyAccountPresenter;
import biz.turnonline.ecosystem.widget.myaccount.presenter.SettingsPresenter;
import org.ctoolkit.gwt.client.presenter.PresenterController;

import javax.inject.Inject;

/**
 * App controller maps place to a presenter.
 * Set Presenter's place by {@link org.ctoolkit.gwt.client.presenter.Presenter#setPlace(Class)}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class MyAccountController
        extends PresenterController
{
    @Inject
    public MyAccountController( MyAccountPresenter p1, SettingsPresenter p2 )
    {
        super( p1, p2 );
    }
}
