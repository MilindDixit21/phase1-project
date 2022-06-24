package com.lockedme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;



public class LockerOperations {
	
	static Admin dao = new Admin();
	static File userfile;
	static boolean loopagain1 = true;
	static char decision;
	static List<UserCredentials> userCredentialList;
	static ObjectOutputStream oos;
	static boolean found = false;
	
	@SuppressWarnings("unchecked")
	public static void Operations(String usernameByUser) {

		int choice = -1;

		userCredentialList = new ArrayList<UserCredentials>();
		oos = null;
		ObjectInputStream ois = null;
		ListIterator<UserCredentials> ucli = null;

		userfile = new File("datafiles/"+usernameByUser+".txt");

		Scanner scan = new Scanner(System.in);
		
		do {
			System.out.println("=================================================");
			System.out.println("   ---<>< USER MENU - DASHBOARD ><>--- ");
			System.out.println("=================================================");
			System.out.println("  Hello "+usernameByUser+", select any one of the following:  ");
			System.out.println("  1. Insert User Credentials");
			System.out.println("  2. Display User Credentials");
			System.out.println("  3. Search by site name");
			System.out.println("  4. Delete site credentials");
			System.out.println("  5. Update credentials");
			System.out.println("  6. Display by Site Name - Ascending order");
			System.out.println("  7. delete user account");
			System.out.println("  8. Return to Main Menu");
			System.out.println("  9. Exit the application");
			System.out.println("=================================================");
			System.out.print("  ::> Enter your choice: ");
			try {
			choice = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(" Invalid input. Enter valid input integers:(1-9)");
				Operations(usernameByUser);
			}

			
			switch (choice) {
			case 1:
				System.out.println("\n\n ---------<| Add user credentials |>---------");
				do {
					System.out.print("\n::> Enter Site Name: ");
					String sitename = scan.nextLine();

					System.out.print("::> Enter "+sitename+" Username: ");
					String siteusername = scan.nextLine();

					System.out.print("::> Enter "+sitename+" Password: ");
					String sitepassword = scan.nextLine();

					userCredentialList.add(new UserCredentials(sitename, siteusername, sitepassword));
					
					System.out.print("\nHave another user credentials to add (y/n)?  :");
					String ans = scan.nextLine();

					if (ans.equals("y") || ans.equals("Y")) {
						continue;
					} else {
						break;
					}
				} while (true);
				
				try {
					writeUserData(userCredentialList, userfile);
					System.out.println(" \nUser Credentials stored for user ["+usernameByUser+"]");
				} catch (FileNotFoundException e) {
					System.out.println(e.getClass()+" "+e.getMessage()+" -writeData()=>1");
				} catch (IOException e) {
					System.out.println(e.getClass()+" "+e.getMessage()+" -writeData()=>2");
					
				}
				
				System.out.println("\n*********************************************\n");
				break;
			case 2:
				if (userfile.isFile()) {
					System.out.println("\n\n------------<| List of User credentials |>------------\n");
				
						try {
							ois = new ObjectInputStream(new FileInputStream(userfile));
							userCredentialList = (ArrayList<UserCredentials>) ois.readObject();
							ois.close();
						} catch (ClassNotFoundException e) {
							System.out.println(e.getClass()+" "+e.getMessage());
						} catch (IOException e) {
							System.out.println(" No records available. Insert user credentials.");
						}
					
					Iterator<UserCredentials> uclist = userCredentialList.iterator();
					while (uclist.hasNext()) {
						System.out.println(" "+uclist.next());
					}
				} else {
					System.out.println(" No records available. Insert user credentials.");
				}
				System.out.println("\n*********************************************\n");
				break;
			case 3:

				if (userfile.isFile()) {
					try {
						ois = new ObjectInputStream(new FileInputStream(userfile));
						userCredentialList = (ArrayList<UserCredentials>) ois.readObject();
						ois.close();
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}

					boolean found = false;
					System.out.print(" \n::> Enter site name to search: ");
					String sitename = scan.nextLine();
					System.out.println("\n==== "+(sitename).toUpperCase()+" site credentials =============");
					ucli = userCredentialList.listIterator();
					while (ucli.hasNext()) {
						UserCredentials uscred = (UserCredentials) ucli.next();
							if (uscred.sitename.equalsIgnoreCase(sitename)) {
//							System.out.println("Site name : "+ (uscred.sitename).toUpperCase());
							System.out.println("Username : "+uscred.siteusername+ "   |   Password : "+uscred.sitepassword);
							found = true;
						}

					}
					if (!found) {
						System.out.println("Site credentials are not available!");
					}
				} else {
					System.out.println("File does not exist...!");
				}
				System.out.println("\n*********************************************\n");
				break;
			case 4:
				decision = 0;
				if (userfile.isFile()) {
					try {
						ois = new ObjectInputStream(new FileInputStream(userfile));
						userCredentialList = (ArrayList<UserCredentials>) ois.readObject();
						ois.close();
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}

					
					System.out.print("\n::> Enter site name to delete site record: ");
					String sitename = scan.nextLine();
					
					
					System.out.print("\n Delete "+sitename+" record ? Confirm (Y/N) :" );
					decision =  scan.nextLine().toUpperCase().charAt(0);
					if(decision == 'Y') {
					ucli = userCredentialList.listIterator();
					while (ucli.hasNext()) {
						UserCredentials e = (UserCredentials) ucli.next();
						if (e.sitename.equalsIgnoreCase(sitename)) {
							ucli.remove();
							found = true;
						}

					}
					if (found) {
						
						try {
							writeUserData(userCredentialList, userfile);
						} catch (FileNotFoundException e) {
							System.out.println(e.getClass()+" "+e.getMessage()+" -writeData()=>1");
						} catch (IOException e) {
							System.out.println(e.getClass()+" "+e.getMessage()+" -writeData()=>2");
							
						}
												
						System.out.println(" Site record for '"+sitename+"' deleted successfully!");
					} else {
						System.out.println(" Site record for '"+sitename+"' not found!");
					}
					}else if(decision == 'N') {
						System.out.println(" ");
						break;
					}else {
						System.out.println("\nInvalid Input \nValid Inputs :(Y/N)\n");
					}
				} else {
					System.out.println("File is not exists...!");
				}
				System.out.println("\n*********************************************\n");
				break;
			case 5:

				if (userfile.isFile()) {
					try {
						ois = new ObjectInputStream(new FileInputStream(userfile));
						userCredentialList = (ArrayList<UserCredentials>) ois.readObject();
						ois.close();
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}

					boolean found = false;
					System.out.print("\n::> Enter site name to update record: ");
					String sitename = scan.nextLine();
					
					ucli = userCredentialList.listIterator();
					while (ucli.hasNext()) {
						UserCredentials e = (UserCredentials) ucli.next();
						if (e.sitename.equalsIgnoreCase(sitename)) {
							
							System.out.print("::> Enter new username: ");
							String username = scan.nextLine();					
							System.out.print("::> Enter new password: ");
							String sitepassword = scan.nextLine();
							
							ucli.set(new UserCredentials(sitename, username, sitepassword));
							found = true;
						}
					}
					if (found) {
						try {
							oos = new ObjectOutputStream(new FileOutputStream(userfile));
							oos.writeObject(userCredentialList);
							oos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("\n'"+sitename+"' user credentials updated successfully!");
					} else {
						System.out.println(" Site record not found!");
					}
				} else {
					System.out.println(" File does not exists...!");
				}
				System.out.println("\n*********************************************\n");
				break;				
			
			case 6:
				if (userfile.isFile()) {
					try {
						ois = new ObjectInputStream(new FileInputStream(userfile));
						userCredentialList = (ArrayList<UserCredentials>) ois.readObject();
						ois.close();
						
						Collections.sort(userCredentialList, new Comparator<UserCredentials>() {
							@Override
							public int compare(UserCredentials e1, UserCredentials e2) {
								return e1.sitename.compareTo(e2.sitename);
							}
						});
						
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("\n------<| Site names (ascending order) |>---------\n");
					Iterator<UserCredentials> uclist = userCredentialList.iterator();
					while (uclist.hasNext()) {
						UserCredentials ucl = uclist.next();
						System.out.println(" "+(ucl.sitename).toUpperCase() +" ==>  username :"+ucl.siteusername+"  password :"+ucl.sitepassword);
//						System.out.println(ucl.siteusername);
//						System.out.println(ucl.sitepassword);
					}
				} else {
					System.out.println("File is not exists...!");
				}
				System.out.println("\n*********************************************\n");
				break;
			case 7:
				try {
				System.out.print("\n Are you sure to delete your user account ? (Y/N) : ");
				decision = LockerMain.myobj.nextLine().toUpperCase().charAt(0);
				if(decision == 'Y') {
					deleteUserRecords(usernameByUser);
					
					String userfileName = usernameByUser+".txt";
					Admin.deleteFile(LockerMain.path, userfileName);
					LockerMain.welcomeScreen();
					LockerMain.displaymain();
				}else if(decision == 'N') {
					System.out.println(" ");
					Operations(usernameByUser);
				}else {
					System.out.println(" invalid input. Enter Y/N ( Y for Yes or N for No)");
				}
				}catch(NullPointerException e) {
					System.out.println(e.getMessage());
				}catch(IOException e) {
					System.out.println("Error occurred while Deleting File..");
					System.out.println("Please try again.");
				}catch(Exception e) {
					System.out.println("Error occurred while Deleting File..");
					System.out.println("Please try again.");
				}
				System.out.println("\n*******************o************************\n");
				break;
			case 8:
				System.out.println("\n...Returned to main menu!\n");
				LockerMain.displaymain();
				loopagain1 = false;
				break;
			case 9:
				LockerMain.exitScreen();
				loopagain1 = false;
				System.exit(0);
			default:
				System.out.println("Invalid Input \nValid Input Integers:(1-9)");

			}
		
		} while (loopagain1);
	scan.close();}
	
	
	public static void writeUserData(List<UserCredentials> uc,File uf) throws FileNotFoundException, IOException {
			oos = new ObjectOutputStream(new FileOutputStream(uf));
			oos.writeObject(uc);
			oos.close();
		
	}
	public static void readData(String path, File file) {
	}
	public static void deleteUserRecords(String str) throws IOException, ClassNotFoundException {
				
					LockerMain.readData(LockerMain.p1, LockerMain.file);
					
					LockerMain.li = LockerMain.p1.listIterator();
					while (LockerMain.li.hasNext()) {
					Users listpersons = (Users)LockerMain.li.next();
					if (listpersons.getUsername().equalsIgnoreCase(str)) {
						LockerMain.li.remove();
						found = true;
					}
					}
					if (found) {
						
						try {
							LockerMain.writeData(LockerMain.p1, LockerMain.file);
						} catch (FileNotFoundException e) {
							System.out.println("Error occurred while Deleting File..");
							System.out.println("Please try again.");
						} catch (IOException e) {
							System.out.println("Error occurred while Deleting File..");
							System.out.println("Please try again.");
						}
						
//						System.out.println("\n User Record updated!");
					} 
					/*else {
						System.out.println(" Record not found!");
					}*/
					
	}
}
