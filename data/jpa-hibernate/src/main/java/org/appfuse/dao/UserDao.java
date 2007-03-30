package org.appfuse.dao;

import java.util.List;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.appfuse.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Gets users information based on login name.
     * @param username the user's username
     * @return userDetails populated userDetails object
     */
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    public List<User> getUsers();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     */
    public User saveUser(User user);
    
}
