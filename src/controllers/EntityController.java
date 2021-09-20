package controllers;

public interface EntityController {

    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isRequestingAction();
    boolean isRequestingAttack();
    boolean isRequesting1();
    boolean isRequesting2();

}
