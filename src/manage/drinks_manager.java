package manage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import connectSQL.connect;
import infor.category;
import infor.drinks;

public class drinks_manager {
	public void getAll(drinks p) throws ClassNotFoundException {
		Connection con;
		CallableStatement getAll = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getAll = con.prepareCall("SELECT * FROM drinks");
			rs = getAll.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setStatus(rs.getInt("status") == 1 ? true : false);
				p.setPrice(rs.getFloat("price"));
				p.setDescription(rs.getString("description"));
				p.setExpiration(rs.getString("expiration"));
				p.setCategory_id(rs.getString("category_id"));
				p.displayData();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getAllCategory() throws ClassNotFoundException {
		Connection con;
		CallableStatement getAll = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getAll = con.prepareCall("SELECT * FROM category");
			rs = getAll.executeQuery();
			System.out.print("\tCác danh mục có sẵn : ");
			while (rs.next()) {
				System.out.print("[" + rs.getString("id") + " : " + rs.getString("name") +"] ");
			}
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getExpiration(drinks p) throws ClassNotFoundException {
		Connection con;
		CallableStatement getAll = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			getAll = con.prepareCall("SELECT * FROM drinks");
			rs = getAll.executeQuery();
			while (rs.next()) {
				p.setExpiration(rs.getString("expiration"));
				LocalDate localDate = LocalDate.parse(p.getExpiration(), DateTimeFormatter.ISO_LOCAL_DATE);
				if (localDate.now().plusDays(3).equals(localDate)) {
					p.displayData();
					status = true;
				}
			}
			if (status == false) {
				System.out.println("Không có đồ uống nào hết hạn còn 3 ngày");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<drinks> getExp() throws ClassNotFoundException {
		Connection con;
		PreparedStatement exp = null;
		ResultSet rs = null;
		List<drinks> list = new ArrayList<drinks>();
		drinks dr = new drinks();
		con = connect.Database();
		try {
			exp = con.prepareStatement("SELECT *,DATEDIFF(DAY,getdate(),Expiration) as 'Day' FROM drinks WHERE DATEDIFF(DAY,getdate(),Expiration) <= 3");
			rs = exp.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				Boolean status = rs.getBoolean("status");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				String expiration = rs.getString("expiration");
				String category_id = rs.getString("category_id");
				drinks lim_exp = new drinks(id,name,status,price,description,expiration,category_id);
				list.add(lim_exp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	public boolean addDrinks(drinks p) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		CallableStatement add = null;
		ResultSet rs = null;
		con = connect.Database();
		p.inputData();
		try {
			add = con.prepareCall("INSERT into drinks values(?,?,?,?,?,?,?)");
			add.setString(1, p.getId());
			add.setString(2, p.getName());
			add.setInt(3, p.isStatus() == true ? 1 : 0);
			add.setFloat(4, p.getPrice());
			add.setString(5, p.getDescription());
			add.setString(6, p.getExpiration());
			add.setString(7, p.getCategory_id());
			int i = add.executeUpdate();
			if (i > 0) {
				System.out.printf("[----Thêm đồ uống thành công!----]\n");
				status = true;
			} else {
				System.out.printf("[----Thêm đồ uống không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public void getSortPrice(drinks p, float start, float end) throws ClassNotFoundException {
		Connection con;
		CallableStatement getSortPrice = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getSortPrice = con.prepareCall("SELECT * FROM drinks WHERE price between ? and ? ORDER BY price ASC ");
			getSortPrice.setFloat(1, start);
			getSortPrice.setFloat(2, end);
			rs = getSortPrice.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setStatus(rs.getInt("status") == 1 ? true : false);
				p.setPrice(rs.getFloat("price"));
				p.setDescription(rs.getString("description"));
				p.setExpiration(rs.getString("expiration"));
				p.setCategory_id(rs.getString("category_id"));
				p.displayData();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void getSortName(drinks p) throws ClassNotFoundException {
		Connection con;
		CallableStatement getSortName = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getSortName = con.prepareCall("SELECT * FROM drinks ORDER BY name ASC");
			rs = getSortName.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setStatus(rs.getInt("status") == 1 ? true : false);
				p.setPrice(rs.getFloat("price"));
				p.setDescription(rs.getString("description"));
				p.setExpiration(rs.getString("expiration"));
				p.setCategory_id(rs.getString("category_id"));
				p.displayData();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getByCategoryId(drinks p, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement getByCategoryId = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getByCategoryId = con.prepareCall("SELECT * FROM drinks WHERE category_id like ?");
			getByCategoryId.setString(1, "%" + Id + "%");
			rs = getByCategoryId.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setStatus(rs.getInt("status") == 1 ? true : false);  
				p.setPrice(rs.getFloat("price"));
				p.setDescription(rs.getString("description"));
				p.setExpiration(rs.getString("expiration"));
				p.setCategory_id(rs.getString("category_id"));
				p.displayData();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getByName(drinks p, String Name) throws ClassNotFoundException {
		Connection con;
		CallableStatement getByName = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getByName = con.prepareCall("SELECT * FROM drinks WHERE name like ?");
			getByName.setString(1, "%" + Name + "%");
			rs = getByName.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setStatus(rs.getInt("status") == 1 ? true : false);
				p.setPrice(rs.getFloat("price"));
				p.setDescription(rs.getString("description"));
				p.setExpiration(rs.getString("expiration"));
				p.setCategory_id(rs.getString("category_id"));
				p.displayData();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String nameCategory(String category_id) throws ClassNotFoundException {
		String catName = null ;
		Connection con;
		CallableStatement getNameCat = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getNameCat = con.prepareCall("SELECT * FROM category WHERE id like ?");
			getNameCat.setString(1,category_id);
			rs = getNameCat.executeQuery();
			while (rs.next()) {
				catName = rs.getString("name");
			}
		} catch (SQLException e) {
			System.out.println("Lỗi ối dồi ôi !");
			e.printStackTrace();
		}
		return catName;
	}

	public boolean getById(drinks p, String Id) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		CallableStatement getByName = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getByName = con.prepareCall("SELECT * FROM drinks WHERE id like ?");
			//rồi đó , siêu thật , đội ơn cậu
			getByName.setString(1, "%" + Id + "%");
			rs = getByName.executeQuery();
			while (rs.next()) {
				p.setId(rs.getString("id"));
				p.setName(rs.getString("name"));
				p.setStatus(rs.getInt("status") == 1 ? true : false);
				p.setPrice(rs.getFloat("price"));
				p.setDescription(rs.getString("description"));
				p.setExpiration(rs.getString("expiration"));
				p.setCategory_id(rs.getString("category_id"));
				p.displayData();
				status = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public void updateName(drinks p, String Name, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement UpdateName = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			UpdateName = con.prepareCall("UPDATE drinks SET name = ? WHERE id = ?");
			UpdateName.setString(1, Name);
			UpdateName.setString(2, Id);
			int i = UpdateName.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.printf("[----Sửa sản phẩm thành công!----]\n");
			} else {
				System.out.printf("[----Sửa sản phẩm không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateSatus(drinks p, int Status, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement UpdateStatus = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			UpdateStatus = con.prepareCall("UPDATE drinks SET status = ? where id = ?");
			UpdateStatus.setInt(1, Status);
			UpdateStatus.setString(2, Id);
			int i = UpdateStatus.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.printf("[----Sửa sản phẩm thành công!----]\n");
			} else {
				System.out.printf("[----Sửa sản phẩm không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatePrice(drinks p, float Price, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement UpdatePrice = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			UpdatePrice = con.prepareCall("UPDATE drinks SET price = ? where id = ?");
			UpdatePrice.setFloat(1, Price);
			UpdatePrice.setString(2, Id);
			int i = UpdatePrice.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.printf("[----Sửa sản phẩm thành công!----]\n");
			} else {
				System.out.printf("[----Sửa sản phẩm không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateDescription(drinks p, String Description, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement UpdateDescription = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			UpdateDescription = con.prepareCall("UPDATE drinks SET description = ? where id = ?");
			UpdateDescription.setString(1, Description);
			UpdateDescription.setString(2, Id);
			int i = UpdateDescription.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.printf("[----Sửa sản phẩm thành công!----]\n");
			} else {
				System.out.printf("[----Sửa sản phẩm không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateExpiration(drinks p, String Expiration, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement UpdateExpiration = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			UpdateExpiration = con.prepareCall("UPDATE drinks SET expiration = ? where id = ? ");
			UpdateExpiration.setString(1, Expiration);
			UpdateExpiration.setString(2, Id);
			int i = UpdateExpiration.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.printf("[----Sửa sản phẩm thành công!----]\n");
			} else {
				System.out.printf("[----Sửa sản phẩm không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCategoryId(drinks p, String CategoryId, String Id) throws ClassNotFoundException {
		Connection con;
		CallableStatement UpdateCategoryId = null;
		ResultSet rs = null;
		boolean status = false;
		con = connect.Database();
		try {
			UpdateCategoryId = con.prepareCall("UPDATE drinks SET category_id = ? where id = ? ");
			UpdateCategoryId.setString(1, CategoryId);
			UpdateCategoryId.setString(2, Id);
			int i = UpdateCategoryId.executeUpdate();
			if (i > 0) {
				status = true;
				System.out.printf("[----Sửa sản phẩm thành công!----]\n");
			} else {
				System.out.printf("[----Sửa sản phẩm không thành công!----]\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getCateId() throws ClassNotFoundException {
		Connection con;
		CallableStatement getCateId = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			getCateId = con.prepareCall("SELECT id FROM category");
			rs = getCateId.executeQuery();
			System.out.print("\t" + "Các mã danh mục hiện có: ");
			while (rs.next()) {
				System.out.print(rs.getString("id") + "-" + rs.getString("name") + " | ");
			}
			System.out.print("\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
