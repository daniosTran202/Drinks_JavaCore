package infor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connectSQL.connect;
import manage.drinks_manager;

public class drinks {
	 private drinks_manager dm = new drinks_manager();
	 protected String id;
	 protected String name; 
	 protected boolean status ;
	 protected float price ;
	 protected String description ;
	 protected String expiration ;
	 protected String category_id ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public drinks() {
		super();
		// TODO Auto-generated constructor stub
	}
	public drinks(String id, String name, boolean status, float price, String description, String expiration,
			String category_id) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.price = price;
		this.description = description;
		this.expiration = expiration;
		this.category_id = category_id;
	}
	
	public void inputData() throws ClassNotFoundException {
		ValidateId();
		ValidateName();
		ValidateStatus();
		ValidatePrice();
		ValidateDescription();
		ValidateExpiration();
		ValidateCategoryId();
	};

	public void ValidateId() throws ClassNotFoundException {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập mã đồ uống: ");
			String str = sc.nextLine();
			if (str.length() > 0) {
				this.id = str;
				if (CheckId(this.id) == false) {
					status = false;
				} else {
					System.out.println("\t[----Mã đồ uống đã tồn tại!----]");
				}
			} else {
				System.out.println("\t[----Mã đồ uống không được để trống!----]");
			}
		}

	}

	public void ValidateName() {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập tên đồ uống: ");
			this.name = sc.nextLine();
			if (this.name.length() >= 6 && this.name.length() <= 30) {
				status = false;
				break;
			} else {
				System.out.println("\t[----Tên đồ uống ít nhất 6 ký tự!----]");
			}
		}
	}

	public void ValidateStatus() {
		Pattern pattern = Pattern.compile("[0-1]");
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập trạng thái đồ uống (1-còn hàng | 0-hết hàng): ");
			String str = sc.nextLine();
			if (str.length() > 0) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.matches()) {
					int tam = Integer.parseInt(str);
					if (tam == 1) {
						this.status = true;
						status = false;
						break;
					} else {
						this.status = false;
						status = false;
						break;
					}
				} else {
					System.out.printf("\t[----Phải là số 1 hoặc 0!----]\n");
				}
			} else {
				System.out.println("\t[----Trạng thái không được để trống!----]");
			}
		}
	}
	
	public void ValidatePrice() {
		Pattern pattern = Pattern.compile("[0-9]*.?[0-9]*");
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập giá đồ uống: ");
			String str = sc.nextLine();
			if(str.length() > 0) {
				Matcher matcher = pattern.matcher(str);
				if(matcher.matches()) {
	            	this.price = Float.parseFloat(str);
	            	if(this.price > 0) {
			            status = false;
			            break;
	            	}else {
	            		System.out.printf("\t[----Giá sản phẩm phải lớn hơn 0!----]\n");
					}

				}else {
					System.out.printf("\t[----Giá sản phẩm phải là số!----]\n");
				}
			}else {
				System.out.println("\t[----Giá sản phẩm không được để trống!----]");
			}
		}
	}
	
	public void displayData() throws ClassNotFoundException {
		System.out.printf("%-7s %-15s %-15s %-10.1f %-20s %-15s %-5s \n",this.id,this.name,this.status ? "Còn hàng" :"Hết hàng",this.price,this.description,this.expiration,dm.nameCategory(this.category_id));
	}
	
	public void ValidateDescription() {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập mô tả sản phẩm: ");
			String str = sc.nextLine();
			if(str.length() >0) {
				this.description = str;
				status = false;
				break;
			}else {
				System.out.println("\t[----Mô tả sản phẩm không được để trống!----]");
			}
		}
	}
	
	public void ValidateExpiration() {
		Scanner sc = new Scanner(System.in);
		Pattern pt = Pattern.compile("((20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])");
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập ngày hết hạn theo dạng (yyyy-MM-dd): ");
			String str = sc.nextLine();
			Matcher matcher = pt.matcher(str);
			if (str.length() > 0) {
				if (matcher.matches()) {
					status = false;
					this.expiration = str;
				} else {
					System.out.println("\t[----Vui lòng nhập theo định dạng (yyyy-MM-dd)!----]");
				}
			} else {
				System.out.println("\t[----Ngày hết hạn không được để trống!----]");
			}
		}
	}

	public void ValidateCategoryId() throws ClassNotFoundException {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		dm.getAllCategory();
		while (status) {
			System.out.printf("\tVui lòng nhập mã danh mục cha: ");
			String str = sc.nextLine();
			if (str.length() > 0) {
				this.category_id = str;
				if (CheckId(this.category_id) == false) {
					status = false;
				} else {
					System.out.println("\t[----Mã danh mục cha không tồn tại!----]");
				}
			} else {
				System.out.println("\t[----Mã danh mục không được để trống!----]");
			}
		}

	}
	
	
	public boolean CheckId(String Id) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		CallableStatement CheckId = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			CheckId = con.prepareCall("SELECT id FROM drinks WHERE id = ?");
			CheckId.setString(1, Id);
			rs = CheckId.executeQuery();
			if (rs.next()) {
				status = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public boolean CheckName(String Name) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		CallableStatement CheckName = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			CheckName = con.prepareCall("SELECT name FROM drinks WHERE name like ?");
			CheckName.setString(1, Name);
			rs = CheckName.executeQuery();
			if (rs.next()) {
				status = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public boolean CheckCategoryId(String str) throws ClassNotFoundException {
		boolean status = false;
		Connection con;
		CallableStatement CheckCateId = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			CheckCateId = con.prepareCall("select id from category where id = ?");
			CheckCateId.setString(1, str);
			rs = CheckCateId.executeQuery();
			if (!rs.next()) {
				status = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}


	
}
