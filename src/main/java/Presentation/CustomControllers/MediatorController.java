package Presentation.CustomControllers;

public abstract class MediatorController {
    protected MediatorController() {
        setParentPane();
    }
    abstract protected void setParentPane();
}
