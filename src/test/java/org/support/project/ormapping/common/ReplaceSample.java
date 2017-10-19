package org.support.project.ormapping.common;


public class ReplaceSample {

	public static void main(String[] args) {
		String str = "jdbc:h2:tcp://localhost/{user.home}/.crawler/crawler_db";
		if (str.indexOf("{user.home}") != -1) {
			String userHome = System.getProperty("user.home");
			str = str.replace("{user.home}", userHome);
			System.out.println("Database connection url : " + str);
		}
		
		if (str.indexOf("\\") != -1) {
			str = str.replaceAll("\\\\", "/");
			System.out.println("Database connection url : " + str);
		}
		
	}

}
