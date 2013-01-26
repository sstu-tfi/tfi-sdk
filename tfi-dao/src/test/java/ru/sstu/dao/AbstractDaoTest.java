package ru.sstu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.hibernate.HibernateUnitils;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import ru.sstu.dao.domain.Group;
import ru.sstu.dao.domain.Person;

/**
 * {@code AbstractDaoTest} class is unit test for {@link AbstractDao}.
 *
 * @author Denis_Murashev
 * @since DAO 1.0
 */
@SpringApplicationContext("applicationContext-test.xml")
public class AbstractDaoTest extends UnitilsJUnit4 {

	/**
	 * Group property.
	 */
	private static final String GROUP = "group";

	/**
	 * Expected groups.
	 */
	private static final GroupHolder[] GROUPS = {
		new GroupHolder(1, "A"),
		new GroupHolder(2, "B"),
	};

	/**
	 * Expected persons.
	 */
	private static final PersonHolder[] PERSONS = {
		new PersonHolder(1, "Aa", GROUPS[0].group),
		new PersonHolder(2, "Bb", GROUPS[0].group),
	};

	/**
	 * DAO for persons.
	 */
	@SpringBean("personDao")
	private AbstractDao<Person> personDao;

	/**
	 * DAO for groups.
	 */
	@SpringBean("groupDao")
	private AbstractDao<Group> groupDao;

	/**
	 * Tests Spring IoC working.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	public void testIoC() throws Exception {
		Assert.assertNotNull(personDao);
		Assert.assertNotNull(groupDao);
	}

	/**
	 * Tests Hibernate mapping.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	public void testMapping() throws Exception {
		HibernateUnitils.assertMappingWithDatabaseConsistent();
	}

	/**
	 * Tests common objects search.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testFind() throws Exception {
		List<Group> groups = groupDao.find();
		Assert.assertEquals(GROUPS.length, groups.size());
		for (int i = 0; i < GROUPS.length; i++) {
			Assert.assertEquals(GROUPS[i].group, groups.get(i));
		}
		List<Person> persons = personDao.find();
		Assert.assertEquals(PERSONS.length, persons.size());
		for (int i = 0; i < PERSONS.length; i++) {
			Assert.assertEquals(PERSONS[i].person, persons.get(i));
		}
		final long id = 1;
		Assert.assertEquals(GROUPS[0].group, groupDao.findById(id));
		Assert.assertEquals(PERSONS[0].person, personDao.findById(id));
	}

	/**
	 * Tests search by criteria.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testFindByCriteria() throws Exception {
		DetachedCriteria criteria = personDao.getCriteria()
				.add(Restrictions.eq(GROUP, GROUPS[0].group));
		Assert.assertEquals(PERSONS.length, personDao.list(criteria).size());
		criteria = personDao.getCriteria()
				.add(Restrictions.eq(GROUP, GROUPS[1].group));
		Assert.assertEquals(0, personDao.list(criteria).size());
	}

	/**
	 * Tests
	 * @link AbstractDao#unique(org.hibernate.criterion.DetachedCriteria)}
	 * method.
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testUnique() {
		DetachedCriteria criteria = personDao.getCriteria()
				.add(Restrictions.eq("id", 1L));
		Assert.assertEquals(PERSONS[0].person, personDao.unique(criteria));
		criteria = personDao.getCriteria()
				.add(Restrictions.eq("id", 0L));
		Assert.assertNull(personDao.unique(criteria));
	}

	/**
	 * Tests object saving.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testSave() throws Exception {
		Group group = new Group();
		group.setName("test");
		groupDao.save(group);
		Assert.assertEquals(GROUPS.length + 1, groupDao.find().size());
		long id = group.getId();
		Assert.assertEquals(group, groupDao.findById(id));

		Person person = new Person();
		person.setName("X");
		person.setGroup(group);
		groupDao.save(person);
	}

	/**
	 * Tests {@link AbstractDao#save(List)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testSaveAll() throws Exception {
		final int count = 3;
		List<Group> groups = new ArrayList<Group>();
		for (int i = 0; i < count; i++) {
			Group group = new Group();
			group.setName("test" + i);
			groups.add(group);
		}
		groupDao.save(groups);
		Assert.assertEquals(GROUPS.length + count, groupDao.find().size());
	}

	/**
	 * Tests object deleting.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testDelete() throws Exception {
		groupDao.delete(GROUPS[1].group);
		Assert.assertEquals(GROUPS.length - 1, groupDao.find().size());
	}

	/**
	 * Tests {@link AbstractDao#delete(Object)} method.
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testDeleteObject() {
		personDao.delete(GROUPS[1].group);
		Assert.assertEquals(GROUPS.length - 1, groupDao.find().size());
	}

	/**
	 * Tests {@link AbstractDao#delete(List)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testDeleteAll() throws Exception {
		List<Person> persons = new ArrayList<Person>();
		for (PersonHolder h : PERSONS) {
			persons.add(h.person);
		}
		personDao.delete(persons);
		Assert.assertEquals(0, personDao.find().size());
	}

	/**
	 * Tests search and delete by criteria.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testCriteria() throws Exception {
		DetachedCriteria criteria = personDao.getCriteria()
				.add(Restrictions.eq(GROUP, GROUPS[0].group));
		personDao.delete(criteria);
		Assert.assertEquals(0, personDao.find().size());
	}

	/**
	 * Tests aggregate functions.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testAggregate() throws Exception {
		DetachedCriteria criteria = personDao.getCriteria();
		criteria.setProjection(Projections.rowCount());
		Number actual = personDao.aggregate(criteria);
		Assert.assertEquals(PERSONS.length, actual.intValue());
	}

	/**
	 * Tests aggregate functions.
	 *
	 * @throws Exception if some error occurs
	 */
	@Test
	@DataSet("/dataset.xml")
	public void testHQL() throws Exception {
		List<Person> persons = personDao.hql("from Person person");
		Assert.assertEquals(PERSONS.length, persons.size());
		persons = personDao.hql("from Person person where person.id = ?", 1L);
		Assert.assertEquals(1, persons.size());
		persons = personDao.hql("from Person person "
				+ "where person.id = ? and person.name = ?", 1L, "Aa");
		Assert.assertEquals(1, persons.size());
	}

	private static final class GroupHolder {

		private Group group = new Group();

		private GroupHolder(long id, String name) {
			group.setId(id);
			group.setName(name);
		}
	}

	private static final class PersonHolder {

		private Person person = new Person();
	
		private PersonHolder(long id, String name, Group group) {
			person.setId(id);
			person.setName(name);
			person.setGroup(group);
		}
	}
}
