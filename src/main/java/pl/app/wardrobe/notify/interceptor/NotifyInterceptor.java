package pl.app.wardrobe.notify.interceptor;

import jakarta.annotation.Priority;
import jakarta.ejb.EJBAccessException;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import pl.app.wardrobe.clothes.entity.Item;
import pl.app.wardrobe.notify.interceptor.binding.Notify;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
@Notify
@Priority(10)
public class NotifyInterceptor {
    private final SecurityContext securityContext;

    //private static final Logger logger = Logger.getLogger(NotifyInterceptor.class.getName());
//    static {
//        Logger rootLogger = Logger.getLogger("");
//        rootLogger.setLevel(Level.ALL);
//        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
//            if (handler instanceof ConsoleHandler) {
//                handler.setLevel(Level.ALL);
//            }
//        }
//        ConsoleHandler handler = new ConsoleHandler();
//        handler.setLevel(Level.ALL);
//        logger.addHandler(handler);
//    }

    @Inject
    public NotifyInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new EJBAccessException();
        }

        try {
            if (notify(context)) {
                return context.proceed();
            }
        } catch (Exception e) {
           // logger.log(Level.SEVERE, "Error in NotifyInterceptor: {0}", e.getMessage());
            throw e;
        }
        return null;
    }

    private boolean notify(InvocationContext context) {
        Object provided = context.getParameters()[0];
        String method = context.getMethod().getName();

        if(provided instanceof Item){
            String user = securityContext.getCallerPrincipal().getName();
            //log(user, method, ((Item) provided).getId().toString());
            System.out.println( "User: " + user + "  do operation: " +  method + " on object: "+ ((Item) provided).getId().toString());
            return true;
        }
        else if(provided instanceof UUID) {
            String user = securityContext.getCallerPrincipal().getName();
            //log(user, method, ((UUID) provided).toString());
            System.out.println( "User: " + user + "  do operation: " +  method + " on object: "+ ((UUID) provided).toString());
            return true;
        }

        return false;
    }

//    private void log(String user, String operation, String item) {
//        logger.log(Level.INFO, "User: " + user + "  do operation: " +  operation + " on object: "+ item);
//    }
}

