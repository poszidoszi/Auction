/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.gui;

import hu.elte.progtech.bead.gaf6rk.auction.db.dao.DaoManager;
import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;
import static hu.elte.progtech.bead.gaf6rk.auction.gui.FrameConstants.*;
import hu.elte.progtech.bead.gaf6rk.auction.gui.model.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author gaf6rk
 */
public class CombinationPanel extends JPanel {

    private final JFrame PARENT;

    private CombinationListModel model;
    
    private JList<Bid> items;
    private Good good;
    private JLabel maxBidLabel;

    public CombinationPanel(JFrame parent) {
        PARENT = parent;
        initPanel();
        initLabel();
        initList();
        fill();
    }
    
    private void fill() {
        add(maxBidLabel, BorderLayout.PAGE_START);
        add(items, BorderLayout.CENTER);
    }
    
    private void initList() {
        model = new CombinationListModel();
        items = new JList<>(model);
    }
    
    private void initPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(COMBINATION_PANEL_WIDTH, COMBINATION_PANEL_HEIGHT));
    }

    public void setGood(Good good) {
        if (good != null) {
            this.good = good;
            updateMaxBid();
            model.read(good);
        } else {
            this.good = null;
            updateMaxBid();
        }
    }
    
    private void updateMaxBid() {
        int sum = 0;
        if (good != null) {
            sum = good.getMaxBid();
        }
        maxBidLabel.setText(COMBINATION_SUM_LABEL_TEXT + sum + COMBINATION_VALUE_TEXT);
    }
    
    public void addBid(Bid bid) {
        
        if (good == null || bid == null) {
            return;
        }
        
        good.getBids().add(bid);
        bid.setGood(good);
        DaoManager.getInstance().getGoodDao().update(good);
    }
    
    private void initLabel() {
        maxBidLabel = new JLabel(COMBINATION_SUM_LABEL_TEXT + "0" + COMBINATION_VALUE_TEXT);
    }
      
}