package com.example.jdo;

import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.example.Repository;
import com.google.inject.Provider;

/**
 * This base class implements the full Repository interface for managing persistent JDO entities.
 *
 * @param <T> the persistent entity type
 */
public abstract class JdoRepository<T> implements Repository<T>
{
    private final Class<T> clazz;
    private final Provider<PersistenceManager> pmProvider;
    private static final Logger log = Logger.getLogger(JdoRepository.class.getName());

    protected JdoRepository(Class<T> clazz, Provider<PersistenceManager> pmProvider)
    {
        this.clazz = clazz;
        this.pmProvider = pmProvider;
    }

    public T get(Object key)
    {
        PersistenceManager pm = pmProvider.get();
        try
        {
            return pm.getObjectById(clazz, key);
        }
        catch (JDOObjectNotFoundException e)
        {
            return null;
        }
    }

    public void persist(T entity)
    {
        pmProvider.get().makePersistent(entity);
    }

    public void delete(T entity)
    {
        pmProvider.get().deletePersistent(entity);
    }

    public void runInTransaction(Runnable block)
    {
    	log.info("runInTransaction: start.");
    	PersistenceManager pm = pmProvider.get();
    	if (null == pm || pm.isClosed()) {
    		log.info("runInTransaction: pm is not set or closed. end.");
    		return;
    	}
        Transaction tx = pm.currentTransaction();
        try
        {
            tx.begin();
            block.run();
            tx.commit();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            log.info("runInTransaction: end.");
        }
    }
}