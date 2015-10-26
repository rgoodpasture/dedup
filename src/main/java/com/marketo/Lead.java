/*
 * Lead.java
 *
 * created on: 10/20/15 4:45 PM
 * Copyright(c) 2002-2015 Thetus Corporation.  All Rights Reserved.
 *                        www.thetus.com
 *
 * Use of copyright notice does not imply publication or disclosure.
 * THIS SOFTWARE CONTAINS CONFIDENTIAL AND PROPRIETARY INFORMATION CONSTITUTING VALUABLE TRADE SECRETS
 *  OF THETUS CORPORATION, AND MAY NOT BE:
 *  (a) DISCLOSED TO THIRD PARTIES;
 *  (b) COPIED IN ANY FORM;
 *  (c) USED FOR ANY PURPOSE EXCEPT AS SPECIFICALLY PERMITTED IN WRITING BY THETUS CORPORATION.
 */
package com.marketo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A Lead object is a representation of a marketing lead
 *
 * @author Ryan Goodpasture - rgoodpasture@thetus.com
 */
class Lead {

    public int    id;
    public String email;
    public String firstName;
    public String lastName;
    public String address;
    public Date   entryDate;

    public Lead (int id, String email, String firstName, String lastName, String address, String entryDate) throws ParseException {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        DateFormat format = new SimpleDateFormat("mm-dd-yy hh:MM:ss", Locale.ENGLISH);
        this.entryDate = format.parse(entryDate);
    }

    public String toString() {
        return "id:"+id+", email:"+email+", date:"+entryDate+", first:"+firstName+", last:"+lastName+", address:"+address;
    }

}
