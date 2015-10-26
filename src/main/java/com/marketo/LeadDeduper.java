/*
 * LeadDeduper.java
 *
 * created on: 10/20/15 4:54 PM
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * Deduplicates a list of leads, logging changes to a given logger
 *
 * @author Ryan Goodpasture - rgoodpasture@thetus.com
 */

public class LeadDeduper {

    private Logger logger;

    // TreeSet is sorted, faster lookup than List for large datasets
    TreeSet<Lead> leads = new TreeSet<Lead>(new LeadComparator());

    public LeadDeduper (Logger logger) {
        this.logger = logger;
    }

    //
    private void updateLeadData (Lead newLead, Lead existingLead) {

        if (0 >= existingLead.entryDate.compareTo(newLead.entryDate)) {

            String logMsg = "\nUpdating record " + existingLead + '\n';

            logMsg += "Source record " + newLead + "\n";

            if (!existingLead.entryDate.equals(newLead.entryDate)) {
                logMsg += "FIELD_NAME: \"entryDate\", OLD_VALUE: \"" + existingLead.entryDate +"\", NEW_VALUE: \"" + newLead.entryDate + "\"\n";
                existingLead.entryDate = newLead.entryDate;
            }

            if (!existingLead.firstName.isEmpty() && !existingLead.firstName.equals(newLead.firstName)) {
                logMsg += "FIELD_NAME: \"firstName\", OLD_VALUE: \"" + existingLead.firstName +"\", NEW_VALUE: \"" + newLead.firstName + "\"\n";
                existingLead.firstName = newLead.firstName;
            }

            if (!existingLead.lastName.isEmpty() && !existingLead.lastName.equals(newLead.lastName)) {
                logMsg += "FIELD_NAME: \"lastName\", OLD_VALUE: \"" + existingLead.lastName +"\", NEW_VALUE: \"" + newLead.lastName + "\"\n";
                existingLead.lastName = newLead.lastName;
            }

            if (!existingLead.address.isEmpty() && !existingLead.address.equals(newLead.address)) {
                logMsg += "FIELD_NAME: \"address\", OLD_VALUE: \"" + existingLead.address +"\", NEW_VALUE: \"" + newLead.address + "\"\n";
                existingLead.address = newLead.address;
            }

            if (!existingLead.email.isEmpty() && !existingLead.email.equals(newLead.email)) {
                logMsg += "FIELD_NAME: \"email\", OLD_VALUE: \"" + existingLead.email +"\", NEW_VALUE: \"" + newLead.email + "\"\n";
                existingLead.email = newLead.email;
            }

            logger.info(logMsg + "Output record " + existingLead);
        }
    }

    //Parhaps maintain a separate TreeSet sorted by email to speed this part up for large data sets.
    @SuppressWarnings("CastToConcreteClass")
    public Lead findEmailMatch (String email) {

        Iterator itr = leads.iterator();
        while (itr.hasNext()) {
            Lead existingLead = (Lead)itr.next();
            if (email.equals(existingLead.email)) {
                return existingLead;
            }
        }
        return null;
    }

    public void addLead (Lead lead) {

        if (!leads.contains(lead)) {

            //lead with same id does not exist, look for email match.
            Lead existingLead = findEmailMatch(lead.email);
            if (null != existingLead) {
                updateLeadData(lead, existingLead);
            } else  {
                logger.info("Adding new lead " + lead);
                leads.add(lead);
            }

        } else {

            Lead existingLead = leads.floor(lead);

            if (existingLead.email.equals(lead.email)) {
                updateLeadData(lead, existingLead);
            } else {
                //email doesn't match, make sure it doesn't exist in another record
                Lead matchingLead = findEmailMatch(lead.email);
                if (null == matchingLead) {
                    updateLeadData(lead, existingLead);
                } else {
                    // both email and id are found in different records. This is a problem because they both must be unique in the deduplicated dataset.
                    logger.warning("Conflict in record " + lead + "\nBoth id and email exist in different records. Assuming email match is correct.");

                    updateLeadData(lead, matchingLead);
                }
            }
        }
    }

    public List<Lead> getLeads() {
        return new ArrayList<Lead>(leads);
    }

}
