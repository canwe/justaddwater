package net.ftlines.blog.cdidemo.jdo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * NB: this file is derived from the open-source project hosted at https://github.com/42Lines/blog-cdidemo
 */
public class PersistenceMangerFactoryProducer {

  @Inject
  Event<PersistenceManagerFactoryCreatedEvent> created;

  @Produces
  @ApplicationScoped
  public PersistenceManagerFactory create() {
    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    created.fire(new PersistenceManagerFactoryCreatedEvent(pmf));
    return pmf;
  }

  public void destroy(@Disposes PersistenceManagerFactory factory) {
    factory.close();
  }

}
