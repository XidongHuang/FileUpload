package javaweb.app;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import javaweb.utils.FileUploadAppProperties;


/**
 * Application Lifecycle Listener implementation class FileUploadAppListener
 *
 */
@WebListener
public class FileUploadAppListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public FileUploadAppListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
        InputStream in =  getClass().getClassLoader().getResourceAsStream("/upload.properties");
        
        Properties properties = new Properties();
        try {
			properties.load(in);
			
			for(Map.Entry<Object, Object> prop:properties.entrySet()){
				
				String propertyName = (String) prop.getKey();
				String propertyValue = (String) prop.getValue();
				FileUploadAppProperties.getInstance().addProperty(propertyName, propertyValue);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
         
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
