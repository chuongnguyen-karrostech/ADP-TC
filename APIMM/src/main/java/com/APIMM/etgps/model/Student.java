package com.APIMM.etgps.model;
// Generated Nov 14, 2017 1:55:59 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Student generated by hbm2java
 */
@Entity
@Table(name = "Student")
public class Student implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentId id;

	public Student() {
	}

	public Student(StudentId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "studentId", column = @Column(name = "StudentID", length = 50)),
			@AttributeOverride(name = "status", column = @Column(name = "Status", length = 50)),
			@AttributeOverride(name = "latitude", column = @Column(name = "Latitude", precision = 53, scale = 0)),
			@AttributeOverride(name = "longitude", column = @Column(name = "Longitude", precision = 53, scale = 0)),
			@AttributeOverride(name = "writeTimeStamp", column = @Column(name = "WriteTimeStamp", length = 23)),
			@AttributeOverride(name = "positionTimeStamp", column = @Column(name = "PositionTimeStamp", length = 23)),
			@AttributeOverride(name = "unitId", column = @Column(name = "UnitID")) })
	public StudentId getId() {
		return this.id;
	}

	public void setId(StudentId id) {
		this.id = id;
	}

}
