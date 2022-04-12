package manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectSQL.connect;
import infor.category;

public class cat_manager {
	public boolean addCategory(category cate) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		PreparedStatement add = null;
		ResultSet rs = null;
		con = connect.Database();
		cate.inputData();
		try {
			add = con.prepareStatement("INSERT into category (id, name, status, parentId) VALUES (?,?,?,?)");
			add.setString(1, cate.getId());
			add.setString(2, cate.getName());
			add.setInt(3, cate.isStatus() == true ? 1 : 0);
			add.setString(4, cate.getParentId());
			int i = add.executeUpdate();
			if (i > 0) {
				status = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public static List<category> getAll() throws ClassNotFoundException {
		Connection con;
		PreparedStatement getAll = null;
		ResultSet rs = null;
		List<category> list = new ArrayList<category>();
		con = connect.Database();
		try {
			getAll = con.prepareStatement("SELECT * FROM category");
			rs = getAll.executeQuery();
			while (rs.next()) {
				category cate = new category();
				cate.setId(rs.getString("id"));
				cate.setName(rs.getString("name"));
				cate.setStatus(rs.getInt("status") == 1 ? true : false);
				cate.setParentId(rs.getString("parentId"));
				list.add(cate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void getById(category cate, int Id) throws ClassNotFoundException {
		Connection con;
		PreparedStatement getById = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getById = con.prepareStatement("SELECT * FROM category WHERE id = ?");
			getById.setInt(1, Id);
			rs = getById.executeQuery();
			while (rs.next()) {
				cate.setId(rs.getString("id"));
				cate.setName(rs.getString("name"));
				cate.setStatus(rs.getInt("status") == 1 ? true : false);
				cate.setParentId(rs.getString("parentId"));
				cate.displayData();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean DeleteById(category cate, int Id) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		PreparedStatement DeleteById = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			DeleteById = con.prepareStatement("DELETE FROM category WHERE id = ?");
			DeleteById.setInt(1, Id);
			int i = DeleteById.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.println("Xóa danh mục thành công");
			} else {
				System.err.println("Xóa danh mục không thành công do danh mục đã có sản phẩm");
			}

		} catch (SQLException e) {
			System.out.println("\t Ối dồi ôi ! Lỗi");
			e.printStackTrace();
		}
		return status;
	}

	public void getByName(category cate, String Name) throws ClassNotFoundException {
		Connection con;
		PreparedStatement getByName = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getByName = con.prepareStatement("SELECT * FROM category WHERE name LIKE ?");
			getByName.setString(1, Name);
			rs = getByName.executeQuery();
			while (rs.next()) {
				cate.setId(rs.getString("id"));
				cate.setName(rs.getString("name"));
				cate.setStatus(rs.getInt("status") == 1 ? true : false);
				cate.setParentId(rs.getString("parentId"));
				cate.displayData();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public List<category> getAll1() throws ClassNotFoundException {
		Connection con;
		PreparedStatement getAll = null;
		ResultSet rs = null;
		con = connect.Database();
		List<category> List = new ArrayList<category>();
		try {
			getAll = con.prepareStatement("SELECT * FROM category");
			rs = getAll.executeQuery();
			while (rs.next()) {
				category cate = new category();
				cate.setId(rs.getString("id"));
				cate.setName(rs.getString("name"));
				cate.setParentId(rs.getString("parentId"));
				List.add(cate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return List;
	}

}
