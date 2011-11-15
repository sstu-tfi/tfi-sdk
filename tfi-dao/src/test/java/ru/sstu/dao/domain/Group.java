package ru.sstu.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.sstu.dao.Identifiable;

/**
 * <code>Group</code> class.
 *
 * @author Denis_Murashev
 * @since DAO 1.0.1
 */
@Entity
@Table(name = "GROUPS")
public class Group implements Identifiable, Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 7879814596173794931L;

	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GROUP_ID_PK", nullable = false)
	private long id = -1L;

	/**
	 * Name.
	 */
	@Column(name = "GROUP_NAME", nullable = false)
	private String name;

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
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Long.valueOf(id).hashCode();
		return result + name.hashCode();
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
		if (!(obj instanceof Group)) {
			return false;
		}
		Group other = (Group) obj;
		return id == other.id && name.equals(other.name);
	}
}
