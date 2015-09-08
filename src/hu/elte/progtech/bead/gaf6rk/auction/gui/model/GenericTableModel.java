/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.gui.model;

import hu.elte.progtech.bead.gaf6rk.auction.db.dao.*;
import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gaf6rk
 */
public class GenericTableModel<T extends PersistentEntity> extends AbstractTableModel {

    private List<T> items;
    private final GenericDao<T> DAO;
    private final String PROPERTY_NAMES[];
    private final Class<?>[] COLUMN_TYPES;

    public GenericTableModel(final GenericDao<T> DAO, final String[] PROPERTY_NAMES, final Class<?>[] COLUMN_TYPES) {
        this.DAO = DAO;
        this.items = new ArrayList<>();
        this.PROPERTY_NAMES = PROPERTY_NAMES;
        this.COLUMN_TYPES = COLUMN_TYPES;
    }

    @Override
    public int getRowCount() {
        items = DAO.findAll();
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return PROPERTY_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        items.get(rowIndex).set(columnIndex, aValue);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return PROPERTY_NAMES[column];
    }

    public T getEntity(int rowIndex) {
        return rowIndex < items.size() ? items.get(rowIndex) : null;
    }
    
    public void addEntity(T item) {
        DAO.create(item);
        fireTableDataChanged();
    }

    public void removeEntity(int rowIndex) {
        
        GenericDao<Bid> bidDao = DaoManager.getInstance().getBidDao();
        Good entity = (Good) items.get(rowIndex);
        
        for(Bid b : entity.getBids()) {
            bidDao.delete(b);
        }
        
        items.remove(rowIndex);
        fireTableDataChanged();
        DAO.delete((T) entity);
    }

}
