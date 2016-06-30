package jiangsir.eshine.DAOs;

import java.sql.SQLException;
import java.util.TreeMap;

import jiangsir.eshine.Objects.AppConfig;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Scopes.ApplicationScope;

public class AppConfigService {

	public int insert(AppConfig appConfig) throws DataException {
		new AppConfigDAO().truncate();
		try {
			int result = new AppConfigDAO().insert(appConfig);
			ApplicationScope.setAppConfig(appConfig);
			return result;
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

	public void update(AppConfig appConfig) throws DataException {
		try {
			new AppConfigDAO().update(appConfig);
			ApplicationScope.setAppConfig(appConfig);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(long id) throws DataException {
		try {
			new AppConfigDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public AppConfig getAppConfig() {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		for (AppConfig appConfig : new AppConfigDAO().getAppConfigByFields(fields, "id DESC", 0)) {
			return appConfig;
		}
		return new AppConfig();
	}

}
