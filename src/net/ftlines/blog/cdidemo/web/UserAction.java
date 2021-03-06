package net.ftlines.blog.cdidemo.web;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;

@ConversationScoped
public class UserAction implements Serializable {

  @Inject
  Conversation conversation;

  public UserAction begin(EntityManager em) {
    if (conversation.isTransient()) {
      conversation.begin();
    }
    if (em.getTransaction().isActive()) {
      em.getTransaction().commit();
    }
    em.setFlushMode(FlushModeType.COMMIT);
    return this;
  }

  public UserAction apply(EntityManager em) {
    EntityTransaction txn = em.getTransaction();
    txn.begin();
    try {
      em.flush();
    } catch (RuntimeException e) {
      txn.rollback();
      throw e;
    }
    txn.commit();
    return this;
  }

  public UserAction undo(EntityManager em) {
    em.clear();
    return this;
  }
}
