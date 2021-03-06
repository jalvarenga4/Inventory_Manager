package invmanger;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * author: Nick Bishop & Jaime Alvarenga
 */
public class Controller {
	static Scanner input = new Scanner(System.in);

    public void changePassword(String password){
        Data.password = password;
    }

    /**
     * call Controller.login in the start of Manager. If the password is correct it will
     * simply proceed with manager.
     */
    //Views-----------------------------------------------------------------------------------------
    public static void viewProducts(){
    	clrscr();
		if(Data.productArr.isEmpty()) {
			System.out.println("You must add a Product before you can view them");
			pressAny();
		}
		else {
			System.out.println("--------------------");
			System.out.println("View All Products");
			System.out.println("--------------------\n");
			displayTablenonum(Data.warehouseArr);
			pressAny();
		}
    }
    public static void viewUnderstockedProducts(){
    	clrscr();
		if(Data.productArr.isEmpty()) {
			System.out.println("You must add a Product before you can view understocked products");
			pressAny();
		}
		else {
			System.out.println("--------------------");
			System.out.println("Understocked Products");
			System.out.println("--------------------\n");
			for (int i = 0; i < Data.warehouseArr.size(); i++) {
				System.out.print(Data.warehouseArr.get(i).listLowStock());
				System.out.println("\n");
			}
			pressAny();
		}
    }
    public static void viewEmployeeSales(){
    	System.out.printf("%-30s|%-11s","Name","Total Sales");
		System.out.print("\n");
		for (int i = 0; i < Data.employeeArr.size(); i++) {
			System.out.printf("%-30s|%-11s", Data.employeeArr.get(i).getName() ,Data.employeeArr.get(i).getSales());
			System.out.print("\n");
		}
		System.out.print("\n");
		pressAny();
    }
    public static void viewActiveInvoices(){
   	 ArrayList<Invoice> invoiceList = Data.invoiceArr; 
	        int x = 0;
	        for (int i = 0; i < invoiceList.size(); i++) {
	            if(invoiceList.get(i).isActiveStatus()) //if the invoice is active
	            System.out.print(x + ") ");
	            System.out.println(invoiceList.get(i));
	            x++;
	        }
		pressAny();
   }

