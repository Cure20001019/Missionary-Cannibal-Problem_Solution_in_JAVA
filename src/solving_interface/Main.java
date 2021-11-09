package solving_interface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import utils.MC_Problem;
import utils.State;
import utils.StateList;

public class Main {

	public static void main(String[] args) {
		try {
			File data = new File("results.txt");
			if(!data.exists()) {
				data.createNewFile();
			}
			//set to false so that the file is overriden every time 
			//the program is run
			FileWriter fw = new FileWriter(data.getAbsoluteFile(),false);
			BufferedWriter bw = new BufferedWriter(fw);
			showExample(bw);
			System.out.println("Please enter the number of missionaries and cannibals(press 0 to exit): ");
			Scanner sc=new Scanner(System.in);
			String temp;
			int NUMBER, CAPACITY;
			while (sc.hasNextLine()) {
				temp = sc.nextLine();
				if(isInt(temp)) {
					NUMBER = Integer.parseInt(temp);
					if(NUMBER == 0) {
						fw.close();
						sc.close();
						System.out.println("\nProgram terminated!\n");
						System.exit(0);
					}
					System.out.println("Please enter the capacity of the boat: ");
					temp = sc.nextLine();
					CAPACITY = Integer.parseInt(temp);
				}else {
					System.out.println("Invalid input!");
					System.out.println("Please enter the number of missionaries and cannibals(press 0 to exit): ");
					continue;
				}
				MC_Problem problem = new MC_Problem(NUMBER, CAPACITY);
				System.out.print('\n');
				problem.A_Star_solve(bw);
				System.out.println("Please enter the number of missionaries and cannibals(press 0 to exit): ");
			}

			fw.close();
			sc.close();
			System.out.print("\nProgram terminated!\n");
			System.exit(0);

		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	private static boolean isInt(String temp) {
		try{
			Integer check = Integer.parseInt(temp);
			if (check instanceof Integer){
				return true;
			}
		}catch(NumberFormatException e){
			return false;
		}
		
		return false;
	}
	
	private static void showExample(BufferedWriter bw) throws IOException {
		System.out.println("Look at an example first: \n");
		write("Look at an example first: \n",bw);
		MC_Problem problem = new MC_Problem(3, 2);
		problem.A_Star_solve(bw);
	}
	
	//writes to the output file "results.txt"
	private static void write(String s, BufferedWriter bw) throws IOException {
		bw.write(s);
		bw.flush();
	}

}
