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
 * @Description PartyStruct, 用来实现Party的自身多对多关联，并对父子关联进行限制。
 * 
 * @author crazy/Y
 * @date 2013-4-4
 */
@Entity
@Table(name="T_UC_PARTY_STRUCT")
public class PartyStruct {
	
	private String id;
	private Party partyP;
	private Party partyC;
	private PartyStructType partyStructType;
	private TimeLimit timeLimit;

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
	@JoinColumn(name="PARTY_PARENT_ID")
	public Party getPartyP() {
		return partyP;
	}

	public void setPartyP(Party partyP) {
		this.partyP = partyP;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARTY_CHILDREN_ID")
	public Party getPartyC() {
		return partyC;
	}

	public void setPartyC(Party partyC) {
		this.partyC = partyC;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARTY_STRUCT_TYPE_ID")
	public PartyStructType getPartyStructType() {
		return partyStructType;
	}

	public void setPartyStructType(PartyStructType partyStructType) {
		this.partyStructType = partyStructType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TIME_LIMIT_ID")
	public TimeLimit getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(TimeLimit timeLimit) {
		this.timeLimit = timeLimit;
	}
}
