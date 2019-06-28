package com.bene.validation;

import java.io.Serializable;

public class CardDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3999620392420013508L;

	@Column(nullable = false, email = true)
	private String email;
	
	@Column(nullable = false)
	private String card;
	
	@Column(nullable = false)
	@Domain(string = {"3","4"})
	private String typeCard;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getTypeCard() {
		return typeCard;
	}

	public void setTypeCard(String typeCard) {
		this.typeCard = typeCard;
	}
	
	

}
