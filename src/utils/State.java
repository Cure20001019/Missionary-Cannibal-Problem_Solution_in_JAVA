package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class State {
	int missionaries;
	int cannibals;
	int boat;
	int depth;
	int f;
	State parent;
	
	public State(int missionaries, int cannibals, int boat,int depth, int f, State parent) {
		this.missionaries = missionaries;
		this.cannibals = cannibals;
		this.boat = boat;
		this.depth = depth;
		this.f = f;
		this.parent = parent;
	}
	
	//shows relevant information of the state and writes them 
	//to the "results.txt" file
	public void showState(BufferedWriter bw) throws IOException {
		System.out.println("("+Integer.toString(missionaries)+","+Integer.toString(cannibals)+","+Integer.toString(boat)+")"
				+"  f="+Integer.toString(f)
				+",  depth="+Integer.toString(depth)
				+",  h="+Integer.toString(compute_H()));
		write("("+Integer.toString(missionaries)+","+Integer.toString(cannibals)+","+Integer.toString(boat)+")"
				+"  f="+Integer.toString(f)
				+",  depth="+Integer.toString(depth)
				+",  h="+Integer.toString(compute_H())+"\n",bw);
	}
	
	//writes to the output file "results.txt"
	private void write(String s, BufferedWriter bw) throws IOException {
		bw.write(s);
		bw.flush();
	}
	
	//f is calculated by depth + heuristic function value
	public int update_F() {
		f = depth + compute_H();
		return f;
	}
	
	//the heuristic function for A* search algorithm
	public int compute_H() {
		return missionaries+cannibals-2*boat;
	}
}
