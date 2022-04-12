package Drinks_management;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import infor.*;
import manage.cat_manager;
import manage.drinks_manager;
import connectSQL.connect;

public class MainClass {
	List<category> lstCat;
	category cate = new category();
	cat_manager cm = new cat_manager();
	drinks p = new drinks();
	drinks_manager dm = new drinks_manager();
	
	private void expiration_lim() throws ClassNotFoundException {
		List<drinks> ld = dm.getExp();
		if(dm.getExp().size() == 0) {
			System.out.println("Không có sản phầm nào còn hạn sử dụng trong ba ngày");
		}else {
			HeaderSanPham();
			ld.stream().forEach(d -> {
				try {
					d.displayData();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
			});
		}
	}
	public static void main(String[] args) throws ClassNotFoundException {
		MainClass demo = new MainClass();
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-3]");
		int key = 0;
		do {
			demo.menu();
			boolean Status = true;
			while (Status) {
				System.out.print("Sự lựa chọn của bạn:");
				String str = sc.nextLine();
				Matcher matcher = pattern.matcher(str);
				if(str.length() > 0) {
					if(matcher.matches()) {
						key = Integer.parseInt(str);
						Status = false;
						break;
					}else {
		            	System.out.printf("[----Lựa chọn phải từ 1 đến 3!----]\n");
					}
				}else {
					System.out.println("[----Lựa trọn không được để trống!----]");
				}
			}
			switch (key) {
			case 1:
				demo.case1();
				break;
			case 2:
				demo.case2();
				break;
			default:
				System.out.println("[----Bạn đã thoát!----]");
				break;
			}
		} while (0<key && key<3);
	}
	
	
	public void HeaderDanhMuc() {
		System.out.printf("%-5s%-15s%-15s%-5s\n",
	            "Id","Name","Status","ParentId");
	}
	
	public void HeaderSanPham() {
		System.out.printf("%-7s %-15s %-15s %-10s %-20s %-15s %-5s\n",
	            "Id","Name","Status","Price","Description","Expiration","CategoryId");
	}
	
	public void menu() {
		System.out.println();
		System.out.println("**************** MENU ***************");
		System.out.println("1. Quản lý danh mục");
		System.out.println("2. Quản lý đồ uống");
		System.out.println("3. Thoát");
	}
	
	public void menu1() {
		System.out.println();
		System.out.println("**************** QUẢN LÝ DANH MỤC ***************");
		System.out.println("1. Danh sách danh mục");
		System.out.println("2. Thêm danh mục");
		System.out.println("3. Xóa danh mục");
		System.out.println("4. Tìm kiếm danh mục");
		System.out.println("5. Quay lại");
	}
	
	public void menu1_1() {
		System.out.println();
		System.out.println("**************** DANH SÁCH DANH MỤC ***************");
		System.out.println("1. Danh sách cây danh mục");
		System.out.println("2. Thông tin chi tiết danh mục");
		System.out.println("3. Quay lại");
	}
	
	public void menu2() {
		System.out.println();
		System.out.println("**************** QUẢN LÍ ĐỒ UỐNG ***************");
		System.out.println("1. Thêm đồ uống");
		System.out.println("2. Hiển thị đồ uống");
		System.out.println("3. Sắp xếp đồ uống");
		System.out.println("4. Cập nhập thông tin đồ uống");
		System.out.println("5. Quay lại");
	}
	
	public void menu2_2() {
		System.out.println();
		System.out.println("**************** THÔNG TIN ĐỒ UỐNG ***************");
		System.out.println("1. Hiển thị đồ uống theo danh mục");
		System.out.println("2. Hiển thị chi tiết đồ uống");
		System.out.println("3. Đồ uống quá hạn");
		System.out.println("4. Quay lại");
	}
	
	public void menu2_3() {
		System.out.println();
		System.out.println("**************** SẮP XẾP ***************");
		System.out.println("1. Sắp xếp theo khoảng giá");
		System.out.println("2. Sắp xếp theo tên tăng dần");
		System.out.println("3. Quay lại");
	}
	