   public static void viewArchivedInvoices(){
   	ArrayList<Invoice> invoiceList = Data.invoiceArr; 
       int x = 0;
       for (int i = 0; i < invoiceList.size(); i++) {
           if(!invoiceList.get(i).isActiveStatus()) //if the invoice is not active
           System.out.print(x + ") ");
           System.out.println(invoiceList.get(i));
           x++;
       }
       pressAny();
   }
   //ADDS----------------------------------------------------------------------------------------------------------------
    public static void addProduct(){
    	Controller.clrscr();
		if(Data.warehouseArr.isEmpty()) {
			System.out.println("You must add a Warehouse before you can add a Product.");
			pressAny();
		}
		else {
			System.out.println("--------------------");
			System.out.println("Adding New Products");
			System.out.println("--------------------\n");
			System.out.print("Please enter Product's Name.\nName: ");
			String pname = input.nextLine();
			System.out.print("\nPlease enter Cost Price\nCost Price: ");
			Float pcost = input.nextFloat();
			input.nextLine();
			System.out.print("\nPlease enter Sale Price\nSale Price: ");
			Float psale = input.nextFloat();
			input.nextLine();
			System.out.print("\nPlease enter Category Name.\nCategory:  ");
			String pcat = input.nextLine();
			clrscr();
			System.out.println("Warehouses ------------------------------------------------");
			System.out.print("\n");
			displayTable(Data.warehouseArr);
			System.out.println("-----------------------------------------------------------");
			System.out.print("Please select the Warehouse that this product is located at.\nNumber:  ");
			Integer ware = input.nextInt();
			System.out.print("\n");
			Product prod = new Product(pname, pcost, psale, pcat);
			System.out.printf("%-30s|%-15s|%-15s|%-20s|%-15s|%-15s","Name","Cost","Price","Category","Amount Sold", "Warehouse");
			System.out.print("\n");
			System.out.println(prod + "|" + Data.warehouseArr.get(ware).getWarehouseID());
			System.out.print("\nDoes this information look correct? (Y/N)  ");
			String h = input.next();
			input.nextLine();
			if(h.equals("Y") || h.equals("y")) {
				Data.productArr.add(prod);
				int code = prod.getID();
				Data.warehouseArr.get(ware).addProduct(code);
				//Data.warehouseArr.get(ware).increaseStock(code, 10);
				Data.warehouseArr.get(ware).addProduct(Data.productArr.size()-1);
				System.out.println("New Product has been added to the file system.");
				pressAny();
			}
		}
    }
    public static void addWarehouse() {
		System.out.print("What is the warehouse's Name?\nName:  ");
		String wname = input.nextLine();
		System.out.print("\nWhat is the warehouse's Phone#?\nPhone#:  ");
		String wphone = input.nextLine();
		System.out.print("\nWhats is the warehouse's Address?\nAddress:  ");
		String waddress = input.nextLine();
		Warehouse ware = new Warehouse(wname, waddress, wphone);
		Data.warehouseArr.add(ware);
		System.out.println("The Warehouse has been added to the filesystem.");
		pressAny();
    }
    public static void addStock() {
    	if(Data.productArr.isEmpty()) {
    		System.out.println("You need to add a product before you can add stock");
    	}
    	else {
	    	System.out.println("    Warehouse Name");
			for (int i = 0; i < Data.warehouseArr.size(); i++) {
				System.out.print(i + ")  ");
				System.out.print(Data.warehouseArr.get(i).getWarehouseID());
				System.out.print("\n");
			}
			System.out.print("\nWhat Warehouse is this product located at.\nNumber:  ");
			Integer ware = input.nextInt();
			ArrayList<Product> prodlist = Data.warehouseArr.get(ware).getAllProducts();
			clrscr();
			System.out.println("Products at Warehouse: " + Data.warehouseArr.get(ware).getWarehouseID());
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-33s|%-15s|%-15s|%-20s|%-15s|%-15s","Name","Cost","Price","Category","Amount Sold", "In Stock");
			System.out.print("\n");
			for (int i = 0; i < Data.warehouseArr.get(ware).getAllProducts().size(); i++) {
	        	System.out.print(i + ") ");
				System.out.print(Data.warehouseArr.get(ware).getAllProducts().get(i));
				int code0 = prodlist.get(i).getID();
				System.out.print("|" + Data.warehouseArr.get(ware).getStock(code0));	        
				}
			System.out.print("\n");
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.print("\nPlease select the Product you would like to add stock to.\nNumber:  ");
			Integer num = input.nextInt(); 
			input.nextLine();
			System.out.print("\nHow much stock would you like to add.\nAmount:  ");
			Integer stock = input.nextInt();
			input.nextLine();
			int code = prodlist.get(num).getID();
			Data.warehouseArr.get(ware).increaseStock(code, stock);
			pressAny();
    	}
    }
    public static void addEmployee() {
    	System.out.print("Please enter Employee's Name.\nName: ");
		String ename = input.nextLine();
		System.out.print("\nPlease enter Employee's Phone#\nPhone Number: ");
		String ephone = input.nextLine();
		System.out.print("\nPlease enter Employee's Commissions.\nCommissions: ");
		Float commission = input.nextFloat();
		input.nextLine();
		Employee emplo = new Employee(commission, ename, ephone, 0);
		clrscr();
		System.out.printf("%-30s|%-11s|%-10s|%-1s","Name","Phone","Commi.","Total Sales");
		System.out.print("\n");
		System.out.println(emplo);
		System.out.print("\nDoes this information look correct? (Y/N)  ");
		String h = input.next();
		input.nextLine();
		if(h.equals("Y") || h.equals("y")) {
			Data.employeeArr.add(emplo);
			System.out.println("New Employee has been added to the filesystem.");
			pressAny();
		}
    }
    public static void addCustomer() {
    	String cname = input.nextLine();
		System.out.print("What is the Customer's Phone#?\nPhone: ");
		String cphone = input.nextLine();
		Customer cust = new Customer(5, cname, cphone, false, true);
		Data.customerArr.add(cust);
		System.out.println("Customer has successfully been added to the file system.");
		pressAny();
    }
    public static void addInvoice(int userInt){
    	List<Product> product =new LinkedList<Product>();
		float cost = 0;
		int out = 0;
		while(userInt != Data.productArr.size()) {
			clrscr();
			System.out.println("Please select a product from the list to add to the invoice\n");
			System.out.println("Products----------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%-33s|%-15s|%-15s|%-20s|%-15s","Name","Cost","Price","Category","Amount Sold");
			System.out.print("\n");
			displayTable(Data.productArr);
			System.out.print("\n");
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.print("Enter " + Data.productArr.size() + " to finish entering Products.\nNumber:  ");
			Integer num = input.nextInt();
			if(num >= 0 && num < Data.productArr.size()) {
				product.add(Data.productArr.get(num));
				cost = cost + Data.productArr.get(num).getSalePrice();
				System.out.println("Product has been added to the invoice.");
				out++;
				pressAny();
			}
			else if(out > 0 && num == Data.productArr.size()) {
				break;
			}
			else{
				clrscr();
				System.out.println("Invaild input, please enter a Product number.");
				pressAny();
			}
		}
		clrscr();
		System.out.print("Please enter the Month, Day and Year(ex. 01/01/2020).\nMonth:  ");
		Integer month = input.nextInt();
		System.out.print("Day:  ");
		Integer day = input.nextInt();
		System.out.print("Year:  ");
		Integer year = input.nextInt();
		LocalDate date1 = LocalDate.of(year, month, day);
		clrscr();
		System.out.println("Customers---------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-33s|%-11s|%-10s|%-17s|%-5s", "Name", "Phone", "Sales Tax", "Suspension Status", "Active Status");
		System.out.print("\n");
		displayTable(Data.customerArr);
		System.out.print("\n");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.print("Please select a customer to add to this Invoice.\nNumber:  ");
		Customer customer = Data.customerArr.get(input.nextInt());
		clrscr();
		System.out.println("Employee----------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-30s|%-11s|%-10s|%-1s", "Name", "Phone", "Commission", "Total Sales");
		System.out.print("\n");
		displayTable(Data.employeeArr);
		System.out.print("\n");
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.print("Please select a employee to attach to the Invoice.\nNumber:  ");
		Employee employee = Data.employeeArr.get(input.nextInt());
		Invoice in = new Invoice(product, cost, date1, customer, employee);
		Data.invoiceArr.add(in);
		System.out.println("Invoice has succsusfully been added to the file system.");
		pressAny();
    }
    //Delete----------------------------------------------------------------------------------------------------
    public static void deleteProduct(int userInt){
    	clrscr();
		if(Data.productArr.isEmpty()) {
			System.out.println("You must add a Product before you can delete them");
			pressAny();
		}
		else {
			while(userInt != 0) {
				clrscr();
				//TODO breaks when there's no pdouct left
				System.out.println("--------------------");
				System.out.println("Delete Products");
				System.out.println("--------------------\n");
				displayTable(Data.productArr);
				System.out.print("\nPlease select an Number from the list to remove.\nNumber: ");
				Integer num = input.nextInt();
				System.out.print("\nAre you sure you want to delete this Employee?(Y/N)  ");
				String z = input.next();
				input.nextLine();
				if(z.equals("Y") || z.equals("y")) {
					remove(Data.productArr, num);
					System.out.println("Product has been deleted from the File System.");
					pressAny();
					break;
				}
			}
		}
    }
    
