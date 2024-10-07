package pl.app.wardrobe.controller.servlet.exception;

public class ResourceConflictException extends HttpRequestException{
    private static final int RESPONSE_CODE = 409;

    public ResourceConflictException() {
        super(RESPONSE_CODE);
    }
}
