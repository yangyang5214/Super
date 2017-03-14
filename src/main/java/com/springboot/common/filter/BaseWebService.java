package com.springboot.common.filter;


import com.springboot.common.listener.StoredUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

public class BaseWebService {
	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse res;

	public Object getSessionValue(String key) {
		HttpSession session = req.getSession();
		if (null == session) {
			return null;
		} else {
			Object ret = session.getAttribute(key);
			return ret;
		}
	}

	public boolean addSessionValue(String key, Object value) {
		HttpSession session = req.getSession();
		if (null == session) {
			return false;
		} else {
			session.setAttribute(key, value);
			return true;
		}
	}

	public StoredUser getUser() {
		return (StoredUser) getSessionValue(StoredUser.KEY);
	}
}
