/**
 * LeadComparator.java
 *
 * Compares two leads on id field.
 *
 * @author Ryan Goodpasture - ryan.goodpasture@gmail.com
 *
 */

package com.marketo;

import java.util.Comparator;

public class LeadComparator implements Comparator<Lead> {

    @Override
    public int compare (Lead o1, Lead o2) {
        //compare ids first, if equal compare entry dates
        if (o1.id < o2.id) {
            return -1;
        } else if (o1.id > o2.id) {
            return 1;
        } else {
            return 0;
        }
    }

}
