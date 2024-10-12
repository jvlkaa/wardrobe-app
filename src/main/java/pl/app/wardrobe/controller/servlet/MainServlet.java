package pl.app.wardrobe.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import pl.app.wardrobe.clothes.controller.api.ClothesController;
import pl.app.wardrobe.clothes.controller.api.ItemController;
import pl.app.wardrobe.clothes.dto.PatchItemRequest;
import pl.app.wardrobe.clothes.dto.PutItemRequest;
import pl.app.wardrobe.user.controller.api.UserController;
import pl.app.wardrobe.user.dto.PatchUserRequest;
import pl.app.wardrobe.user.dto.PutUserRequest;
import pl.app.wardrobe.user.entity.User;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* main servlet response for collecting requests, sending responses */
@WebServlet(urlPatterns = {
        MainServlet.Paths.MAIN + "/*"})
@MultipartConfig(maxFileSize = 200 * 1024)
public class MainServlet extends HttpServlet {
    private ItemController itemController;
    private ClothesController clothesController;
    private UserController userController;
    private final Jsonb jsonb = JsonbBuilder.create();
    public static final class Paths {
        public static final String MAIN = "/main";

    }
    /* paths */
    public static final class Patterns {
        /* ITEM */

        /* id */
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        /* item list */
        public static final Pattern ITEMS   = Pattern.compile("/items/?");

        /* item */
        public static final Pattern ITEM = Pattern.compile("/item/(%s)".formatted(UUID.pattern()));


        /* CLOTHES */

        /* clothes list */
        public static final Pattern CLOTHES_LIST = Pattern.compile("/clothesList/?");

        /* item list from clothes */
        public static final Pattern CLOTHES_ITEM_LIST = Pattern.compile("/clothesList/(%s)/items/?".formatted(UUID.pattern()));

        /* item list from user*/
        public static final Pattern USER_ITEM_LIST = Pattern.compile("/users/(%s)/items/?".formatted(UUID.pattern()));

        /* USER */

        /* user list */
        public static final Pattern USER_LIST = Pattern.compile("/userList/?");

        /* user */
        public static final Pattern USER = Pattern.compile("/userList/(%s)".formatted(UUID.pattern()));

        /* avatar  of the user */
        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        itemController = (ItemController) getServletContext().getAttribute("itemController");
        clothesController =   (ClothesController) getServletContext().getAttribute("clothesController");
        userController = (UserController) getServletContext().getAttribute("userController");
    }

    /* url creation */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

    private String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID getIdFromPath(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("Id not found in the path");
    }


    /* doPut, doGet, doPatch, doDelete */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getPath(request);
        String servletPath = request.getServletPath();
        if (Paths.MAIN.equals(servletPath)) {
            if (path.matches(Patterns.ITEM.pattern())) {
                UUID uuid = getIdFromPath(Patterns.ITEM, path);
                itemController.putItem(uuid, jsonb.fromJson(request.getReader(), PutItemRequest.class));
                response.addHeader("Location", createUrl(request, Paths.MAIN, "items", uuid.toString()));
                return;
            }
            else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = getIdFromPath(Patterns.USER, path);
                userController.putUser(uuid, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                response.addHeader("Location", createUrl(request, Paths.MAIN, "userList", uuid.toString()));
                return;
            }
            else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = getIdFromPath(Patterns.USER_AVATAR, path);
                userController.putUserAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getPath(request);
        String servletPath = request.getServletPath();
        if (Paths.MAIN.equals(servletPath)) {
            if (path.matches(Patterns.ITEMS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(itemController.getItemList()));
                return;
            } else if (path.matches(Patterns.ITEM.pattern())) {
                response.setContentType("application/json");
                UUID uuid = getIdFromPath(Patterns.ITEM, path);
                response.getWriter().write(jsonb.toJson(itemController.getItem(uuid)));
                return;
            } else if (path.matches(Patterns.CLOTHES_LIST.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(clothesController.getClothesList()));
                return;
            } else if (path.matches(Patterns.CLOTHES_ITEM_LIST.pattern())) {
                response.setContentType("application/json");
                UUID uuid = getIdFromPath(Patterns.CLOTHES_ITEM_LIST, path);
                response.getWriter().write(jsonb.toJson(itemController.getClothesItemList(uuid)));
                return;
            } else if (path.matches(Patterns.USER_ITEM_LIST.pattern())) {
                response.setContentType("application/json");
                UUID uuid = getIdFromPath(Patterns.USER_ITEM_LIST, path);
                response.getWriter().write(jsonb.toJson(itemController.getUserItemList(uuid)));
                return;
            }
            else if (path.matches(Patterns.USER_LIST.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUserList()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = getIdFromPath(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = getIdFromPath(Patterns.USER_AVATAR, path);
                byte[] avatar = userController.getUserAvatar(uuid);
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getPath(request);
        String servletPath = request.getServletPath();
        if (Paths.MAIN.equals(servletPath)) {
            if (path.matches(Patterns.ITEM.pattern())) {
                UUID uuid = getIdFromPath(Patterns.ITEM, path);
                itemController.patchItem(uuid, jsonb.fromJson(request.getReader(), PatchItemRequest.class));
                return;
            }
            else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = getIdFromPath(Patterns.USER, path);
                userController.patchUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                return;
            }
            else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = getIdFromPath(Patterns.USER_AVATAR, path);
                userController.patchUserAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getPath(request);
        String servletPath = request.getServletPath();
        if (Paths.MAIN.equals(servletPath)) {
            if (path.matches(Patterns.ITEM.pattern())) {
                UUID uuid = getIdFromPath(Patterns.ITEM, path);
                itemController.deleteItem(uuid);
                return;
            }
            else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = getIdFromPath(Patterns.USER, path);
                userController.deleteUser(uuid);
                return;
            }
            else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = getIdFromPath(Patterns.USER_AVATAR, path);
                userController.deleteUserAvatar(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
