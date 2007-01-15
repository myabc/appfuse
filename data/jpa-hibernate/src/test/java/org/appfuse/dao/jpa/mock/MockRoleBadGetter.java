package org.appfuse.dao.jpa.mock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 */
@Entity
public class MockRoleBadGetter {
    private static final long serialVersionUID = 3690197650654049848L;
    
    @Id  @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    public MockRoleBadGetter() {
    }

    public MockRoleBadGetter(String name) {
        this.name = name;
    }

    public Long getPersistentId() {
        return id;
    }

    /**
     * @see org.acegisecurity.GrantedAuthority#getAuthority()
     */
    @Transient
    public String getAuthority() {
        return getName();
    }

    @Column(length=20)
    public String getName() {
        return this.name;
    }

    @Column(length=64)
    public String getDescription() {
        return this.description;
    }

    public void setPersistentId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockRoleBadGetter)) return false;

        final MockRoleBadGetter role = (MockRoleBadGetter) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString();
    }

}
