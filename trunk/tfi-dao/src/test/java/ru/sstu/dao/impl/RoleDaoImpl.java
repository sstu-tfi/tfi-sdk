package ru.sstu.dao.impl;

import org.springframework.stereotype.Repository;

import ru.sstu.dao.EnumDao;
import ru.sstu.dao.domain.Role;

/**
 * {@code RoleDaoImpl} class.
 *
 * @author Denis_Murashev
 * @since DAO 1.0.1
 */
@Repository("roleDao")
class RoleDaoImpl extends EnumDao<Role> {

	@Override
	public Role[] findAll() {
		return Role.values();
	}
}
