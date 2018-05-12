package com.hussain.mycrm.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import com.hussain.mycrm.model.Role;
import com.hussain.mycrm.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	
    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLES = "roles";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

  //  @Value("${auth.token.secret}")
    private String secret="quadnary";

   // @Value("${auth.token.expiration.min}")
    private Long expiration=10L;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = (String)claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
        	LOGGER.error("Error while getting username from token",e);
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }
    
    public Set<Role> getRolesFromToken(String token) {
        Set<Role> roles= new HashSet<Role>();
        try {
            final Claims claims = getClaimsFromToken(token);
            Object roleClaims = claims.get(CLAIM_KEY_ROLES);
            LOGGER.debug("CLAIM_KEY_ROLES::::"+roleClaims);
            if(roleClaims != null && List.class.isAssignableFrom(roleClaims.getClass())) {
            	List<String> roleCodes = (List<String>)claims.get(CLAIM_KEY_ROLES);
            	roleCodes.forEach(roleCode->{
            		Role role = new Role();
            		role.setRoleCode(roleCode);
            		roles.add(role);
            	});
            
            }
        } catch (Exception e) {
        	LOGGER.error("Error while getting Roles from token",e);
        }
        return roles;
    }
    
    public User getUserFromToken(String token) {
    	try {
    		User user = new User();
    		user.setActive(true);
    		//user.setLastLogin(null);
    		//user.setLastPasswordReset(null);
    		//user.setPassword(null);
    		user.setRoles(getRolesFromToken(token));
    		user.setUserName(getUsernameFromToken(token));
    		return user;
    	} catch(Exception e) {
    		return null;
    	}
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
        	LOGGER.error("Error while parsing token",e);
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 60 * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if(expiration == null) {
        	return true;
        }
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if(device == null) {
        	return audience;
        }
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience)||true);
    }

    public String generateToken(User userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUserName());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_CREATED, new Date().getTime());
        if(userDetails.getRoles() !=null ) {
        	List<String> roleCodes = new ArrayList<String>();
        	userDetails.getRoles().forEach(role->roleCodes.add(role.getRoleCode()));
        	/*String roleArray[] =  roleCodes.toArray(new String[roleCodes.size()]);*/
        	LOGGER.debug("roleCodes.size() ::"+roleCodes.size());
        	claims.put(CLAIM_KEY_ROLES,roleCodes);
        }
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        LOGGER.debug("created:"+created+" lastPasswordReset:"+lastPasswordReset);
        LOGGER.debug("ingoreToken:"+ignoreTokenExpiration(token));
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) /*|| ignoreTokenExpiration(token)*/);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date().getTime());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, User userDetails) {
        User user = userDetails;
        final String username = getUsernameFromToken(token);
        //final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUserName())
                        && !isTokenExpired(token)
                        /*&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())*/);
    }
    
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

