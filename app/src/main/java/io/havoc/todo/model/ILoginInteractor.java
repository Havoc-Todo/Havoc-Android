package io.havoc.todo.model;

/**
 * Model for logging in
 * Used in LoginInteractorImpl
 */
public interface ILoginInteractor {

    /**
     * Login to Havoc
     *
     * @param username of the user
     * @param password of the user
     * @return whether or not the login was successful
     */
    boolean login(String username, String password);

    /**
     * Logout the current user
     *
     * @return whether or not the logout was successful
     */
    boolean logout();
}
