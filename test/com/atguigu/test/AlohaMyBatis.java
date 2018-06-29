package com.atguigu.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.atguigu.bean.Customer;
import com.atguigu.dao.CustomerDao;

public class AlohaMyBatis {
	CustomerDao mapper;
	SqlSession session;

	@Before
	public void prepared() throws IOException {
		// 1.创建SqlSessionFactory对象
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsStream("mybatis-config.xml"));
		// 2.开启会话
		session = sessionFactory.openSession();
		// 3.获取CustomerMapper对象
		mapper = session.getMapper(CustomerDao.class);
	}

	// 实验1：删除cust_id为8的Customer
	@Test
	public void testDeleteCustomerById() {
		int deleteCustomerById = mapper.deleteCustomerById(14);
		System.out.println(deleteCustomerById);

	}

	// 实验2：更新cust_id为10的Customer的姓名和年龄
	@Test
	public void testUpdateCustomerById() {
		Customer customer = new Customer(10, "小了点", 25);
		int updateCustomerById = mapper.updateCustomerById(customer);
		System.out.println(updateCustomerById);

	}

	// 实验3：查询cust_id为12的Customer
	@Test
	public void testSelectCustomerById() {
		Customer customer = mapper.selectCustomerById(12);
		customer.setCustId(12);
		System.out.println(customer);
	}

	// 实验4：查询List<Customer>，没有查询条件
	@Test
	public void testSelectAll1() {
		List<Map<String, Customer>> selectAll1 = mapper.selectAll1();
		for (Map<String, Customer> customer : selectAll1) {
			System.out.println(customer);
		}
	}

	// 实验5：查询List<Customer>，查询条件是 (以实体类对象形式封装查询条件)
	// cust_name包含'a'
	// cust_age大于20
	@Test
	public void testSelectAll2() {
		Customer customer = new Customer(null, "a", 20);
		List<Customer> selectAll2 = mapper.selectAll2(customer);
		for (Customer customer1 : selectAll2) {
			System.out.println(customer1);
		}
	}

	// 实验6：查询List<Customer>，查询条件是 (以Map类型的对象封装查询条件)
	// cust_name包含'a'
	// order_amount大于20

	@Test
	public void testSelectAllByNameAndAmount() {
		Map<String, Object> map = new HashMap<>();
		map.put("cust_name", "a");
		map.put("order_amount", 20);
		List<Customer> customers = mapper.selectAllByNameAndAmount(map);
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	// 实验7：查询List<Customer>，查询条件是 (传多个零散的参数)
	// cust_age在25~30之间
	@Test
	public void testSelectAllAmongAge() {
		List<Customer> customers = mapper.selectAllAmongAge(25, 30);
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	// 实验8：查询order_id为5的cust_name和order_name (将零散的查询结果封装到Map对象中返回)
	@Test
	public void testGetCustNameAndOrderNameByOrderId() {
		Map<String, String> custNameAndOrderName = mapper.getCustNameAndOrderNameByOrderId(5);
		System.out.println(custNameAndOrderName);
	}

	// 实验9：查询order_amount大于20的cust_name和order_name
	// (查询结果的一条记录对应一个Map对象,多条记录的结果可以使用List封装Map对象)
	@Test
	public void testGetCustNameAndOrderNameByOrderAmount() {
		List<Map<String, String>> custNameAndOrderNames = mapper.getCustNameAndOrderNameByOrderAmount(20);
		for (Map<String, String> map : custNameAndOrderNames) {
			System.out.println(map);
		}
	}

	// 实验10：查询cust_id为12的Customer，但是使用resultMap映射 (使用resultMap方式解决字段名与属性名不一致问题)
	@Test
	public void testGetCustomer() {
		Customer customer = mapper.getCustomer(12);
		System.out.println(customer);
	}
	
	// 实验11：查询结果为单值，统计Customer数量
		@Test
		public void testSelectCountCustomer() {
			 int count = mapper.selectCountCustomer();
			System.out.println(count);
		}
		
		// 实验12：执行DDL语句创建数据库表
		@Test
		public void testCreateTable() {
			mapper.createTable("tableName");
		}

	@Test
	public void test() {
		// 4.创建要保存的Customer对象
		Customer customer = new Customer(null, "newCust", 123);
		// 5.执行保存操作
		mapper.save(customer);
	}

	@After
	public void tail() {
		// 6.提交事务
		session.commit();
		// 7.关闭Session
		session.close();
	}

}
