/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.progtech.bead.gaf6rk.auction.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author gaf6rk
 */
@Entity
public class Good extends PersistentEntity {

    private static final long serialVersionUID = 1L;

    private String name;
    private String category;
    private Integer originalBid;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date closeDate;
    @OneToMany(mappedBy = "good")
    private List<Bid> bids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getOriginalBid() {
        return originalBid;
    }

    public void setOriginalBid(Integer originalBid) {
        this.originalBid = originalBid;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {

        if (bids == null) {
            bids = new ArrayList<>();
        }
        bids.add(bid);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
    }

    public Integer getMaxBid() {
        
        if (bids.isEmpty()) {
            return originalBid;
        }

        Bid lastbid = bids.get(bids.size() - 1);
        return lastbid.getBidQ();
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
        if (!(object instanceof Good)) {
            return false;
        }
        Good other = (Good) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return name;
            case 1:
                return category;
            case 2:
                return originalBid;
            case 3:
                return closeDate;
            case 4:
                return getMaxBid();
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setName((String) value);
                break;
            case 1:
                setCategory((String) value);
                break;
            case 2:
                setOriginalBid((Integer) value);
                break;
            case 3:
                setCloseDate((Date) value);
                break;
        }
    }

    public static final String[] PROPERTY_NAMES = {"Elnevezés", "Kategória",
        "Kezdő licitösszeg", "Lezárás", "Legmagasabb licit"};

    public static final Class<?>[] CLASS_TYPES = {String.class, String.class,
        Integer.class, Date.class, Integer.class};

}