	public void menu2_4() {
		System.out.println();
		System.out.println("**************** CẬP NHẬP THÔNG TIN ***************");
		System.out.println("1. Sửa tên đồ uống");
		System.out.println("2. Sửa trạng thái đồ uống");
		System.out.println("3. Sửa giá đồ uống");
		System.out.println("4. Sửa ngày hết hạn đồ uống");
		System.out.println("5. Sửa mô tả đồ uống");
		System.out.println("6. Sửa danh mục cha đồ uống");
		System.out.println("7. Quay lại");
	}
	
	private void treeList(String parentID, String subtitle) throws ClassNotFoundException {
		int level = 1;
		for (int i = 0; i < lstCat.size(); i++) {
			if (lstCat.get(i).getParentId().equals(parentID)) {
				System.out.println(subtitle + level + ". " + lstCat.get(i).getName());
				String sub = "\t" + subtitle + level + ".";
				
				treeList(lstCat.get(i).getId(), sub);
				level++;
			}
		}
	}
	
	public void DanhSachDanhMuc() throws ClassNotFoundException{
		
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-3]");
		int key = 0;
		do {
			menu1_1();
			boolean Status = true;
			while (Status) {
				System.out.print("Sự lựa chọn của bạn:");
				String str = sc.nextLine();
				Matcher matcher = pattern.matcher(str);
				if(str.length() > 0) {
					if(matcher.matches()) {
						key = Integer.parseInt(str);
						Status = false;
						break;
					}else {
		            	System.out.printf("[----Lựa chọn phải từ 1 đến 3!----]\n");
					}
				}else {
					System.out.println("[----Lựa trọn không được để trống!----]");
				}
			}
			switch (key) {
			case 1:
				System.out.println("CÂY DANH MỤC");
				lstCat = new cat_manager().getAll();
				treeList("0", "");
				System.out.println("\t\t------------------------");
				break;
			case 2:
				boolean Status1 = true;
				while (Status1) {
					Pattern pattern1 = Pattern.compile("\\d*");
					System.out.print("Nhâp mã danh mục cần tìm:");
					String str = sc.nextLine();
					//Matcher matcher = pattern1.matcher(str);
					if(str.length() > 0) {
							HeaderDanhMuc();
							cm.getByName(cate, str);;
							Status1 = false;
					}else {
						System.out.println("[---------Bạn chưa nhập mã danh mục!--------]");
					}
				}
				System.out.println("\t\t------------------------");
				break;
			default:
				System.out.println("[----Bạn đã thoát!----]");
				break;
			}
		} while (0<key && key<3);
	}
	
	public void case1() throws ClassNotFoundException {
		category cate = new category();
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-5]");
		int key = 0;
		do {
			menu1();
			boolean Status = true;
			while (Status) {
				System.out.print("Sự lựa chọn của bạn:");
				String str = sc.nextLine();
				Matcher matcher = pattern.matcher(str);
				if(str.length() > 0) {
					if(matcher.matches()) {
						key = Integer.parseInt(str);
						Status = false;
						break;
					}else {
		            	System.out.printf("[----Lựa chọn phải từ 1 đến 5!----]\n");
					}
				}else {
					System.out.println("[----Lựa trọn không được để trống!----]");
				}
			}
			switch (key) {
			case 1:
				DanhSachDanhMuc();
				break;
			case 2:
				int SoLuong = 0;
				boolean Status2 = true;
				while (Status2) {
					System.out.print("Số lượng danh mục cần thêm:");
					String str = sc.nextLine();
					Matcher matcher = pattern.matcher(str);
					if(str.length() > 0) {
						if(matcher.matches()) {
							SoLuong = Integer.parseInt(str);
							Status2 = false;
							break;
						}else {
			            	System.out.printf("[----Số lượng phải là số!----]\n");
						}
					}else {
						System.out.println("[----Số lượng không được để trống!----]");
					}
				}
				for (int i = 0; i < SoLuong; i++) {
					System.out.printf("Danh mục thứ %d:\n",i+1);
					cm.addCategory(cate);
				}
				break;
			case 3:
				boolean Status1 = true;
				while (Status1) {
					Pattern pattern1 = Pattern.compile("[1-9]*");
					System.out.print("Nhâp mã danh mục cần xóa:");
					String str = sc.nextLine();
					Matcher matcher = pattern1.matcher(str);
					if(str.length() > 0) {
						if(matcher.matches()) {
							int Id = Integer.parseInt(str);
							cm.DeleteById(cate, Id);
							Status1 = false;
						}else {
							System.out.println("[---------Mã danh mục phải là số và lớn hơn 0!---------]");
						}
					}else {
						System.out.println("[---------Bạn chưa nhập mã danh mục!--------]");
					}
				}
				break;
			case 4:
				boolean Status3 = true;
				while (Status3) {
					System.out.print("Nhâp tên danh mục cần tìm:");
					String str = sc.nextLine();
					if(str.length() > 0) {
						HeaderDanhMuc();
						cm.getByName(cate, str);
						Status3 = false;
					}else {
						HeaderDanhMuc();
						cat_manager.getAll();
						Status3 = false;
					}
				}
				System.out.println("\t\t------------------------");
				break;
			default:
				System.out.println("[----Bạn đã thoát!----]");
				break;
			}
		} while (0<key && key<5);
	}
	

	public void case2() throws ClassNotFoundException {
		
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-5]");
		int key = 0;
		do {
			menu2();
			boolean Status = true;
			while (Status) {
				System.out.print("Sự lựa chọn của bạn:");
				String str = sc.nextLine();
				Matcher matcher = pattern.matcher(str);
				if(str.length() > 0) {
					if(matcher.matches()) {
						key = Integer.parseInt(str);
						Status = false;
						break;
					}else {
		            	System.out.printf("[----Lựa chọn phải từ 1 đến 5!----]\n");
					}
				}else {
					System.out.println("[----Lựa trọn không được để trống!----]");
				}
			}
			switch (key) {
			case 1:
				int SoLuong = 0;
				boolean Status2 = true;
				while (Status2) {
					System.out.print("Số lượng đồ uống cần thêm:");
					String str = sc.nextLine();
					Matcher matcher = pattern.matcher(str);
					if(str.length() > 0) {
						if(matcher.matches()) {
							SoLuong = Integer.parseInt(str);
							Status2 = false;
							break;
						}else {
			            	System.out.printf("[----Số lượng phải là số!----]\n");
						}
					}else {
						System.out.println("[----Số lượng không được để trống!----]");
					}
				}
				for (int i = 0; i < SoLuong; i++) {
					System.out.printf("Sản phẩm thứ %d:\n",i+1);
					dm.addDrinks(p);
				}
				break;
			case 2:
				DanhSachDoUong();
				break;
			case 3:
				SortDrinks();
				break;
			case 4:
				UpdateDrinks();
				break;
			default:
				System.out.println("[----Bạn đã thoát!----]");
				break;
			}
		} while (0<key && key<5);
	}
	
	
	public void DanhSachDoUong() throws ClassNotFoundException{
		drinks p = new drinks();
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-4]");
		int key = 0;
		do {
			menu2_2();
			boolean Status = true;
			while (Status) {
				System.out.print("Sự lựa chọn của bạn:");
				String str = sc.nextLine();
				Matcher matcher = pattern.matcher(str);
				if(str.length() > 0) {
					if(matcher.matches()) {
						key = Integer.parseInt(str);
						Status = false;
						break;
					}else {
		            	System.out.printf("[----Lựa chọn phải từ 1 đến 4!----]\n");
					}
				}else {
					System.out.println("[----Lựa trọn không được để trống!----]");
				}
			}
			switch (key) {
			case 1:
				boolean Status1 = true;
				while (Status1) {
					Pattern pattern1 = Pattern.compile("\\d*");
					System.out.print("Nhâp mã danh mục đồ uống:");
					String str = sc.nextLine();
					Matcher matcher = pattern1.matcher(str);
					if(str.length() > 0) {
						if(matcher.matches()) {
							int Id = Integer.parseInt(str);
							HeaderSanPham();
							dm.getByCategoryId(p, str);
							Status1 = false;
						}else {
							System.out.println("[---------Mã danh mục phải là số và lớn hơn 0!---------]");
						}
					}else {
						HeaderSanPham();
						dm.getAll(p);
						Status1 = false;
					}
				}
				System.out.println("\t\t------------------------");
				break;
			case 2:
				boolean Status2 = true;
				while (Status2) {
					System.out.print("Nhâp tên đồ uống cần tìm:");
					String str = sc.nextLine();
					if(str.length() > 0) {
							HeaderSanPham();
							dm.getByName(p, str);;
							Status2 = false;
					}else {
						System.out.println("Không tìm thấy đồ uống nào có tên "+ str);
					}
				}
				break;
			case 3:
				System.out.println("Danh sách đồ uống có ngày hết hạn còn ba ngày :");
//				HeaderSanPham();
//				dm.getExpiration(p);
				expiration_lim();
				System.out.println("\t\t------------------------");
				break;
			default:
				System.out.println("[----Bạn đã thoát!----]");
				break;
			}
		} while (0<key && key<4);
	}
	
	public void SortDrinks() throws ClassNotFoundException{
		drinks p = new drinks();
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-3]");
		int key = 0;
		do {
			menu2_3();
			boolean Status = true;
			while (Status) {
				System.out.print("Sự lựa chọn của bạn:");
				String str = sc.nextLine();
				Matcher matcher = pattern.matcher(str);
				if(str.length() > 0) {
					if(matcher.matches()) {
						key = Integer.parseInt(str);
						Status = false;
						break;
					}else {
		            	System.out.printf("[----Lựa chọn phải từ 1 đến 3!----]\n");
					}
				}else {
					System.out.println("[----Lựa trọn không được để trống!----]");
				}
			}
			switch (key) {
			case 1:
				System.out.println("Danh sách đồ uống theo khoảng giá từ :");
				Float start = sc.nextFloat();
				System.out.println("Đến :");
				Float end = sc.nextFloat();
				System.out.println("Danh sách đồ uống theo khoảng giá từ [" + start +"] đến [" + end +"] là :");
				HeaderSanPham();
				dm.getSortPrice(p, start, end);
				System.out.println("\t\t------------------------");
				break;
			case 2:
				HeaderSanPham();
				dm.getSortName(p);
				System.out.println("\t\t------------------------");
				break;
			default:
				System.out.println("[----Bạn đã thoát!----]");
				break;
			}
		} while (0<key && key<3);
	}
	
	
	
	public void UpdateDrinks() throws ClassNotFoundException{
		drinks p = new drinks();
		Scanner sc = new Scanner(System.in); 
		Pattern pattern = Pattern.compile("[1-8]");
		Pattern pattern1 = Pattern.compile("[C]{1}\\w{3}");
		boolean Status2 = true;
		String Id = null;
		int key = 0;
		while (Status2) {
			System.out.print("Nhập mã đồ uống cần sửa:");
			Id = sc.nextLine();
			Matcher matcher1 = pattern1.matcher(Id);
			if(Id.length() > 0) {
				if(matcher1.matches()) {
					HeaderSanPham();
					if(dm.getById(p, Id) == true) {
						Status2 = false;
						do {
							menu2_4();
							boolean Status = true;
							while (Status) {
								System.out.print("Sự lựa chọn của bạn:");
								String strr = sc.nextLine();
								Matcher matcher = pattern.matcher(strr);
								if(Id.length() > 0) {
									if(matcher.matches()) {
										key = Integer.parseInt(strr);
										Status = false;
										break;
									}else {
						            	System.out.printf("[----Lựa chọn phải từ 1 đến 3!----]\n");
									}
								}else {
									System.out.println("[----Lựa trọn không được để trống!----]");
								}
							}
							switch (key) {
							case 1:
								String nameNew = null;
								boolean Status1 = true;
								while (Status1) {
									System.out.printf("\tVui lòng nhập tên đồ uống mới: ");
									nameNew = sc.nextLine();
									if(nameNew.length() >= 6 && nameNew.length() <= 50) {
										Status1 = false;
										break;
									}else {
										System.out.println("\t[----Tên sản phẩm mới ít nhất 6 ký tự!----]");
									}
								}
								dm.updateName(p, nameNew, Id);
								break;
							case 2:
								Pattern pattern2 = Pattern.compile("[0-1]");
								boolean Status3 = true;
								int tam = 0;
								while (Status3) {
									System.out.printf("\tVui lòng nhập trạng thái mới của đồ uống (1 - Còn hàng | 0 - Hết hàng): ");
									String status2 = sc.nextLine();
									if(Id.length() > 0) {
										Matcher matcher = pattern2.matcher(status2);
										if(matcher.matches()) {
											tam = Integer.parseInt(status2);
											Status3 = false;
										}else {
											System.out.printf("\t[----Phải là số 1 hoặc 2!----]\n");
										}
									}else {
										System.out.println("\t[----Trạng thái mới không được để trống!----]");
									}
								}
								dm.updateSatus(p, tam, Id);
								break;
							case 3:
								float PriceNew = 0;
								Pattern pattern3 = Pattern.compile("[0-9]*.?[0-9]*");
								boolean status = true;
								while (status) {
									System.out.printf("\tVui lòng nhập giá đồ uống mới: ");
									String str = sc.nextLine();
									if(str.length() > 0) {
										Matcher matcher = pattern3.matcher(str);
										if(matcher.matches()) {
							            	PriceNew = Float.parseFloat(str);
							            	if(PriceNew > 0) {
									            status = false;
									            break;
							            	}else {
							            		System.out.printf("\t[----Giá đồ uống mới phải lớn hơn 0!----]\n");
											}

										}else {
											System.out.printf("\t[----Giá đồ uống mới phải là số!----]\n");
										}
									}else {
										System.out.println("\t[----Giá đồ uống mới không được để trống!----]");
									}
								}
								dm.updatePrice(p, PriceNew, Id);
								break;
							case 4:
								String expiration = null;
								boolean status3 = true;
								while (status3) {
									System.out.printf("\tVui lòng nhập ngày hết hạn đồ uống mới: ");
									String str = sc.nextLine();
									if(str.length() >0) {
										expiration = str;
										status3 = false;
										break;
									}else {
										System.out.println("\t[----Ngày hết hạn đồ uống mới không được để trống!----]");
									}
								}
								dm.updateExpiration(p, expiration, Id);
								break;
							case 5:
								String description = null;
								boolean status5 = true;
								while (status5) {
									System.out.printf("\tVui lòng nhập mô tả đồ uống mới: ");
									String str = sc.nextLine();
									if(str.length() >0) {
										description = str;
										status5 = false;
										break;
									}else {
										System.out.println("\t[----Mô tả đồ uống mới không được để trống!----]");
									}
								}
								dm.updateDescription(p, description, Id);
								break;
							case 6:
								String category_id = null;
								boolean status6 = true;
								while (status6) {
									System.out.printf("\tVui lòng nhập danh mục cha đồ uống mới: ");
									String str = sc.nextLine();
									if(str.length() >0) {
										category_id = str;
										status6 = false;
										break;
									}else {
										System.out.println("\t[----Mô tả đồ uống mới không được để trống!----]");
									}
								}
								dm.updateCategoryId(p, category_id, Id);
								break;
							default:
								System.out.println("[----Bạn đã thoát!----]");
								break;
							}
						} while (0<key && key<7);
					}else {
						System.out.println("[----Mã đồ uống không tồn tại!----]");
					}
				}else {
					System.out.println("\t[----Mã đồ uống phải bắt đầu bằng chữ C và bao gồm 4 ký tự!----]");
				}
			}else {
				System.out.println("[----Mã đồ uống không được để trống!----]");
			}
		}

	}
	
	
}


