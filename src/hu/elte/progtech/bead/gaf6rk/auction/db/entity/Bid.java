/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.db.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author gaf6rk
 */
@Entity
public class Bid extends PersistentEntity{
    private static final long serialVersionUID = 1L;
    
    private String bidder;
    private Integer bidQ;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bidDate = new Date();
    @ManyToOne
    private Good good;

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public Integer getBidQ() {
        return bidQ;
    }

    public void setBidQ(Integer bidQ) {
        this.bidQ = bidQ;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return bidder + ", " + getBidQ() + "Ft, " + getBidDate();
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return bidder;
            case 1:
                return bidQ;
            case 2:
                return bidDate;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setBidder((String) value);
                break;
            case 1:
                setBidQ((Integer) value);
                break;
            case 2:
                setBidDate((Date) value);
                break;
        }
    }
    
    public static final String[] PROPERTY_NAMES = {"Tárgy neve", "Licitáló neve", "Dátum", "Licit"};
}
