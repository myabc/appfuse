package org.appfuse.dao;

import org.appfuse.model.Role;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface RoleDao extends GenericDao<org.appfuse.model.Role, Long> {
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return role populated role object
     */
    public Role getRoleByName(String rolename);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    public void removeRole(String rolename);
}
