/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.progtech.bead.gaf6rk.auction.gui.model;

import hu.elte.progtech.bead.gaf6rk.auction.db.dao.*;
import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author gaf6rk
 */
public class CombinationListModel extends AbstractListModel<Bid> {

    private List<Bid> bids;
    private Good good;
    private final GenericDao<Good> DAO;

    public CombinationListModel() {
        bids = new ArrayList<>();
        DAO = DaoManager.getInstance().getGoodDao();
    }

    @Override
    public int getSize() {
        return bids.size();
    }

    @Override
    public Bid getElementAt(int index) {
        return (bids.size() < index) ? null : bids.get(index);
    }

    public void add(Bid bid) {

        if (bid == null || new Date().after(good.getCloseDate())) {
            return;
        }
        
        List<Bid> jpaBids = good.getBids();
        
        if (bid.getBidQ() < jpaBids.get(jpaBids.size() - 1).getBidQ()) {
            return;
        }

        good.addBid(bid);
        DAO.update(good);
        fireIntervalAdded(this, bids.size() - 1, bids.size() - 1);

    }

    public void read(Good good) {
        if (good != null) {
            this.good = good;
            bids = good.getBids();
            fireContentsChanged(this, 0, bids.size() - 1);
        }
    }

}
