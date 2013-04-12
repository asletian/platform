package com.crazy.pss.uc.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @Description 部门/人员/职位等父类
 * 
 * @author crazy/Y
 * @date 2013-4-4
 */
@Entity
@Table(name="T_UC_PARTY")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="PARTY")
public class Party {

	private String id;
	private String name;
	private Integer status;
	private Date createTime;
	private Date endTime;
	private String description;
	private Set<PartyStruct> partyStructPs;
	private Set<PartyStruct> partyStructCs;
	private PartyType partyType;

	@Id
	@Column(length=32, name="ID")
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

	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy="partyP")
	public Set<PartyStruct> getPartyStructPs() {
		return partyStructPs;
	}

	public void setPartyStructPs(Set<PartyStruct> partyStructPs) {
		this.partyStructPs = partyStructPs;
	}

	@OneToMany(mappedBy="partyC")
	public Set<PartyStruct> getPartyStructCs() {
		return partyStructCs;
	}

	public void setPartyStructCs(Set<PartyStruct> partyStructCs) {
		this.partyStructCs = partyStructCs;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARTY_TYPE_ID")
	public PartyType getPartyType() {
		return partyType;
	}

	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}
}