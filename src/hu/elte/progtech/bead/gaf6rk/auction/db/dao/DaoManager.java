/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.db.dao;

import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;

/**
 *
 * @author gaf6rk
 */
public class DaoManager {
    
    private GenericDao<Good> goodDao;
    private GenericDao<Bid> bidDao;
    
    public GenericDao<Good> getGoodDao() {
        if (goodDao == null) {
            goodDao = new DefaultDao<>(Good.class);
        }
        return goodDao;
    }

    public GenericDao<Bid> getBidDao() {
        if (bidDao == null) {
            bidDao = new DefaultDao<>(Bid.class);
        }
        return bidDao;
    }
    
    private DaoManager() {
    }

    public static DaoManager getInstance() {
        return DaoManagerHolder.INSTANCE;
    }

    private static class DaoManagerHolder {

        private static final DaoManager INSTANCE = new DaoManager();
    }
}
