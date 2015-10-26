/*
 * LeadDeduper.java
 *
 * Maintains a list of leads. Allows adding leads. Added leads will be deduplicated.
 * Changes are logged to a given logger.
 *
 * @author Ryan Goodpasture - ryan.goodpasture@gmail.com
 *
 */
package com.marketo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

public class LeadDeduper {

    private Logger logger;

    // TreeSet is sorted, faster lookup than List for large datasets
    TreeSet<Lead> leads = new TreeSet<Lead>(new LeadComparator());

    public LeadDeduper (Logger logger) {
        this.logger = logger;
    }

    /**
     * Update an existing lead in the set with data from a new lead record.
     * The update will only occur if the new lead has a later date or the same date as the existing lead.
     * The new lead is assumed to have been matched with the existing lead.
     * All changes are logged with the logger, both on a record and field level.
     *
     * @param newLead      The new lead
     * @param existingLead The lead to be updated.
    **/

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


    /**
     * Search the set of existing leads for one that has the given email.
     * Note: It might be possible to speed this up by maintaining a separate TreeSet sorted by email.
     *
     * @param email      The email address to match
     * @return The Lead which matches the given email, or null if no match was found.
     **/
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


    /**
     * Incorporate a new lead into the current set.
     * Try to match an existing lead on id and email fields.
     * If a match is found, update the existing lead.
     * If no match is found, add a new lead.
     *
     * @param lead      The lead to add to the set
     *
     **/
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

    /**
     * Return the current set of leads as a list
     *
     * @return an ArrayList of {@link Lead} objects
     */
    public List<Lead> getLeads() {
        return new ArrayList<Lead>(leads);
    }

}
