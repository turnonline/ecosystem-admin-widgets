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

import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a transaction removed event.
 */
public class TransactionSuggestRemovedEvent extends GwtEvent<TransactionSuggestRemovedHandler> {

  /**
   * Handler type.
   */
  private static Type<TransactionSuggestRemovedHandler> TYPE;

  /**
   * Fires a selection event on all registered handlers in the handler
   * manager.If no such handlers exist, this method will do nothing.
   * 
   * @param source the source of the handlers
   * @param transaction transaction to remove
   */
  public static void fire( HasTransactionSuggestRemovedHandlers source, Transaction transaction) {
    if (TYPE != null) {
      TransactionSuggestRemovedEvent event = new TransactionSuggestRemovedEvent(transaction);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<TransactionSuggestRemovedHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<>();
    }
    return TYPE;
  }

  private final Transaction transaction;

  public TransactionSuggestRemovedEvent( Transaction transaction )
  {
    this.transaction = transaction;
  }

  // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
  // field itself does not, so we have to do an unsafe cast here.
  @Override
  public final Type<TransactionSuggestRemovedHandler> getAssociatedType() {
    return TYPE;
  }

  public Transaction getTransaction()
  {
    return transaction;
  }

  @Override
  protected void dispatch( TransactionSuggestRemovedHandler handler) {
    handler.onSelection(this);
  }
}
