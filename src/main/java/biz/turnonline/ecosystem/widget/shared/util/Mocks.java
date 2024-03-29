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

package biz.turnonline.ecosystem.widget.shared.util;

import biz.turnonline.ecosystem.widget.shared.rest.payment.Bill;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Merchant;
import biz.turnonline.ecosystem.widget.shared.rest.payment.Transaction;
import biz.turnonline.ecosystem.widget.shared.rest.payment.TransactionBank;
import biz.turnonline.ecosystem.widget.shared.rest.payment.TransactionCategory;
import biz.turnonline.ecosystem.widget.shared.rest.search.GlobalItemType;
import biz.turnonline.ecosystem.widget.shared.rest.search.SearchGlobal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:pohorelec@turnonline.biz">Jozef Pohorelec</a>
 */
public class Mocks
{
    public static List<SearchGlobal> mockSearchGlobal()
    {
        List<SearchGlobal> list = new ArrayList<>();

        // invoices
        SearchGlobal i1 = new SearchGlobal();
        i1.setId( "1" );
        i1.setName( "VFA00001" );
        i1.setImageUrl( "https://www.chargify.com/blog/wp-content/uploads/2018/01/invoice-public-view.png" );
        i1.setType( GlobalItemType.INVOICE );
        list.add( i1 );

        SearchGlobal i2 = new SearchGlobal();
        i2.setId( "1" );
        i2.setName( "VFA00002" );
        i2.setImageUrl( "https://www.pandadoc.com/app/uploads/form-invoice.png" );
        i2.setType( GlobalItemType.INVOICE );
        list.add( i2 );

        // products

        SearchGlobal p1 = new SearchGlobal();
        p1.setId( "1" );
        p1.setName( "Mac Book Pro" );
        p1.setImageUrl( "https://images-na.ssl-images-amazon.com/images/I/51FQpz-zY1L._SX679_.jpg" );
        p1.setType( GlobalItemType.PRODUCT );
        list.add( p1 );

        SearchGlobal p2 = new SearchGlobal();
        p2.setId( "1" );
        p2.setName( "IPhone S5" );
        p2.setImageUrl( "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone/xr/iphone-xr-white-select-201809?wid=940&hei=1112&fmt=png-alpha&qlt=80&.v=1551226036668" );
        p2.setType( GlobalItemType.PRODUCT );
        list.add( p2 );

        // contacts
        SearchGlobal c1 = new SearchGlobal();
        c1.setId( "1" );
        c1.setName( "HP enterprise" );
        c1.setImageUrl( "https://upload.wikimedia.org/wikipedia/commons/4/46/Hewlett_Packard_Enterprise_logo.svg" );
        c1.setType( GlobalItemType.CONTACT );
        list.add( c1 );

        SearchGlobal c2 = new SearchGlobal();
        c2.setId( "1" );
        c2.setName( "IBM" );
        c2.setImageUrl( "https://www.cbronline.com/wp-content/uploads/2016/07/ibm.png" );
        c2.setType( GlobalItemType.CONTACT );
        list.add( c2 );

        // orders

        SearchGlobal o1 = new SearchGlobal();
        o1.setId( "1" );
        o1.setName( "123232353452" );
        o1.setImageUrl( "https://www.chargify.com/blog/wp-content/uploads/2018/01/invoice-public-view.png" );
        o1.setType( GlobalItemType.ORDER );
        list.add( o1 );

        SearchGlobal o2 = new SearchGlobal();
        o2.setId( "1" );
        o2.setName( "334543523453345" );
        o2.setType( GlobalItemType.ORDER );
        list.add( o2 );

        return list;
    }

    public static Transaction transaction()
    {
        Transaction transaction = new Transaction();

        transaction.setTransactionId( 5662784121470976L );
        transaction.setAmount( 959.24 );
        transaction.setBalance( 100000D );
        transaction.setCredit( false );
        transaction.setCurrency( "EUR" );
        transaction.setReference( "/VS1100012021/SS/KS" );
        transaction.setStatus( "COMPLETED" );
        transaction.setType( "TRANSFER" );
        transaction.setCompletedAt( new Date() );

        TransactionBank bankAccount = new TransactionBank();
        bankAccount.setCode( "REVO" );
        bankAccount.setIban( "LT04 1234 5678 9812 4321" );
        transaction.setBankAccount( bankAccount );

        Merchant merchant = new Merchant();
        merchant.setCategory( "Gas" );
        merchant.setName( "To Daňový úrad Trnava DPH" );
        merchant.setCity( "Bratislava" );
        transaction.setMerchant( merchant );

        Bill bill = new Bill();
        bill.setInvoice( 1L );
        bill.setOrder( 2L );
        transaction.setBill( bill );

        TransactionCategory transactionCategory1 = new TransactionCategory();
        transactionCategory1.setColor( "#cc0000" );
        transactionCategory1.setName( "Gas" );
        transactionCategory1.setPropagate( true );

        TransactionCategory transactionCategory2 = new TransactionCategory();
        transactionCategory2.setColor( "#00cc00" );
        transactionCategory2.setName( "Money loundry" );
        transactionCategory2.setPropagate( true );

        TransactionCategory transactionCategory3 = new TransactionCategory();
        transactionCategory3.setColor( "#0000cc" );
        transactionCategory3.setName( "Tax prepayment" );
        transactionCategory3.setPropagate( true );

        transaction.setCategories( Arrays.asList(transactionCategory1, transactionCategory2, transactionCategory3) );

        return transaction;
    }
}
