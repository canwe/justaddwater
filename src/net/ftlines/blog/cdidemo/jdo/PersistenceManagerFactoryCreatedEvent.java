package net.ftlines.blog.cdidemo.jdo;

import javax.jdo.PersistenceManagerFactory;

/**
 * NB: this file is derived from the open-source project hosted at https://github.com/42Lines/blog-cdidemo
 */
public class PersistenceManagerFactoryCreatedEvent {
  private final PersistenceManagerFactory pmf;

  public PersistenceManagerFactoryCreatedEvent(PersistenceManagerFactory pmf) {
    this.pmf = pmf;
  }

  public PersistenceManagerFactory getPersistenceManagerFactory() {
    return pmf;
  }

}
