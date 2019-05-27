/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2018-10-08 17:45:39 UTC)
 * on 2019-03-14 at 19:18:29 UTC
 * Modify at your own risk.
 */

package biz.turnonline.ecosystem.widget.shared.rest.billing;

import java.util.Date;

/**
 * Model definition for EventBegin.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the TurnOnline.biz Product Billing. For a detailed
 * explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings( "javadoc" )
public final class EventBegin
{

    /**
     * The value may be {@code null}.
     */
    
    private Integer from;

    /**
     * The value may be {@code null}.
     */
    
    private Date on;

    /**
     * The value may be {@code null}.
     */
    
    private Boolean show;

    /**
     * The value may be {@code null}.
     */
    
    private Integer to;

    /**
     * @return value or {@code null} for none
     */
    public Integer getFrom()
    {
        return from;
    }

    /**
     * @param from from or {@code null} for none
     */
    public EventBegin setFrom( Integer from )
    {
        this.from = from;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Date getOn()
    {
        return on;
    }

    /**
     * @param on on or {@code null} for none
     */
    public EventBegin setOn( Date on )
    {
        this.on = on;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Boolean getShow()
    {
        return show;
    }

    /**
     * @param show show or {@code null} for none
     */
    public EventBegin setShow( Boolean show )
    {
        this.show = show;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Integer getTo()
    {
        return to;
    }

    /**
     * @param to to or {@code null} for none
     */
    public EventBegin setTo( Integer to )
    {
        this.to = to;
        return this;
    }

}