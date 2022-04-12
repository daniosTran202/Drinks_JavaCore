package infor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connectSQL.connect;

public class category {
	protected String id;
	protected String name;
	protected boolean status;
	protected String parentId;

	public category(String id, String name, boolean status, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.parentId = parentId;
	}

	public category() {
		super();
	}

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void inputData() throws ClassNotFoundException {
		ValidateId();
		ValidateName();
		ValidateStatus();
		ValidateParentId();
	};

	public void ValidateId() throws ClassNotFoundException {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập mã danh mục: ");
			String str = sc.nextLine();
			if (str.length() > 0) {
				this.id = str;
				if (CheckId(this.id) == false) {
					status = false;
				} else {
					System.out.println("\t[----Mã danh mục đã tồn tại!----]");
				}
			} else {
				System.out.println("\t[----Mã danh mục không được để trống!----]");
			}
		}

	}

	public void ValidateName() {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập tên danh mục: ");
			this.name = sc.nextLine();
			if (this.name.length() >= 6 && this.name.length() <= 30) {
				status = false;
				break;
			} else {
				System.out.println("\t[----Tên danh mục ít nhất 6 ký tự!----]");
			}
		}
	}

	public void ValidateStatus() {
		Pattern pattern = Pattern.compile("[0-1]");
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập trạng thái danh mục (1-còn hàng | 0-hết hàng): ");
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

	public void ValidateParentId() {
		Scanner sc = new Scanner(System.in);
		boolean status = true;
		while (status) {
			System.out.printf("\tVui lòng nhập mã danh mục cha: ");
			String str = sc.nextLine();
			if (str.length() > 0) {
				this.parentId = str;
				status = false;
				break;

			} else {
				System.out.println("\t[----Mã danh mục cha không được để trống!----]");
			}
		}
	}
	

	public void displayData() {
		System.out.printf("%-5s%-15s%-15s%-5s\n", this.id, this.name, this.status == true ? "Còn hàng" : "Hết hàng",
				this.parentId);
	}

	public boolean CheckId(String id2) throws ClassNotFoundException {
		int i = 0;
		boolean status = false;
		Connection con;
		PreparedStatement CheckId = null;
		ResultSet rs = null;
		con = connect.Database();
		try {
			CheckId = con.prepareStatement("select id from category where id = ?");
			CheckId.setString(1, id2);
			rs = CheckId.executeQuery();
			while (rs.next()) {
				i = rs.getInt("id");
			}
			if (i > 0) {
				status = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}