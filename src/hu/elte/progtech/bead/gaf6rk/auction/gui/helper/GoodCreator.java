/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.progtech.bead.gaf6rk.auction.gui.helper;

import hu.elte.progtech.bead.gaf6rk.auction.db.entity.Good;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gaf6rk
 */
public class GoodCreator {

    private final static String NAME_TEXT = "Add meg a termék nevét!";
    private final static String CATEGORY_TEXT = "Add meg a termék kategóriáját!";
    private final static String BID_TEXT = "Mi a kezdő licit?";
    private final static String DATE_TEXT = "Mikor zárják le? (éééé/hh/nn)";
    private final static String INVALID_DATE = "Érvénytelen dátum formátum.";
    private final static String INVALID_NUMBER = "Érvénytelen szám formátum.";
    private final static String INPUT_ERROR = "Beviteli hiba";

    private Good good;
    private boolean invalid;

    private GoodCreator() {
        good = new Good();
        invalid = true;
    }

    public static GoodCreator preparation() {
        return new GoodCreator();
    }

    public GoodCreator withName() {
        String name = JOptionPane.showInputDialog(NAME_TEXT);

        if (name != null && !"".equals(name.trim())) {
            good.setName(name.trim());
            invalid = false;
        } else {
            invalid = true;
        }
        return this;
    }

    public GoodCreator withCategory() {
        String category = JOptionPane.showInputDialog(CATEGORY_TEXT);

        if (category != null && !"".equals(category.trim())) {
            good.setCategory(category.trim());
            invalid = false;
        } else {
            invalid = true;
        }
        
        return this;
    }
    
    public GoodCreator withOriginalBid() {
        Integer bid = null;
        
        do {
            String str = JOptionPane.showInputDialog(BID_TEXT);
            
            try {
                bid = Integer.parseInt(str);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, INVALID_NUMBER, INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } while (bid == null);
        
        good.setOriginalBid(bid);
        
        return this;
    }
    
    public GoodCreator withCloseDate() {
        
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String str = JOptionPane.showInputDialog(DATE_TEXT);
        boolean invalid = false;
        
        try {
            Date input = df.parse(str);
            
            if (new Date().after(input)) {
                invalid = true;
            } else {
                good.setCloseDate(input);
            }
        } catch (ParseException ex) {
            invalid = true;
            Logger.getLogger(GoodCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (invalid) {
            JOptionPane.showMessageDialog(null, INVALID_DATE, INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            this.invalid = invalid;
        }
        
        return this;
    }
    
    public Good build() {
        return invalid ? null : good;
    }

}
