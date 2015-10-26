package com.marketo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    final static Logger logger = Logger.getLogger(Main.class.getName());

    private static void outputLeads(List<Lead> leads) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < leads.size(); i++) {
            sb.append(leads.get(i).toString()).append('\n');
        }

        logger.info(sb.toString());

    }

    public static void main(final String[] args) {

        List<Lead> leads = new ArrayList<Lead>();

        try {

            leads.add(new Lead(1,"sam@sam","Sam","Malone","1234 Smolsen", "05-12-1999 12:08:08"));
            leads.add(new Lead(1,"sam@sam","Sam","Malone","1234 Smolsen", "05-12-1999 12:08:08"));

            leads.add(new Lead(2,"norm@norm","Norm","Peterson","333 Distro", "06-02-2012 01:01:04"));

            leads.add(new Lead(3,"fras@fras","Frasier","Crane","76 Happy Lane", "07-02-2012 01:01:01"));

            leads.add(new Lead(32,"di@di","Diane","Chambers","1011 Human Circle", "08-02-2012 01:01:01"));
            leads.add(new Lead(33,"di@di","Diane","Chambersxxx","1011 Human Circle", "08-02-2012 01:01:01"));

            leads.add(new Lead(4,"clif@clif","Cliff","Clavin","712 Frak Ave", "09-02-2012 01:01:01"));

            leads.add(new Lead(5,"carla@tony","Carla","Tortelli","3633 Vera Road", "10-02-2012 01:01:01"));

            leads.add(new Lead(6,"woody@natural","Woody","Boyd","3224 Small Happy Planet", "11-02-2012 01:01:01"));

            leads.add(new Lead(1,"sammy@sam","Sam","Maloney","1234 Smolsen", "05-12-1999 12:08:08"));

            leads.add(new Lead(11,"sammy@sam","Sam","Maloney","1234 Smolsen", "05-30-1999 12:08:08"));

            leads.add(new Lead(2,"norm@norm","Norm","Norm","333 Distro", "06-02-2012 01:01:03"));

            leads.add(new Lead(3,"fras@fras","Frasier","Crane","76 Happy Camp", "07-02-2012 01:01:01"));

            leads.add(new Lead(2,"di@di","Diane","Chambery","1011 Human Circle", "08-02-2012 01:01:01"));

            leads.add(new Lead(4,"clif@clif","Cliff","Clavin","712 Fraky Ave", "09-13-2012 01:01:01"));

            leads.add(new Lead(5,"carla@carla","Carlax","Tortelliz","3633 Vera Katz", "10-02-2012 01:01:01"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        LeadDeduper leadDeduper = new LeadDeduper(logger);

        int leadsSize = leads.size();
        for (int i = 0; i < leadsSize; i++) {
            leadDeduper.addLead(leads.get(i));
        }

        final List<Lead> dedupedLeads = leadDeduper.getLeads();

        logger.info("\nDeduped leads\n");
        outputLeads(dedupedLeads);

    }
}
