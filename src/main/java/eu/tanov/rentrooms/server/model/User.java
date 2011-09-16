package eu.tanov.rentrooms.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

@Entity
@MappedSuperclass
public abstract class User {
	@Id
	@Column(name = "googleId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private Key googleId;

	private String mail;

	public Key getGoogleId() {
		return googleId;
	}

	public void setGoogleId(Key googleId) {
		this.googleId = googleId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}