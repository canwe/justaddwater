package com.example.wicketgae;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.example.jdo.JdoQueries;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JdoWicketPageQueries extends JdoQueries<WicketPage> implements WicketPageQueries {

	private static final Logger log = Logger.getLogger(JdoWicketPageQueries.class.getName());
	
	@Inject
	public JdoWicketPageQueries(Provider<PersistenceManager> pmProvider) {
		super(WicketPage.class, pmProvider);
	}

	@Override
	public boolean containesPage(String sessionId, String pageMapName, int pageId, int pageVersion) {
		Query query = newQuery();
		query.setFilter("sessionId == :sessionId && pageMapName == :pageMapName && pageId == :pageId && pageVersion == :pageVersion");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sessionId", sessionId);
		params.put("pageMapName", pageMapName);
		params.put("pageId", pageId);
		params.put("pageVersion", pageVersion);

		@SuppressWarnings("unchecked")
		List<WicketPage> list = (List<WicketPage>) query.executeWithMap(params);

		return !list.isEmpty();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WicketPage> findPages(String sessionId, String pagemap, int id) {
		Query query = newQuery();
		query.setFilter("sessionId == :sessionId && pageMapName == :pageMapName && pageId == :pageId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sessionId", sessionId);
		params.put("pageMapName", pagemap);
		params.put("pageId", id);

		return (List<WicketPage>) query.executeWithMap(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WicketPage findPage(String sessionId, String pagemap, int id, int versionNumber, int ajaxVersionNumber) {
		Query query = newQuery();
		query.setFilter("sessionId == :sessionId && pageMapName == :pageMapName && pageId == :pageId");
		query.setOrdering("pageVersion ascending, ajaxVersionNumber ascending");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sessionId", sessionId);
		params.put("pageMapName", pagemap);
		params.put("pageId", id);

		List<WicketPage> pages = (List<WicketPage>) query.executeWithMap(params);

		if (pages.isEmpty()) {
			return null;
		}

		if (versionNumber == -1 && ajaxVersionNumber == -1) {
			return pages.get(0);
		}

		for (WicketPage wp : pages) {
			return wp;
		}

		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WicketPage> findPages(String sessionId) {
		Query query = newQuery();
		query.setFilter("sessionId == :sessionId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sessionId", sessionId);

		return (List<WicketPage>) query.executeWithMap(params);
	}

	@Override
	public WicketPage findPage(String sessionId, int pageId) {
		log.info("findPage: sessionId = " + sessionId + ", pageId = " + pageId + ". start.");
		Query query = newQuery();
		query.setFilter("sessionId == :sessionId && pageId == :pageId");
		//query.declareParameters("java.lang.Integer :pageId, java.lang.String :sessionId");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sessionId", sessionId);
		params.put("pageId", pageId);

		List<WicketPage> pages = (List<WicketPage>) query.executeWithMap(params);

		if (pages.isEmpty()) {
			log.info("findPage: pages isEmpty. end.");
			return null;
		}

		for (WicketPage wp : pages) {
			log.info("findPage: page found. end.");
			return wp;
		}
		return null;
	}

}
