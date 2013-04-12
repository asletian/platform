package com.crazy.pss.uc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @Description 用来实现PartyType的自身多对多关联，并对父子关联进行限制。
 * 
 * @author crazy/Y
 * @date 2013-4-4
 */
@Entity
@Table(name="T_UC_PARTY_STRUCT_TYPE")
public class PartyStructType {
	

	private String id;
	private PartyType partyTypeP;
	private PartyType partyTypeC;

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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARTY_TYPE_PARENT_ID")
	public PartyType getPartyTypeP() {
		return partyTypeP;
	}

	public void setPartyTypeP(PartyType partyTypeP) {
		this.partyTypeP = partyTypeP;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARTY_TYPE_CHILDREN_ID")
	public PartyType getPartyTypeC() {
		return partyTypeC;
	}

	public void setPartyTypeC(PartyType partyTypeC) {
		this.partyTypeC = partyTypeC;
	}
}
