package com.example.jdo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class PersistenceManagerFilter implements Filter
{
	private static final Logger logger = Logger.getLogger(PersistenceManagerFilter.class.getName());
	
    private static final ThreadLocal<PersistenceManager> pm = new ThreadLocal<PersistenceManager>();

    private PersistenceManagerFactory pmf;

    public void init(FilterConfig filterConfig) throws ServletException
    {
        logger.info("Creating PersistenceManagerFactory");
        pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
    	logger.info("doFilter: start");
        try
        {
        	boolean jsessionid = false;
        	if (request instanceof HttpServletRequest) {
        		HttpServletRequest httpRequest = (HttpServletRequest) request;
        	}
        	if (!jsessionid) {
                pm.set(pmf.getPersistenceManager());
                chain.doFilter(request, response);
        	} else {
        		chain.doFilter(request, response);
        	}
        }
        finally
        {
            pm.get().close();
            logger.info("doFilter: finally end");
        }
    }

    public void destroy()
    {
        logger.info("Closing PersistenceManagerFactory");
        pmf.close();
    }

    /**
     * This module binds the JDO {@link javax.jdo.PersistenceManager} interface to the provider that allows the
     * implementation to be injected as Provider&lt;PersistenceManager&gt;.
     */
    public static class GuiceModule extends AbstractModule
    {
        @Override
        protected void configure()
        {
            bind(PersistenceManager.class).toProvider(new Provider<PersistenceManager>()
            {
                public PersistenceManager get()
                {
                    return PersistenceManagerFilter.pm.get();
                }
            });
        }
    }
}
