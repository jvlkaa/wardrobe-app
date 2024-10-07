package pl.app.wardrobe.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pl.app.wardrobe.clothes.entity.Clothes;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.clothes.entity.Material;
import pl.app.wardrobe.clothes.entity.Size;
import pl.app.wardrobe.clothes.service.ClothesService;
import pl.app.wardrobe.clothes.service.ItemService;
import pl.app.wardrobe.user.entity.Role;
import pl.app.wardrobe.user.entity.User;
import pl.app.wardrobe.user.service.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/* order configuration in web.xml*/
@WebListener
public class InitializedData implements ServletContextListener {
    private ItemService itemService;
    private ClothesService clothesService;
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        itemService = (ItemService) event.getServletContext().getAttribute("itemService");
        userService = (UserService) event.getServletContext().getAttribute("userService");
        clothesService = (ClothesService) event.getServletContext().getAttribute("clothesService");
        init();
    }

    @SneakyThrows
    private void init() {
        /* USERS */
        User admin = User.builder()
                .id(UUID.fromString("3ed970bd-e79b-448c-936a-821a58eef383"))
                .login("admin")
                .password("root")
                .dateOfBirth(LocalDate.of(1985, 1, 10))
                .email("admin@example.com")
                .avatar("3ed970bd-e79b-448c-936a-821a58eef383.png")
                .role(Role.ADMIN)
                .build();

        User user1 = User.builder()
                .id(UUID.fromString("8707cd01-ad10-4c87-ba0d-09b789f51ef8"))
                .login("johnSmith")
                .password("john123")
                .dateOfBirth(LocalDate.of(2002, 5, 20))
                .email("john@example.com")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .id(UUID.fromString("b021c25e-e44e-42cc-b07c-774f0b2e5064"))
                .login("BobSmith")
                .password("bob321")
                .dateOfBirth(LocalDate.of(1995, 3, 15))
                .email("jane@example.com")
                .role(Role.USER)
                .build();

        User user3 = User.builder()
                .id(UUID.fromString("d6a72d4b-0f98-47e5-8e3b-2f37904a6f7b"))
                .login("aliceSmith")
                .password("alice987")
                .dateOfBirth(LocalDate.of(2000, 7, 25))
                .email("alice@example.com")
                .role(Role.USER)
                .build();

        /* CLOTHES*/
//        Clothes jeans = Clothes.builder()
//                .id(UUID.fromString("b12c7a56-1e45-4b3f-a5a2-8e8735f543d1"))
//                .name("Jeans")
//                .material(List.of(Material.DENIM))
//                .build();
//
//        Clothes cottonTShirt = Clothes.builder()
//                .id(UUID.fromString("6f14bc1d-2a49-4f70-87ae-923b4bbd572e"))
//                .name("T-Shirt")
//                .material(List.of(Material.COTTON))
//                .build();
//
//        Clothes leatherJacket = Clothes.builder()
//                .id(UUID.fromString("4d7f9b4a-584a-4d9a-8f10-3b8b2b6d3b92"))
//                .name("Jacket")
//                .material(List.of(Material.LEATHER))
//                .build();
//
//        Clothes woolSweater = Clothes.builder()
//                .id(UUID.fromString("3f17ae5c-648e-4f3b-b793-95fc2e5d1247"))
//                .name("Sweater")
//                .material(List.of(Material.WOOL))
//                .build();
//
//        Clothes silkDress = Clothes.builder()
//                .id(UUID.fromString("af9d2f8c-3d28-4a0a-82e4-2292f1a9b15f"))
//                .name("Dress")
//                .material(List.of(Material.SILK))
//                .build();
//
//        Clothes linenShirt = Clothes.builder()
//                .id(UUID.fromString("dc41721a-8f41-4a63-a7e1-bb5e54f54f8e"))
//                .name("Shirt")
//                .material(List.of(Material.LINEN, Material.POLYESTER))
//                .build();

        /* ITEMS */
//        Item item1 = Item.builder()
//                .id(UUID.fromString("1b56d72e-c60f-4e6c-b74a-bd9b531b4be1")) // Item 1
//                .name("High waisted jeans")
//                .size(Size.M)
//                .color("blue")
//                .purchaseDate(LocalDate.of(2024, 5, 20))
//                .clothesCategory(jeans)
//                .owner(user1)
//                .build();
//
//        Item item2 = Item.builder()
//                .id(UUID.fromString("8d0c9b4e-81e4-4f3d-8a07-c38e4af2f27c")) // Item 2
//                .name("basic white t-Shirt")
//                .size(Size.M)
//                .color("white")
//                .purchaseDate(LocalDate.of(2023, 6, 15))
//                .clothesCategory(cottonTShirt)
//                .owner(user1)
//                .build();
//
//        Item item3 = Item.builder()
//                .id(UUID.fromString("f5d79444-9b0a-4bde-b6a8-6355075d4c6a")) // Item 3
//                .name("motorcycle jacket")
//                .size(Size.L)
//                .color("black")
//                .purchaseDate(LocalDate.of(2022, 10, 5))
//                .clothesCategory(leatherJacket)
//                .owner(user2)
//                .build();
//
//        Item item4 = Item.builder()
//                .id(UUID.fromString("289fbdb0-6cc4-4e31-b6e1-e78d6f0ab49a")) // Item 4
//                .name("warm sweater")
//                .size(Size.L)
//                .color("white")
//                .purchaseDate(LocalDate.of(2023, 7, 10))
//                .clothesCategory(woolSweater)
//                .owner(user2)
//                .build();
//
//        Item item5 = Item.builder()
//                .id(UUID.fromString("a5720b66-ded2-4c14-bc38-6b6371c9b70c")) // Item 5
//                .name("elegant maxi dress")
//                .size(Size.S)
//                .color("green")
//                .purchaseDate(LocalDate.of(2023, 8, 25))
//                .clothesCategory(silkDress)
//                .owner(user3)
//                .build();
//
//        Item item6 = Item.builder()
//                .id(UUID.fromString("0b2af1c5-7f4d-4b67-a236-5c36fce3de47")) // Item 6
//                .name("v-neck shirt")
//                .size(Size.XS)
//                .color("pink")
//                .purchaseDate(LocalDate.of(2023, 9, 12))
//                .clothesCategory(linenShirt)
//                .owner(user3)
//                .build();
//
//        Item item7 = Item.builder()
//                .id(UUID.fromString("27c90e4a-fc65-4b49-bf1b-c077d8f0b8f5")) // Item 7
//                .name("skinny jeans")
//                .size(Size.S)
//                .color("black")
//                .purchaseDate(LocalDate.of(2022, 9, 5))
//                .clothesCategory(jeans)
//                .owner(admin)
//                .build();
//
//        Item item8 = Item.builder()
//                .id(UUID.fromString("f8eec186-c7e5-4a2c-81ee-9a1af12c5472")) // Item 8
//                .name("cute pink mini dress")
//                .size(Size.S)
//                .color("pink")
//                .purchaseDate(LocalDate.of(2023, 12, 1))
//                .clothesCategory(silkDress)
//                .owner(admin)
//                .build();

        /* CREATION */
        userService.create(admin);
        userService.create(user1);
        userService.create(user2);
        userService.create(user3);
//        clothesService.create(jeans);
//        clothesService.create(cottonTShirt);
//        clothesService.create(leatherJacket);
//        clothesService.create(woolSweater);
//        clothesService.create(silkDress);
//        clothesService.create(linenShirt);
//        itemService.create(item1);
//        itemService.create(item2);
//        itemService.create(item3);
//        itemService.create(item4);
//        itemService.create(item5);
//        itemService.create(item6);
//        itemService.create(item7);
//        itemService.create(item8);

    }
}
