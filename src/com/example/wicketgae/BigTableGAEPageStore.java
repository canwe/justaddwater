package com.example.wicketgae;

import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.pageStore.IDataStore;

import com.example.Repository;
import com.google.appengine.api.datastore.Blob;
import com.google.inject.Inject;

public class BigTableGAEPageStore implements IDataStore {

	private static final Logger log = Logger.getLogger(BigTableGAEPageStore.class.getName());

	@Inject
	private Repository<WicketPage> wpRepo;

	@Inject
	private WicketPageQueries wpQueries;

	private String applicationKey;

	public BigTableGAEPageStore(String applicationKey) {
		this.applicationKey = applicationKey;
	}

	@Override
	public void destroy() {
	}

	private WicketPage convertToWicketPage(int pageId, byte[] pageAsBytes) {
		WicketPage wp = new WicketPage();
		wp.setApplicationKey(applicationKey);
		wp.setPageId(pageId);
		wp.setSerializedPage(new Blob(pageAsBytes));
		return wp;
	}

	@Override
	public byte[] getData(String sessionId, int pageId) {
		log.info("getPage: [sessionId=" + sessionId + ", pageId=" + pageId + "]");

		WicketPage wp = wpQueries.findPage(sessionId, pageId);

		Blob page = wp.getSerializedPage();

		log.info("getPage RESULT: [sessionId=" + wp.getSessionId() + ", pageId=" + wp.getPageId() + "]");

		return page.getBytes();
	}

	@Override
	public boolean isReplicated() {
		return false;
	}

	@Override
	public void removeData(String sessionId) {
		final List<WicketPage> pages = wpQueries.findPages(sessionId);

		for (final WicketPage wp : pages) {
			wpRepo.runInTransaction(new Runnable() {
				@Override
				public void run() {
					wpRepo.delete(wp);
				}
			});
		}
		;

	}

	@Override
	public void removeData(String sessionId, int pageId) {
		final WicketPage wp = wpQueries.findPage(sessionId, pageId);
		wpRepo.runInTransaction(new Runnable() {
			@Override
			public void run() {
				wpRepo.delete(wp);
			}
		});
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] pageAsBytes) {
		log.info("storeData: [sessionId=" + sessionId + ", pageId=" + pageId + "]");
		final WicketPage wp = convertToWicketPage(pageId, pageAsBytes);
		wp.setSessionId(sessionId);
		wp.setApplicationKey(applicationKey);
		wpRepo.runInTransaction(new Runnable() {
			@Override
			public void run() {
				wpRepo.persist(wp);
			}
		});
	}

}