    public static void deleteEmployee() {
    	System.out.printf("%-30s|%-11s|%-10s|%-1s","Name","Phone","Commi.","Total Sales");
		System.out.print("\n");
		displayTable(Data.employeeArr);
		System.out.print("\nPlease select an Number from the list to remove.\nNumber: ");
		Integer num = input.nextInt();
		System.out.print("\nAre you sure you want to delete this Employee?(Y/N)  ");
		String y = input.next();
		input.nextLine();
		if(y.equals("Y") || y.equals("y")) {
			remove(Data.employeeArr, num);
			System.out.println("Employee has been deleted from the File System.");
			pressAny();
		}
    }
    //edits------------------------------------------------------------------------------------------------------------------
    public static void editProductDetails(int userInt, int num){
		if(userInt == 0) {
			clrscr();
			System.out.print("Please enter the new name for this Product:  ");
			String newname = input.next();
			input.nextLine();
			Data.productArr.get(num).setName(newname);
			System.out.println("Name has successfully been changed to " + newname + "\n");
			System.out.printf("%-30s|%-15s|%-15s|%-20s|%-15s","Name","Cost","Price","Category","Amount Sold");
			System.out.print("\n");
			System.out.println(Data.productArr.get(num) + "\n");
			pressAny();
		}
		if(userInt == 1) {
			clrscr();
			System.out.print("Please enter the new Cost Price for this Product");
			Float newcost = input.nextFloat();
			Data.productArr.get(num).setCostPrice(newcost);
			System.out.println("Cost Price has successfully been changed to " + newcost + "\n");
			System.out.printf("%-30s|%-15s|%-15s|%-20s|%-15s","Name","Cost","Price","Category","Amount Sold");
			System.out.print("\n");
			System.out.println(Data.productArr.get(num) + "\n");
			pressAny();
		}
		if(userInt == 2) {
			clrscr();
			System.out.print("Please enter the new Sale Price for this Product");
			Float newsale = input.nextFloat();
			Data.productArr.get(num).setSalePrice(newsale);
			System.out.println("Sale Price has successfully been changed to " + newsale + "\n");
			System.out.printf("%-30s|%-15s|%-15s|%-20s|%-15s","Name","Cost","Price","Category","Amount Sold");
			System.out.print("\n");
			System.out.println(Data.productArr.get(num) + "\n");
			pressAny();
		}
		if(userInt == 3) {
			clrscr();
			System.out.print("Please enter the new Category for this Product");
			String newcat = input.next();
			input.nextLine();
			Data.productArr.get(num).setCategory(newcat);;
			System.out.println("Category has successfully been changed to " + newcat + "\n");
			System.out.printf("%-30s|%-15s|%-15s|%-20s|%-15s","Name","Cost","Price","Category","Amount Sold");
			System.out.print("\n");
			System.out.println(Data.productArr.get(num) + "\n");
			pressAny();
		}
		if(userInt == 4) {
			clrscr();
			System.out.print("Please enter the new Quanity Sold for this Product");
			Integer newquan = input.nextInt();
			Data.productArr.get(num).setQuantitySold(newquan);
			System.out.println("Quanity Sold has successfully been changed to " + newquan + "\n");
			System.out.printf("%-30s|%-15s|%-15s|%-20s|%-15s","Name","Cost","Price","Category","Amount Sold");
			System.out.print("\n");
			System.out.println(Data.productArr.get(num) + "\n");
			pressAny();
		}
}
    public static void editEmployeeDetails(int userInt, int num){
    	if(userInt == 0) {
			clrscr();
			System.out.print("Please enter the new name for this Employee:  ");
			String newname = input.nextLine();
			Data.employeeArr.get(num).setName(newname);
			System.out.println("Name has successfully been changed to " + newname + "\n");
			System.out.printf("%-30s|%-11s|%-10s|%-1s","Name","Phone","Commi.","Total Sales");
			System.out.print("\n");
			System.out.println(Data.employeeArr.get(num) + "\n");
			pressAny();
		}
		else if(userInt == 1) {
			clrscr();
			System.out.print("Please enter the new Phone Number for this Employee:  ");
			String newPhone = input.next();
			input.nextLine();
			Data.employeeArr.get(num).setPhone(newPhone);
			System.out.println("Phone Number has successfully been changed to " + newPhone + "\n");
			System.out.printf("%-30s|%-11s|%-10s|%-1s","Name","Phone","Commi.","Total Sales");
			System.out.print("\n");
			System.out.println(Data.employeeArr.get(num) + "\n");
			pressAny();
		}
		else if(userInt == 2) {
			clrscr();
			System.out.print("Please enter the new Commission Number for this Employee:  ");
			Float newcom = input.nextFloat();
			Data.employeeArr.get(num).setCommission(newcom);
			System.out.println("Employee's Commission has successfully been changed to " + newcom + "\n");
			System.out.printf("%-30s|%-11s|%-10s|%-1s","Name","Phone","Commi.","Total Sales");
			System.out.print("\n");
			System.out.println(Data.employeeArr.get(num) + "\n");
			pressAny();
		}
    }
    public static void editCustomerDetails(int userInt, int num){
    	if(userInt == 0) {
			clrscr();
			System.out.print("Please enter the new name for this customer:  ");
			String newname = input.nextLine();
			Data.customerArr.get(num).setName(newname);
			System.out.println("Name has successfully been changed to " + newname + "\n");
			System.out.printf("%-30s|%-11s|%-1s|%-5s|%-5s","Name","Phone","Sales Tax.", "Suspension Status", "Active Status");
			System.out.print("\n");
			System.out.println(Data.customerArr.get(num) + "\n");
			pressAny();
		}
		if(userInt == 1) {
			clrscr();
			System.out.print("Please enter the new Phone Number for this customer:  ");
			String newphone = input.next();
			input.nextLine();
			Data.customerArr.get(num).setPhone(newphone);
			System.out.println("Phone Number has successfully been changed to " + newphone + "\n");
			System.out.printf("%-30s|%-11s|%-1s|%-5s|%-5s","Name","Phone","Sales Tax.", "Suspension Status", "Active Status");
			System.out.print("\n");
			System.out.println(Data.customerArr.get(num) + "\n");
			pressAny();
		}
		if(userInt == 2) {
			clrscr();
			System.out.print("Would you like to flip the Suspension Status(Y/N):  ");
			String newsus = input.next();
			input.nextLine();
			if(newsus.equals("y") || newsus.equals("Y")) {
				Data.customerArr.get(num).flipSuspensionStatus();
				System.out.println("SuspensionStatus is now set to " + Data.customerArr.get(num).getSuspensionStatus());
				pressAny();
			}
		}
		if(userInt == 3) {
			clrscr();
			System.out.print("Would you like to flip the Active Status(Y/N):  ");
			String newsus = input.next();
			input.nextLine();
			if(newsus.equals("y") || newsus.equals("Y")) {
				Data.customerArr.get(num).flipActiveStatus();;
				System.out.println("SuspensionStatus is now set to " + Data.customerArr.get(num).getActiveStatus());
				pressAny();
			}
		}
    }
    
