/**
 * Lead.java
 *
 * A Lead object is a representation of a marketing lead
 *
 */

package com.marketo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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
