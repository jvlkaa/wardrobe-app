package pl.app.wardrobe.view.producer;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import pl.app.wardrobe.view.producer.qualifier.FacesElement;

public class HttpServletResponseProducer {

    private final FacesContext facesContext;

    @Inject
    public HttpServletResponseProducer(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    @Produces
    @RequestScoped
    @FacesElement
    HttpServletResponse create() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }

}

