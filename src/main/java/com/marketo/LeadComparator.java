/*
 * LeadComparator.java
 *
 * created on: 10/20/15 5:04 PM
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

import java.util.Comparator;

/**
 * TODO - add class description here
 *
 * @author [Your name here] - rgoodpasture@thetus.com
 */
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
