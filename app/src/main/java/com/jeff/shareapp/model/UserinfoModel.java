package com.jeff.shareapp.model;
// default package



/**
 * UserinfoModel entity. @author MyEclipse Persistence Tools
 */

public class UserinfoModel  implements java.io.Serializable {
    private static final long serialVersionUID = -4549365291121821693L;

    // Fields    

     private Integer userId;
     private Integer userType;
     private String username;
     private String password;
     private String telephone;
     private String email;
     private String userHeadUrl;
     private String token;


    // Constructors

    /** default constructor */
    public UserinfoModel() {
    }

	/** minimal constructor */
    public UserinfoModel(Integer userType, String username, String password) {
        this.userType = userType;
        this.username = username;
        this.password = password;
    }
    
    /** full constructor */
    public UserinfoModel(Integer userType, String username, String password, String telephone, String email, String userHeadUrl, String token) {
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.email = email;
        this.userHeadUrl = userHeadUrl;
        this.token = token;
    }

   
    // Property accessors

    public UserinfoModel(String username, int user_type, String password, String telephone,
			String email, String token) {
		// TODO Auto-generated constructor stub
         this.username = username;
         this.password = password;
         this.telephone = telephone;
         this.email = email;
         this.token = token;
         this.userType=user_type;
	}

	public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return this.userType;
    }
    
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserHeadUrl() {
        return this.userHeadUrl;
    }
    
    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
   








}