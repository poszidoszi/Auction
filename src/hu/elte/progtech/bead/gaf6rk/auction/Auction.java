/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction;

import hu.elte.progtech.bead.gaf6rk.auction.gui.AuctionFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author gaf6rk
 */
public class Auction {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new AuctionFrame().setVisible(true);
            }
        });
    }
}
