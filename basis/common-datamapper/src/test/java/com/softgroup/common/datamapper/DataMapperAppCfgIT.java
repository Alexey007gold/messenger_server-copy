package com.softgroup.common.datamapper;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author odin
 * @since 15.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DataMapperAppCfgIT {

	@Autowired
	private DataMapper dataMapper;

	@Test
	public void test() {
		assertThat(dataMapper, CoreMatchers.notNullValue());
		assertEquals(JacksonDataMapper.class, dataMapper.getClass());
	}
}
