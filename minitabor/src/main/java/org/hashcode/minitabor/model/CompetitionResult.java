package org.hashcode.minitabor.model;

public class CompetitionResult {
	private String error;
	private String redirect;
	private Boolean enabled = true;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
