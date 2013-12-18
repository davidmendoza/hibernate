package com.hibernate.EmpProfile.Util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {
	
	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		Connection conn = session.connection();
		
		try{
			PreparedStatement ps = conn.prepareStatement("select count(*) as nextVal from EmployeeDetails");
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				int id = rs.getInt("nextVal") + 1;
				return id;
			}
		} catch (SQLException e) {
			throw new HibernateException(
			"Unable to generate custom Id");
			}
		return null;
	}
	
	
}
