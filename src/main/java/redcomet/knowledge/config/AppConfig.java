package redcomet.knowledge.config;


public class AppConfig extends redcomet.web.config.AppConfig {
	
	private String indexPath;
	
	private boolean convIndexPath = false;
	
	/**
	 * @return the indexPath
	 */
	public String getIndexPath() {
		if (!convIndexPath) {
			String path = indexPath;
			if (path.indexOf("{user.home}") != -1) {
				String userHome = System.getProperty("user.home");
				path = path.replace("{user.home}", userHome);
			}
			if (path.indexOf("\\") != -1) {
				path = path.replaceAll("\\\\", "/");
			}
			this.indexPath = path;
			convIndexPath = true;
		}
		return indexPath;
	}

	/**
	 * @param indexPath the indexPath to set
	 */
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
	
}
