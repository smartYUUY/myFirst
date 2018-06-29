package com.atguigu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atguigu.bean.Customer;

public interface CustomerDao {
	// 实验0：保存Customer
	void save(Customer customer);

	// 实验1：删除cust_id为8的Customer
	int deleteCustomerById(int id);

	// 实验2：更新cust_id为10的Customer的姓名和年龄
	int updateCustomerById(Customer customer);
	
	// 实验3：查询cust_id为12的Customer
	@Select("select cust_name as custName, cust_age as custAge from tbl_cust where cust_id=#{id} ")
	Customer selectCustomerById(int id);
	
	// 实验4：查询List<Customer>，没有查询条件
	List<Map<String,Customer>> selectAll1();
	
	// 实验5：查询List<Customer>，查询条件是 (以实体类对象形式封装查询条件)
	// cust_name包含'a'
	// cust_age大于20
	List<Customer> selectAll2(Customer customer);

	// 实验6：查询List<Customer>，查询条件是 (以Map类型的对象封装查询条件)
	// cust_name包含'a'
	// order_amount大于20
	List<Customer> selectAllByNameAndAmount(Map<String,Object> map);
	
	// 实验7：查询List<Customer>，查询条件是 (传多个零散的参数)
	// cust_age在25~30之间
	List<Customer> selectAllAmongAge(@Param(value = "minAge") int minAge,@Param(value = "maxAge") int maxAge);
	
	// 实验8：查询order_id为5的cust_name和order_name (将零散的查询结果封装到Map对象中返回)
	Map<String,String> getCustNameAndOrderNameByOrderId(int id);
	
	// 实验9：查询order_amount大于20的cust_name和order_name
	// (查询结果的一条记录对应一个Map对象,多条记录的结果可以使用List封装Map对象)
	List<Map<String,String>> getCustNameAndOrderNameByOrderAmount(int amount);
	
	// 实验10：查询cust_id为12的Customer，但是使用resultMap映射 (使用resultMap方式解决字段名与属性名不一致问题)
	Customer getCustomer(int id);
	
	// 实验11：查询结果为单值，统计Customer数量
	int selectCountCustomer();
	
	// 实验12：执行DDL语句创建数据库表
	void createTable(@Param(value="tableName" )String tableName);
}
