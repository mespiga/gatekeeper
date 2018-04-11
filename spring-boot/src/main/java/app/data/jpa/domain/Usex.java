/*
 * Author: Miguel Espiga
 */

package app.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;
import org.springframework.security.core.GrantedAuthority;
import app.properties.ReportConstants;
import org.springframework.security.core.authority.AuthorityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
public class Usex {


   

    @Id
    @Column(name = "USEX_ID", nullable = false)
    @GeneratedValue
    
    @Getter @Setter private Long id;
    @JsonIgnore
    @Getter @Setter private String selectedProducts;
    @Getter @Setter private Long parentId;
    @Getter @Setter private String name;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Column(unique = true)
    @Getter @Setter private String email;

    @JsonInclude
    @Transient
    @Getter @Setter private String token;
    @JsonIgnore
    @Getter @Setter private String password;
    @Getter @Setter private String company;
    @Getter @Setter private Boolean active;
    @Getter @Setter private String img;
    @Getter @Setter private String role;

    @JsonIgnore
    @Transient
    @Getter @Setter private Collection<? extends GrantedAuthority> authorities;
    
    Usex() { // jpa only
    }

    public Usex(String token){
        this.token = token;
        this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(ReportConstants.AUTH);
    }

    public Usex(Long parentId,String firstName,String lastName,
        String company,String img,String role, String email, 
        String password, Boolean active) {
        this.email = email;
        this.password = password;
        this.active   = active; 
        this.parentId   = parentId; 
        this.firstName   = firstName; 
        this.lastName   = lastName; 
        this.company   = company; 
        this.img   = img; 
        this.role   = role;
        
    }

    public String login(String email, String password){
		Logger log = LoggerFactory.getLogger(this.getClass());
        try {
            this.token = JWT.create()
            .withIssuer(ReportConstants.AUTH)
            .withExpiresAt(new Date(System.currentTimeMillis()+ReportConstants.EXPIRE*60*1000))
            .withClaim("email", email)
            .sign(Algorithm.HMAC256(ReportConstants.SECRET));
            log.debug("On login : ========================== this.token: " + this.token);
            return this.token;
        } catch (JWTCreationException exception){
            return null;
            //Invalid Signing configuration / Couldn't convert Claims.
        }catch (Exception exception){
            return null;
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    public boolean validateToken(){
		Logger log = LoggerFactory.getLogger(this.getClass());
        try {
            log.debug("On validateToken ========================== this.token: " + this.token);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(ReportConstants.SECRET))
                .withIssuer(ReportConstants.AUTH)
                .withClaim("email",email)
                .acceptExpiresAt(1502392374L) //TODO::Remove (infinite duration)
                .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(this.token);
            return true;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return false;
        }catch (Exception exception){
            return false;
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    public String getClaimsFromToken(String claimName){
		Logger log = LoggerFactory.getLogger(this.getClass());
        try {
            this.email = JWT.decode(this.token).getClaim(claimName).asString();
            log.debug("On getClaimsFromToken ========================== this.email: " + this.email);
            return this.email;
        } catch (JWTDecodeException exception){
            //Invalid signature/claims
            return null;
        }catch (Exception exception){
            return null;
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }
}