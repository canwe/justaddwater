package com.example.wicketgae;

import java.util.List;

public interface WicketPageQueries {

	boolean containesPage(String sessionId, String pageMapName, int pageId, int pageVersion);

	List<WicketPage> findPages(String sessionId, String pagemap, int id);

	WicketPage findPage(String sessionId, String pagemap, int id, int versionNumber, int ajaxVersionNumber);
	
	WicketPage findPage(String sessionId, int pageId);

	List<WicketPage> findPages(String sessionId);

}
