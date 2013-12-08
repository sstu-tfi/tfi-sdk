package ru.sstu.dao.impl;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ru.sstu.dao.AbstractDao;
import ru.sstu.dao.domain.Group;

/**
 * {@code OrderedGroupDaoImpl} class.
 *
 * @author Denis_Murashev
 * @since DAO 1.1
 */
@Repository("orderedGroupDao")
class OrderedGroupDaoImpl extends AbstractDao<Group> {

	@Resource
	private HibernateTemplate testTemplate;

	private OrderedGroupDaoImpl() {
		super(Order.desc("name"));
	}

	@Override
	protected HibernateTemplate getTemplate() {
		return testTemplate;
	}
}
