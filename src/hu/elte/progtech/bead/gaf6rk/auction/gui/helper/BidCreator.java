/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.gui.helper;

import hu.elte.progtech.bead.gaf6rk.auction.db.entity.Bid;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author gaf6rk
 */
public class BidCreator {
    
    private final static String BIDDER_TEXT = "Add meg a licitáló nevét!";
    private final static String BIDQ_TEXT = "Add meg licit összegét!";
    private final static String INVALID_NUMBER = "Érvénytelen szám formátum.";
    private final static String INPUT_ERROR = "Beviteli hiba";
    
    private Bid bid;
    private boolean invalid;
    
    private BidCreator() {
        bid = new Bid();
        invalid = false;
    }
    
    public static BidCreator preparation() {
        return new BidCreator();
    }
    
    public BidCreator withBidder() {
        String bidder = JOptionPane.showInputDialog(BIDDER_TEXT);
        
        if (bidder != null && !"".equals(bidder.trim())) {
            bid.setBidder(bidder.trim());
            invalid = false;
        } else {
            invalid = true;
        }
        
        return this;
    }
    
    public BidCreator withBidQ() {
        Integer number = null;
        
        do {
            String str = JOptionPane.showInputDialog(BIDQ_TEXT);
            try {
                number = Integer.parseInt(str);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, INVALID_NUMBER, INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } while (number == null);
        
        if (number < 100) {
            invalid = true;
            
            return this;
        }
        bid.setBidQ(number);
        
        return this;
    }
    
    public Bid call() {
        if(invalid) {
            return null;
        }
        
        bid.setBidDate(new Date());
        return bid;
    }
}
