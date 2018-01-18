package com.perficient.hr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PerfProperties {

	
	@Value("${email.host}")
	private String host;
	
	@Value("${email.port}")
	private String port;
	
	@Value("${email.username}")
	private String username;
	
	@Value("${email.password}")
	private String password;
	
	@Value("${ptoStoreLoc}")
	private String ptoStoreLoc;
	
	@Value("${ptoCount}")
	private String ptoCount;
	
	@Value("${wfhCount}")
	private String wfhCount;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the ptoStoreLoc
	 */
	public String getPtoStoreLoc() {
		return ptoStoreLoc;
	}
	/**
	 * @param ptoStoreLoc the ptoStoreLoc to set
	 */
	public void setPtoStoreLoc(String ptoStoreLoc) {
		this.ptoStoreLoc = ptoStoreLoc;
	}
	/**
	 * @return the ptoCount
	 */
	public String getPtoCount() {
		return ptoCount;
	}
	/**
	 * @param ptoCount the ptoCount to set
	 */
	public void setPtoCount(String ptoCount) {
		this.ptoCount = ptoCount;
	}
	/**
	 * @return the wfhCount
	 */
	public String getWfhCount() {
		return wfhCount;
	}
	/**
	 * @param wfhCount the wfhCount to set
	 */
	public void setWfhCount(String wfhCount) {
		this.wfhCount = wfhCount;
	}

}
