/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.db.dao;

import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author gaf6rk
 */
public class DefaultDao<T extends PersistentEntity> implements GenericDao<T> {

    private final Class<T> CLASS;
    private final EntityManagerFactory EMF;

    public DefaultDao(Class<T> CLASS) {
        this.CLASS = CLASS;
        this.EMF = Persistence.createEntityManagerFactory("SaleMeetingPU");
    }

    @Override
    public void create(T entity) {
        
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(T entity) {
        
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        
        EntityManager entityManager = getEntityManager();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<T> findAll() {
        return findEntities(true, -1, -1);
    }

    @Override
    public T findById(Integer id) {
        return getEntityManager().find(CLASS, id);
    }

    private EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }
    
    private List<T> findEntities(boolean all, int firstResult, int maxResult) {
        
        EntityManager entityManager = getEntityManager();
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(CLASS));
        Query query = entityManager.createQuery(criteriaQuery);
        
        if (!all) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
        }
        
        return query.getResultList();
    }

}