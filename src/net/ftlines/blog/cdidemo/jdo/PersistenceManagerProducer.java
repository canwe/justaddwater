package net.ftlines.blog.cdidemo.jdo;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * NB: this file is derived from the open-source project hosted at https://github.com/42Lines/blog-cdidemo
 */
public class PersistenceManagerProducer {
  @Inject
  PersistenceManagerFactory pmf;

  @Produces
  @ConversationScoped
  public PersistenceManager create() {
    return pmf.getPersistenceManager();
  }

  public void destroy(@Disposes PersistenceManager pm) {
	  if (!pm.isClosed()) {
	      pm.close();
	  }
  }

}
