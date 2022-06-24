package com.lockedme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;


public class LockerMain {
	static Scanner myobj;
	static List<Users> p1 = new ArrayList<Users>();
	static File dir;
	static File file;
	static ObjectOutputStream oos = null;
	static ObjectInputStream ois = null;
	static ListIterator<Users> li;
	public static final String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "datafiles";
	
	
	public static void main(String[] args) {
		initapplication();
		welcomeScreen();
		displaymain();
	}

	@SuppressWarnings("unchecked")
	public static void initapplication() {
		myobj = new Scanner(System.in);
		
		dir = new File("datafiles");
		dir.mkdir();
		file = new File("datafiles/userdb.txt");
		createDatabaseFile();

		if (file.isFile()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				p1 = (ArrayList<Users>) in.readObject();
				in.close();
			} catch (IOException e) {
//					System.out.println(e.getClass()+" "+e.getMessage()+"error1");
				System.out.println("No registerd user yet!!");
			} catch (ClassNotFoundException e) {
				System.out.println(e.getClass() + " " + e.getMessage());
			}
		} else {
			System.out.println(" No registerd user yet!!");
		}
	}

	public static void welcomeScreen() {
		System.out.println("**************************************************");
		System.out.println("            Welcome to LockMe.com \n");
		System.out.println("**************************************************");
		System.out.println("         YOUR SMART DIGITAL LOCKER SYSTEM   ");
		System.out.println(" \n           Developed by Milind Dixit ");
		System.out.println("**************************************************");
		System.out.println("                  ---><><--- ");
	}

	public static void exitScreen() {
		System.out.println("\n*************************************************");
		System.out.println("                                               ");
		System.out.println("      THANK YOU FOR VISITING LOCKME.COM         ");
		System.out.println("                                               ");
		System.out.println("*************************************************");
		System.out.println("\n\n");
	}

	public static void displaymain() {
		boolean looping = true;
		do {
			try {
				System.out.println("\n=================================================");
				System.out.println("            ---<>< MAIN MENU ><>---          ");
				System.out.println("=================================================");
				System.out.println("  Select any one of the following:  ");
				System.out.println("  1.  Register ");
				System.out.println("  2.  Login ");
				System.out.println("  3.  Exit");
				System.out.println("=================================================");
				System.out.print("  ::> Enter your choice: ");
				Scanner input1 = new Scanner(System.in);
				int choice = input1.nextInt();

				switch (choice) {
				case 1:
					userRegistration();
					break;
				case 2:
					userLogin();
					break;
				case 3:
					exitScreen();
					looping = false;
					input1.close();
					System.exit(0);
					break;
				default:
					System.out.println(" Please enter a valid input...");
					continue;
				}
			} catch (Exception e) {
				System.out.println(" Please enter a valid input........");
				continue;
			}
		} while (looping);
	}

	// method to register user and create file with user credentials.
	public static void userRegistration() {
		
		boolean until = true;
		String username, password; 


		if (file.isFile()) {
			// create an instance of Persons first
			System.out.println("\n--------------<| Registration |>-------------\n");

			do {
			System.out.print(" ::> enter username: ");
			username = myobj.nextLine();
			System.out.print(" ::> enter password: ");
			password = myobj.nextLine();
			
			try {
				if(Admin.createNewFile(LockerMain.path, username)) {
					until=false;
					
				}
			}catch(NullPointerException e) {
				System.out.println(e.getMessage());
			}catch(IOException e) {
				System.out.println(" Error occurred while adding file..");
				System.out.println(" Please try again...");
			}catch(Exception e) {
				System.out.println(" Error occurred while adding file..");
				System.out.println(" Please try again...");
			}
			}while(until);
			p1.add(new Users(username, password));

			// now let's write it to file using ObjectOutputStream
			try {

				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(p1); 

				li = p1.listIterator();
				/*while (li.hasNext()) {
					System.out.println(li.next());
				}*/
				System.out.println("\n**************************************************");
				System.out.println(" Registration completed successfully!");
				System.out.println("**************************************************");
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			createDatabaseFile();
						
		}
	}

	//login validation and user role selection
	public static void userLogin() {
		int found = 0;
		
		System.out.println("\n\n---------------<| Login |>---------------\n");
		System.out.print("::> Enter username: ");
		String usernameByUser = myobj.nextLine();

		System.out.print("::> Enter password: ");
		String isCorrectpass = myobj.nextLine();

		li = p1.listIterator();
		while (li.hasNext()) {
			Users listpersons = (Users) li.next();
			String un = listpersons.getUsername();
			String ue = listpersons.getPassword();
			if (un.equalsIgnoreCase(usernameByUser) && ue.contentEquals(isCorrectpass)) {
				
				System.out.print("\n Logged in as Admin ? (Y/N) :");
				char decision = myobj.nextLine().toUpperCase().charAt(0);
				if(decision == 'Y') {
					System.out.println("\n**************************************************");
					System.out.println(" Admin Dashboard Login Successfull!");
					System.out.println("**************************************************");
					System.out.println("\nHello Admin ["+ usernameByUser+"]!\n");
					Admin.adminMenu(usernameByUser);
				}else if(decision == 'N') {
					System.out.println("\n**************************************************");
					System.out.println(" User Dashboard Login Successfull!");
					System.out.println("**************************************************");
					System.out.println("\n Hello " + usernameByUser + "!\n");
					LockerOperations.Operations(usernameByUser);
				}else {
					System.out.println("\n Invalid Input \nValid Inputs :(Y/N)\n");
					userLogin();
				}
				found++;
			}
		}
		if (found == 0) {
			System.out.println("\nLogin failed!!!  Please try to Login again or Register a new user.\n");
		}
	}

 public static boolean createDatabaseFile() {
	 File newFile = new File(path+File.separator+"userdb.txt");
		
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error occurred & handled while creating File.");
		}
		
		return true;
 }
	
	public static void writeData(List<Users> u,File uf) throws FileNotFoundException, IOException {
		oos = new ObjectOutputStream(new FileOutputStream(uf));
		oos.writeObject(u);
		oos.close();
	}
	

	@SuppressWarnings("unchecked")
	public static void readData(List<Users> p1,File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ois = new ObjectInputStream(new FileInputStream(file));
		p1 = (ArrayList<Users>) ois.readObject();
		ois.close();
	}
}
