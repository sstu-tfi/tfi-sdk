package ru.sstu.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import ru.sstu.dao.domain.Role;

/**
 * {@code EnumDaoTest} class is unit test for {@link EnumDao}.
 *
 * @author Denis_Murashev
 * @since DAO 1.0.1
 */
@SpringApplicationContext("applicationContext-test.xml")
public class EnumDaoTest extends UnitilsJUnit4 {

	@SpringBean("roleDao")
	private EnumDao<Role> roleDao;

	/**
	 * Tests Spring IoC working.
	 */
	@Test
	public void testIoC() {
		Assert.assertNotNull(roleDao);
	}

	/**
	 * Unit test for {@link ru.sstu.dao.EnumDao#find()} method.
	 */
	@Test
	public void testFind() {
		List<Role> actual = roleDao.find();
		List<Role> expected = Arrays.asList(Role.values());
		Assert.assertEquals(expected, actual);
	}

	/**
	 * Unit test for {@link ru.sstu.dao.EnumDao#findById(java.io.Serializable)}
	 * method.
	 */
	@Test
	public void testFindById() {
		Role actual = roleDao.findById(0);
		Role expected = Role.USER;
		Assert.assertEquals(expected, actual);
	}
}
