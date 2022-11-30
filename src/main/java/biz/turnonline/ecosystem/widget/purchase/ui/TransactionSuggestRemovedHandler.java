/*
 * Copyright 2008 Google Inc.
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
package biz.turnonline.ecosystem.widget.purchase.ui;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link TransactionSuggestRemovedEvent} events.
 * 
 * @param <T> the type being selected
 */
public interface TransactionSuggestRemovedHandler extends EventHandler {

  /**
   * Called when {@link TransactionSuggestRemovedEvent} is fired.
   * 
   * @param event the {@link TransactionSuggestRemovedEvent} that was fired
   */
  void onSelection( TransactionSuggestRemovedEvent event);
}
