package pl.app.wardrobe.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.app.wardrobe.datasource.DataSource;
import pl.app.wardrobe.serialization.CloningSerialization;

import java.lang.invoke.SerializedLambda;

/* order configuration in web.xml*/
@WebListener
public class DataSourceCreation implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datasource", new DataSource( new CloningSerialization()));
    }
}
