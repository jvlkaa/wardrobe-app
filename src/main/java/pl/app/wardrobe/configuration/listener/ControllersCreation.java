package pl.app.wardrobe.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.app.wardrobe.clothes.controller.impl.ClothesControllerImpl;
import pl.app.wardrobe.clothes.controller.impl.ItemControllerImpl;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.dtofactory.DtoFunctionFactory;
import pl.app.wardrobe.user.controller.impl.UserControllerImpl;
import pl.app.wardrobe.user.service.UserService;

/* order configuration in web.xml*/
@WebListener
public class ControllersCreation implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ItemService itemService = (ItemService) event.getServletContext().getAttribute("itemService");
        ClothesService clothesService =  (ClothesService) event.getServletContext().getAttribute("clothesService");
        UserService userService =  (UserService) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("itemController", new ItemControllerImpl(
                itemService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("clothesController", new ClothesControllerImpl(
                clothesService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("userController", new UserControllerImpl(
                userService,
                new DtoFunctionFactory()
        ));
    }

}
