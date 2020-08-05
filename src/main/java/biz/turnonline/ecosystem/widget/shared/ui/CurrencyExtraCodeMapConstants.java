/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package biz.turnonline.ecosystem.widget.shared.ui;

import com.google.gwt.i18n.client.Constants;

import java.util.Map;

/**
 * CurrencyExtraCodeMapConstants provide a interface to access data constrained in
 * CurrencyExtraCodeMapConstants.properties file.
 *
 * It is taken from CurrencyExtra.properties values - added currencyMap field and this class due to fact that CurrencyExtra constants
 * does not have corresponding class
 */
public interface CurrencyExtraCodeMapConstants
        extends Constants {
  Map<String, String> currencyMap();
}
