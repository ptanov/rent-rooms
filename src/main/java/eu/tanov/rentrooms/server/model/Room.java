package eu.tanov.rentrooms.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;

@Entity
public class Room {
	@Id
	@Column(name = "key")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	/**
	 * Visible only to lessor
	 */
	private String name;

    @ManyToOne(fetch = FetchType.LAZY)
	private Lessor owner;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Lessor getOwner() {
		return owner;
	}

	public void setOwner(Lessor owner) {
		this.owner = owner;
	}
}
