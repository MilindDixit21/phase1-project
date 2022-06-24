package com.lockedme;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Admin {
	static Scanner scan = new Scanner(System.in);
	
	public static void adminMenuOptions(String usernameByUser) {
		System.out.println("=================================================");
		System.out.println("         ---<>< ADMIN MENU ><>---     ");
		System.out.println("=================================================");
		System.out.println("  Hello "+usernameByUser+", select any one of the following:-  ");
	    System.out.println("  1  List all User (files)              ");
	    System.out.println("  2  Search a User (file)               ");
	    System.out.println("  3  Delete a User (file)               ");
	    System.out.println("  4  Go to User Menu-Dashboard              ");
	    System.out.println("  5  Exit                        ");
	    System.out.println("=================================================");
	    System.out.print("  ::> Enter your choice : ");
	}
	
	public static void adminMenu(String usernameByUser) {
		String file = null;
		String fileName = null;
		int choice = 0;
		
		do {
			
			adminMenuOptions(usernameByUser);
			
			try {
				choice = Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input \nValid Input Integers:(1-4)");
				adminMenu(usernameByUser);
			}
			
			
			switch (choice) {
			case 1: 
				System.out.println("\n-----------<| List of Files |>------------\n");
				try {
					listAllFiles(LockerMain.path);
				}catch(NullPointerException e) {
					System.out.println(e.getMessage());
				}catch(IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("\n*************************************************");
				break;
					
			case 2: 
				System.out.println("\n-------------| Search File |-------------\n");
					System.out.print("::> Enter a file name to Search : ");
					file = scan.nextLine();
					fileName = file.trim();
					try {
						searchFile(LockerMain.path, fileName);
					}catch(NullPointerException e) {
						System.out.println(e.getMessage());
					}catch(IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					System.out.println("\n*************************************************");
					break;
					
			case 3: 
				System.out.println("\n------------<| Delete File |>------------\n");
				System.out.print("  ::> Enter a file name to Delete : ");
				file = scan.nextLine();
				if(!"userdb.txt".equalsIgnoreCase(file)) { 
					fileName = file.trim();
					try {
						deleteFile(LockerMain.path, fileName);
						
						String fname = fileName.substring(0, fileName.length() - 4);
						LockerOperations.deleteUserRecords(fname);
						
						if(fileName.equalsIgnoreCase(usernameByUser)) {
							LockerMain.welcomeScreen();
						}
					}catch(NullPointerException e) {
						System.out.println(e.getMessage());
					}catch(IOException e) {
						System.out.println("Error occurred while Deleting File..");
						System.out.println("Please try again...");
					}catch(Exception e) {
						System.out.println("Error occurred while Deleting File..");
						System.out.println("Please try again...");
					}
				}else {
					System.out.println("\n :---------------------------------------------:");
					System.out.println(" !   WARNING !!! DO NOT DELETE userdb.txt FILE !");
					System.out.println(" !           please select a user file         !");
					System.out.println(" :---------------------------------------------:");
				}
				System.out.println("\n*************************************************");
				break;

			case 4: 
				System.out.println("\n...Returned to user menu-dashbard!\n");
				LockerOperations.Operations(usernameByUser);
					break;
			case 5:
				LockerMain.exitScreen();
				System.exit(0);
				break;
				
			default:
				System.out.println(" Invalid Input \nValid Input Integers:(1-4)");
				adminMenu(usernameByUser);
				
			}
			
		file = null;
		fileName = null;
			
		}while(true);
		
	}
	
	
	

	public static void listAllFiles(String path) {
		
		if (path == null || path.isEmpty())
			throw new NullPointerException("Path cannot be Empty or null");
			
		
		File dir = new File(path);
		
		if(!dir.exists())
			throw new IllegalArgumentException("Path does not exist");
		
		if(dir.isFile())
			throw new IllegalArgumentException("The given path is a file. A directory is expected.");
		
		String [] files = dir.list();
		
		if(files != null && files.length > 0) {
			
			Set<String>filesList = new TreeSet<String>(Arrays.asList(files)); 
			System.out.println("The Files in "+ dir.getAbsolutePath() + " are: \n");
			for(String file1:filesList) {
				
				System.out.println(file1);
				
			}
			
			System.out.println("\nTotal Number of files: "+ filesList.size());	
		}else {
			
			System.out.println("Directory is Empty");
		}
		
	}
	
	
	public static boolean createNewFile(String path , String fileName) throws IOException {
		
		if (path == null || path.isEmpty())
			throw new NullPointerException("Path cannot be Empty or null");
	
		
		if (fileName == null || fileName.isEmpty())
			throw new NullPointerException("File Name cannot be Empty or null");
		
		File newFile = new File(path + System.getProperty("file.separator") + fileName+".txt");
		
		boolean createFile = newFile.createNewFile();
		
		if (createFile) {
//			System.out.println("\nUser created Successfully : " + newFile.getAbsolutePath());
			return true;
		}else if(!createFile) {
			System.out.println("\n Username ["+fileName+"] is taken. Try another." );
			return false;
			
		}
		return createFile;
			
	}
	
	
	
public static void deleteFile(String path , String fileName) throws IOException {
	
		if (path == null || path.isEmpty()) 
			throw new NullPointerException("Path cannot be Empty or null");
		
		
		if (fileName == null || fileName.isEmpty()) 
			throw new NullPointerException("File Name cannot be Empty or null");
		
		File newFile = new File(path + System.getProperty("file.separator") + fileName);

		boolean deleteFile = newFile.delete();
		
		if (deleteFile) {
			
			System.out.println("\n User File ["+fileName+"] has been deleted Successfully!");
			
		}else {
			
			System.out.println("\n File ["+fileName+"] Not Found!!!\n" );
			
		}
			
	}
	


public static void searchFile(String path , String fileName){
		
		if (path == null || path.isEmpty()) {
			throw new NullPointerException("Path cannot be Empty or null");
		}
		if (fileName == null || fileName.isEmpty()) {
			throw new NullPointerException("File Name cannot be Empty or null");
		}
		File dir = new File(path);
		
		if(!dir.exists()) {
			throw new IllegalArgumentException("Path does not exist");
		}
		if(dir.isFile()) {
			throw new IllegalArgumentException("The given path is a file. A directory is expected.");
		}
		
		String [] fileList = dir.list();
		boolean flag = false;
		
		Pattern pat = Pattern.compile(fileName);
		if(fileList != null && fileList.length > 0) {
			for(String file:fileList) {
				Matcher mat = pat.matcher(file);
				if(mat.matches()) {
					System.out.println(" \nFile Found at location: " + dir.getAbsolutePath());
					flag = true;
					break;
				}
			}
		}
		
		if(flag == false) {
			System.out.println(" \nFile Not Found!!!");
		}
			
	}
	
}
