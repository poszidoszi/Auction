/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.db.dao;

import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;
import java.util.List;

/**
 *
 * @author gaf6rk
 */
public interface GenericDao<T extends PersistentEntity> {

    public void create(T entity);

    public void update(T entity);

    public void delete(T entity);

    public List<T> findAll();

    public T findById(Integer id);

}
