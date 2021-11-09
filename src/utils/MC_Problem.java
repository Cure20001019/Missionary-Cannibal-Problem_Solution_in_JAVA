package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MC_Problem {
	State initial_state;
	public int CAPACITY, NUMBER;
	
	
	//the initial number of cannibals and missionaries should be equal, thus one 
	// number is taken to specify the number of people on each side.
	public MC_Problem(int number_of_people, int boat_capacity) {
		this.initial_state = new State(number_of_people, number_of_people, 1, 0, 0, null);
		this.initial_state.update_F();
		this.CAPACITY = boat_capacity;
		this.NUMBER = number_of_people;
		
	}
	
	public void A_Star_solve(BufferedWriter bw) throws IOException{
		System.out.println("Initial State: ");
		write("Initial State: ",bw);
		this.initial_state.showState(bw);
		System.out.println("Boat capacity: "+Integer.toString(CAPACITY)+".");
		write("Boat capacity: "+Integer.toString(CAPACITY)+".",bw);
		
		StateList openList = new StateList();
		StateList closeList = new StateList();
		int solutionNumber = 1, minSteps = 0, count = 0;
		
		openList.addItem(initial_state);
		
		while (!openList.isEmpty()) {
			closeList.headInsert(openList.head.element());
			openList.headRemove();
			
			//if a best solution has not been found, go on to search. If a best solution has been found, then if
			// the node to be expanded( the head of closeList at this moment) is deeper than the target found
			//in previous solution(s), it is impossible that from this node a new solution 
			//can be found, thus there is no need to expand it, we proceed to the next node in OPEN list.
			if(solutionNumber == 1 || closeList.head.element().depth <= minSteps) {
				if(isGoal(closeList)) {
					//as the solutions found are all best solutions, the steps they take are the same, thus
					//the minimum steps taken only needs to be output once when we find the first solution
					if(solutionNumber == 1) {
						minSteps = closeList.head.element().depth;
						System.out.println("\nFirst solution found after "+Integer.toString(count)+" iterations.");
						write("\nFirst solution found after "+Integer.toString(count)+" iterations.",bw);
						System.out.println("Minimal steps taken: "+Integer.toString(minSteps)+" steps.");
						write("Minimal steps taken: "+Integer.toString(minSteps)+" steps.",bw);
					
					}
					System.out.println("\nSolution "+Integer.toString(solutionNumber)+" \n");
					write("\nSolution "+Integer.toString(solutionNumber)+" \n",bw);
					solutionNumber++;
					showSolution(closeList.head.element(), bw, 0);
				}else {
					Expand(openList, closeList);
				}
			}
			count++;
//			System.out.println("\nOPEN list after "+Integer.toString(count)+"th iteration:");
//			if(openList.isEmpty()) {
//				System.out.println("\tOPEN list is empty, all states have been searched!");
//			}else {
//				openList.printList();
//			}
		}
		
		if(minSteps == 0) {
			System.out.println("\nNo Solution!\n");
			write("\nNo Solution!\n",bw);
		}
		
		System.out.println("\nSolving process finished!\n\n");
		write("\nSolving process finished!\n\n",bw);
		
	}
	
	private boolean Expand(StateList openList, StateList closeList) {
		State cur_state = closeList.head.element();
		
		if(cur_state.boat == 0) {
		//States where the boat is at the opposite bank
			return boatAtOpposite(cur_state, openList, closeList);
		}else {
		//States where the boat is at the original bank
			return boatAtOriginal(cur_state, openList, closeList);
		}
	}
	
	private boolean boatAtOpposite(State cur_state, StateList openList, StateList closeList) {
		int m_tran, c_tran;
		boolean flag = false;

		//Scenario in which at least 1 missionary gets on-board and transferred to the other side
		for ( m_tran = 1; m_tran <= smaller(NUMBER - cur_state.missionaries, CAPACITY); m_tran++) {
			for (c_tran = 0; c_tran <= min(m_tran, NUMBER - cur_state.cannibals, CAPACITY - m_tran); c_tran++) {
				State temp = new State(cur_state.missionaries, cur_state.cannibals, cur_state.boat, cur_state.depth, 0, null);
				temp.missionaries += m_tran;
				temp.cannibals += c_tran;
				temp.boat = 1;
				++temp.depth;
				if (isLegal(temp) && !closeList.SearchIfExist(temp)) {
					temp.parent = cur_state;
					flag = true;
					temp.update_F();
					update_openList(temp, openList);
				}
			}
		}
		
		//Scenario in which no missionary gets on-board and transferred
		m_tran = 0;
		for (c_tran = 1; c_tran <= smaller(NUMBER - cur_state.cannibals, CAPACITY); c_tran++) {
			State temp = new State(cur_state.missionaries, cur_state.cannibals, cur_state.boat, cur_state.depth, 0, null);
			temp.cannibals += c_tran;
			temp.boat = 1;
			++temp.depth;
			if (isLegal(temp) && !closeList.SearchIfExist(temp)) {
				temp.parent = cur_state;
				flag = true;
				temp.update_F();
				update_openList(temp, openList);
			}
		}
		return flag;
	}
	
	private boolean boatAtOriginal(State cur_state, StateList openList, StateList closeList) {
		int m_tran, c_tran;
		boolean flag = false;
		
		//Scenario in which at least 1 missionary gets on-board and transferred to the other side
		for ( m_tran = 1; m_tran <= smaller(cur_state.missionaries, CAPACITY); m_tran++) {
			for (c_tran = 0; c_tran <= min(m_tran, cur_state.cannibals, CAPACITY - m_tran); c_tran++) {
				State temp = new State(cur_state.missionaries, cur_state.cannibals, cur_state.boat, cur_state.depth, 0, null);
				temp.missionaries -= m_tran;
				temp.cannibals -= c_tran;
				temp.boat = 0;
				++temp.depth;
				if (isLegal(temp) && !closeList.SearchIfExist(temp)) {
					temp.parent = cur_state;
					flag = true;
					temp.update_F();
					update_openList(temp, openList);
				}
			}
		}
		
		//Scenario in which no missionary gets on-board and transferred
		m_tran = 0;
		for (c_tran = 1; c_tran <= smaller(cur_state.cannibals, CAPACITY); c_tran++) {
			State temp = new State(cur_state.missionaries, cur_state.cannibals, cur_state.boat, cur_state.depth, 0, null);
			temp.cannibals -= c_tran;
			temp.boat = 0;
			++temp.depth;
			if (isLegal(temp) && !closeList.SearchIfExist(temp)) {
				temp.parent = cur_state;
				flag = true;
				temp.update_F();
				update_openList(temp, openList);
			}
		}
		return flag;
	}
	
	private void update_openList(State new_sate, StateList openList) {
		openList.addItem(new_sate);
	}
	
	private boolean isLegal(State s) {
		if((s.missionaries != NUMBER) && (s.missionaries != 0)) {
			if(s.missionaries == s.cannibals) {
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}
	}
	
	private int showSolution(State s, BufferedWriter bw, int step) throws IOException{
		if (s.parent.parent == null) {
			step = 1;
			System.out.print("\tStep "+ Integer.toString(step)+": ");
			write("\tStep "+ Integer.toString(step)+": ",bw);
			System.out.print(" --->("+Integer.toString(s.parent.missionaries - s.missionaries)+","+Integer.toString(s.parent.cannibals - s.cannibals)+")"+"  >>>  ");
			write(" --->("+Integer.toString(s.parent.missionaries - s.missionaries)+","+Integer.toString(s.parent.cannibals - s.cannibals)+")"+"  >>>  ",bw);
			s.showState(bw);
			return step;
		}
		else {
			step = showSolution(s.parent, bw, step);
			step++;
			System.out.print("\tStep "+ Integer.toString(step)+": ");
			write("\tStep "+ Integer.toString(step)+": ", bw);
			if (s.boat == 0) {
				System.out.print(" --->("+Integer.toString(s.parent.missionaries - s.missionaries)+","+Integer.toString(s.parent.cannibals - s.cannibals)+")"+"  >>>  ");
				write(" --->("+Integer.toString(s.parent.missionaries - s.missionaries)+","+Integer.toString(s.parent.cannibals - s.cannibals)+")"+"  >>>  ",bw);
			}
			else {
				System.out.print(" <---("+Integer.toString(s.parent.missionaries - s.missionaries)+","+Integer.toString(s.parent.cannibals - s.cannibals)+")"+"  >>>  ");
				write(" <---("+Integer.toString(s.parent.missionaries - s.missionaries)+","+Integer.toString(s.parent.cannibals - s.cannibals)+")"+"  >>>  ",bw);
			}
			s.showState(bw);
			return step;
		}
	}
	
	//writes to the output file "results.txt"
	private void write(String s, BufferedWriter bw) throws IOException {
		bw.write(s);
		bw.flush();
	}
	
	private int smaller(int x, int y) {
		return (x > y) ? y:x;
	}
	
	private int min(int x, int y, int z) {
		return smaller(x, smaller(y,z));
	}
	
	//determines if the target state is reached
	private boolean isGoal(StateList closeList) {
		return (closeList.head.element().missionaries == 0) && (closeList.head.element().cannibals == 0) && (closeList.head.element().boat == 0);
	}
}
