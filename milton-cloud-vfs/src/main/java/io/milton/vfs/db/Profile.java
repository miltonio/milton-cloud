package io.milton.vfs.db;

import io.milton.vfs.db.utils.DbUtils;
import io.milton.vfs.db.utils.SessionManager;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.Expression;

/**
 * A user profile is defined within an organisation. Might change this in the future so
 * that the user profile is within an organsiation, but the credentials probably should exist
 * in a global space.
 *
 * @author brad
 */
@javax.persistence.Entity
@DiscriminatorValue("U")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile extends BaseEntity implements VfsAcceptor {
    
    
    public static List<Profile> findByBusinessUnit(Organisation organisation, Session session) {
        Criteria crit = session.createCriteria(Profile.class);
        crit.add(Expression.eq("adminOrg", organisation));        
        return DbUtils.toList(crit, Profile.class);        
    }
    
     
    private Organisation businessUnit; // users may be assigned to a business unit within their administrative organisation
    
    private List<Credential> credentials;
                
    private String firstName;
    
    private String surName;
    
    private String phone;

    private String email;
    
    private String photoHref;
    
    private String nickName;

    @OneToMany(mappedBy="profile")
    public List<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }
               
    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    public String getEmail() {
        return email;
    }
    
        
    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column
    public String getPhotoHref() {
        return photoHref;
    }

    public void setPhotoHref(String photoHref) {
        this.photoHref = photoHref;
    }
    
    
    

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Users may be assigned to a business unit within their administative organisation
     * 
     */
    @ManyToOne(optional=true)
    public Organisation getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(Organisation businessUnit) {
        this.businessUnit = businessUnit;
    }
      
    
    
    /**
     * Create a GroupMembership linking this profile to the given group. Is immediately saved
     * 
     * @param g
     * @return 
     */
    public Profile addToGroup(Group g) {
        if( g.isMember(this)) {
            return this;
        }
        GroupMembership gm = new GroupMembership();
        gm.setCreatedDate(new Date());
        gm.setGroupEntity(g);
        gm.setMember(this);
        gm.setModifiedDate(new Date());
        SessionManager.session().save(gm);
        return this;
    }

    @Override
    public void accept(VfsVisitor visitor) {
        visitor.visit(this);
    }   
}
