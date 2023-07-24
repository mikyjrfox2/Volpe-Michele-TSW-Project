package utils;

import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MainContext implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc=sce.getServletContext();
		DataSource ds=null;
		
		Context initCtx;
		try {
			initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			ds=(DataSource)envCtx.lookup("jdbc/ggwp");
			
			sc.setAttribute("DataSource", ds);
		} catch (NamingException e) {
			System.out.println(e);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext cont=sce.getServletContext();
		cont.removeAttribute("DataSource");
	}
}
