package com.example.web;

import com.example.Repository;
import com.example.jdo.PersistenceManagerFilter;
import com.example.wicketgae.JdoWicketPageQueries;
import com.example.wicketgae.JdoWicketPageRepository;
import com.example.wicketgae.WicketPage;
import com.example.wicketgae.WicketPageQueries;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

/**
 * This Guice module sets up the bindings used in this Wicket application, including the
 * JDO PersistenceManager.
 */
public class GuiceModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        // Enable per-request-thread PersistenceManager injection.
        install(new PersistenceManagerFilter.GuiceModule());

        // Business object bindings go here.
        
		// Wicket Page Store
		bind(WicketPageQueries.class).to(JdoWicketPageQueries.class);
		bind(new TypeLiteral<Repository<WicketPage>>() {}).to(JdoWicketPageRepository.class);

    }
}
