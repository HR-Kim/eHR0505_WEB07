package kr.co.ehr.user.service;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import kr.co.ehr.cmn.DTO;

public class Login extends DTO {
	/** 사용자 ID*/
	@NotEmpty
	@Length(min=2, max=20)
	private String u_id    ;
	/** 사용자 비번*/
	@NotEmpty
	@Length(min=2, max=100)	
	private String passwd  ;
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Override
	public String toString() {
		return "Login [u_id=" + u_id + ", passwd=" + passwd + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
