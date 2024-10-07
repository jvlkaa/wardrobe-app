package pl.app.wardrobe.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.app.wardrobe.clothes.repository.api.ClothesRepository;
import pl.app.wardrobe.clothes.repository.api.ItemRepository;
import pl.app.wardrobe.clothes.repository.memory.ClothesInMemoryRepository;
import pl.app.wardrobe.clothes.repository.memory.ItemInMemoryRepository;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.crypto.PasswordHash;
import pl.app.wardrobe.datasource.DataSource;
import pl.app.wardrobe.user.repository.api.UserRepository;
import pl.app.wardrobe.user.repository.memory.UserInMemoryRepository;
import pl.app.wardrobe.user.service.UserService;

/* order configuration in web.xml*/
@WebListener
public class ServiceCreation implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        /* avatars path from deskryptor */
        String avatarsPath =   event.getServletContext().getInitParameter("avatarsPath");
        DataSource dataSource = (DataSource) event.getServletContext().getAttribute("datasource");

        ItemRepository itemRepository = new  ItemInMemoryRepository(dataSource);
        ClothesRepository clothesRepository =  new ClothesInMemoryRepository(dataSource);
        UserRepository userRepository = new UserInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("itemService", new ItemService(itemRepository, clothesRepository, userRepository));
        event.getServletContext().setAttribute("clothesService", new ClothesService(clothesRepository));
        event.getServletContext().setAttribute("userService", new UserService(userRepository, new PasswordHash(), avatarsPath));
    }

}