    //Non-visable functions-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void clrscr(){
	    //Clears Screen
	    try {
	    	//For windows
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        //For macOS and linux
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException ex) {}
	}
    
    public static void pressAny()
	 { 
	        System.out.println("Press Enter key to continue...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }
    
    public static void displayTable(ArrayList<?> x) {
        for (int i = 0; i < x.size(); i++) {
        	System.out.print(i + ") ");
			System.out.println(x.get(i));
			if(x == Data.warehouseArr) {
				System.out.print("\n");
			}
        }
    }
    
	public static void displayTablenonum(ArrayList<?> x) {
		 for (int i = 0; i < x.size(); i++) {
				System.out.println(x.get(i));
				System.out.print("\n");
	        }
	}
	
	public static void remove(ArrayList<?> x, int i) {
		x.remove(i);
	}
	
	 public static void login() {
	        @SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
	        String inputPassword = "";
	        if(Data.password == "") {

	            System.out.print("Enter a new Password:  ");
	            Data.password = input.nextLine();
	            Data.savePassword();
	        }
	        else{
	            while(!Data.password.equals(inputPassword)) {
	                System.out.print("Enter Password:  ");
	                inputPassword = input.nextLine();
	            }
	        }
	    }
	 
	 public static boolean passwordValidation(String inputPassword) {
      if(inputPassword.equals(Data.password)) {
          return true;
      }else {
          return false;
      }
  }
	 
  public static void setPassword(String newPassword) {
      Data.password = newPassword;
      Data.savePassword();
  }
  
  public static void loadeverything() {
	  File pass = new File("password.ser");
	  File prod = new File("product.ser");
	  File ware = new File("warehouse.ser");
	  File ID = new File("ID.ser");
	  File date = new File("date.ser");
	  File invoice = new File("invoice.ser");
	  File cus = new File("customer.ser");
	  File emp = new File("employee.ser");
	  if(pass.exists()) {
		  Data.loadPassword();
	  }
	  if(prod.exists()) {
		  Data.loadProduct();
	  }
	  if(ware.exists()) {
		  Data.loadWarehouse();
	  }
	  if(ID.exists()) {
		  Data.loadIDCounter();
	  }
	  if(date.exists()) {
		  Data.loadDate();
	  }
	  else {
		  Data.lastLaunch = LocalDate.now();
	  }
	  if(invoice.exists()) {
		  Data.loadInvoice();
	  }
	  if(cus.exists()) {
		  Data.loadCustomer();
	  }
	  if(emp.exists()) {
		  Data.loadEmployee();
	  }
  }
  
  public static void compoundTotal() {
      LocalDate currentDate = LocalDate.now();
      if(!currentDate.isEqual(Data.lastLaunch)) {
          //program has not been run today
          for (int i = 0; i < Data.invoiceArr.size(); i++) {
              boolean isactive = Data.invoiceArr.get(i).isActiveStatus();
              boolean ispastdue = currentDate.minusDays(30).isAfter(Data.invoiceArr.get(i).getDateIssued());
              if(isactive && ispastdue) {
                  Data.invoiceArr.get(i).compoundtotal();
              }
          }
          Data.lastLaunch = currentDate;
      }
  }

  public static void printinvoiceProdTable(int x) {
      System.out.print(String.format("%nProducts:%n%-20s|%-15s|%-15s", "Product Name", "Product ID", "Product Price"));
      System.out.print("\n");
      Data.invoiceArr.get(x).showproductTable();
  }
}
