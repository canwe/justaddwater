package com.example.wicketgae;

import javax.jdo.PersistenceManager;

import com.example.jdo.JdoRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JdoWicketPageRepository extends JdoRepository<WicketPage> {

	@Inject
	public JdoWicketPageRepository(Provider<PersistenceManager> pmProvider) {
		super(WicketPage.class, pmProvider);
	}

}
