package ru.sstu.dao.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.sstu.dao.Identifiable;

/**
 * <code>Person</code> class.
 *
 * @author Denis_Murashev
 * @since DAO 1.0.1
 */
@Entity
@Table(name = "PERSONS")
public class Person implements Identifiable, Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -849603578092478266L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERSON_ID_PK", nullable = false)
	private long id;

	/**
	 * Name.
	 */
	@Column(name = "PERSON_NAME", nullable = false)
	private String name;

	/**
	 * Group.
	 */
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Group.class)
	@JoinColumn(name = "GROUP_ID_FK")
	private Group group;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Long.valueOf(id).hashCode();
		result = prime * result + name.hashCode();
		return prime * result + group.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		return id == other.id && name.equals(other.name)
				&& group.equals(other.group);
	}
}
