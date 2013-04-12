package com.crazy.pss.uc.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_UC_PARTY_TYPE")
public class PartyType {


	private String id;
	private String name;
	private String typeCode;
	private Set<PartyStructType> partyStructTypePs;
	private Set<PartyStructType> partyStructTypeCs;

	@Id
	@Column(name="ID", length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="TYPE_CODE", length=20)
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@OneToMany(mappedBy="partyTypeP")
	public Set<PartyStructType> getPartyStructTypePs() {
		return partyStructTypePs;
	}

	public void setPartyStructTypePs(Set<PartyStructType> partyStructTypePs) {
		this.partyStructTypePs = partyStructTypePs;
	}

	@OneToMany(mappedBy="partyTypeC")
	public Set<PartyStructType> getPartyStructTypeCs() {
		return partyStructTypeCs;
	}

	public void setPartyStructTypeCs(Set<PartyStructType> partyStructTypeCs) {
		this.partyStructTypeCs = partyStructTypeCs;
	}

}
