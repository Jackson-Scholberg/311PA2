import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Sample test checking simple cases. Please ensure that your code passes all of it.
 * @author aknirala
 *
 */
public class TestCMSimple {
	//addition should happen in O(1)
	@Test(timeout = 2000)
	public void checkO1Addition() {
		CommunicationsMonitor cMon = new CommunicationsMonitor();
		long startTime = System.nanoTime();
		for(int i=0; i<10; i++)
			for(int j=1; j<11; j++) {
				if(i<j) continue;
				for(int t=1; t<10; t++)
					cMon.addCommunication(i, j, t);
			}
		long endTime = System.nanoTime();
		long diff = (endTime - startTime)/1000; //microseconds
		
		//Actual time limit here could be changed in actual test.
		if(diff > 5000) fail("Avg time of addCommunication is not in O(1), takes too long: "+diff);
	}
	
	//Simple query infection (for given example)
	@Test(timeout = 2000)
	public void checkSimpleQuery() {
		int compMap[][] = {{1, 4, 12}, {1, 2, 4}, {2, 4, 8}, {3, 4, 8}};
		CommunicationsMonitor cMon = new CommunicationsMonitor();
		for(int i = 0; i<compMap.length; i++) {
			cMon.addCommunication(compMap[i][0], compMap[i][1], compMap[i][2]);
		}
		cMon.createGraph();
		List<ComputerNode> infpath = cMon.queryInfection(1, 3, 4, 8);
		
		String msg = checkCNList(infpath, new int[][]{{1, 4},{2, 4}, {2, 8}, {4, 8}, {3, 8}});
		if(!msg.equals("")) fail("Tested queryInfection(1, 3, 4, 8), for example 1. "+msg);
	}
	
	//Check if HashMap is created correctly or not (after createGraph() call)
	@Test(timeout = 2000)
	public void checkHashMap() {
		int compMap[][] = {{1, 4, 12}, {1, 2, 4}, {2, 4, 8}, {3, 4, 8}};
		CommunicationsMonitor cMon = new CommunicationsMonitor();
		for(int i = 0; i<compMap.length; i++) {
			cMon.addCommunication(compMap[i][0], compMap[i][1], compMap[i][2]);
		}
		cMon.createGraph();
		HashMap<Integer, List<ComputerNode>> compMapping = cMon.getComputerMapping();
		
		//Check Size
        Assert.assertEquals("4 nodes entered, thus size of Hashmap should be 4", 4, compMapping.size());
		
		//Check for node 4
		String msg = checkCNList(compMapping.get(4), new int[][]{{4, 8},{4, 12}});
		if(!msg.equals("")) fail("Incorrect list in HashMap for key 4, "+msg);
	}
	
	//Check if list is correct or not.
	@Test(timeout = 2000)
	public void checkList() {
		int compMap[][] = {{1, 4, 12}, {1, 2, 4}, {2, 4, 8}, {3, 4, 8}};
		CommunicationsMonitor cMon = new CommunicationsMonitor();
		for(int i = 0; i<compMap.length; i++) {
			cMon.addCommunication(compMap[i][0], compMap[i][1], compMap[i][2]);
		}
		cMon.createGraph();
		
		//Check for node 4
		String msg = checkCNList(cMon.getComputerMapping(1), new int[][]{{1, 4},{1, 12}});
		if(!msg.equals("")) fail(msg);
	}
	
	
	//Check if adjacency list is correct or not
	@Test(timeout = 2000)
	public void checkAdjList() {
		int compMap[][] = {{1, 4, 12}, {1, 2, 4}, {2, 4, 8}, {3, 4, 8}};
		CommunicationsMonitor cMon = new CommunicationsMonitor();
		for(int i = 0; i<compMap.length; i++) {
			cMon.addCommunication(compMap[i][0], compMap[i][1], compMap[i][2]);
		}
		cMon.createGraph();
		
		//Check for node (4, 8) (First node for 4)
		ComputerNode c = cMon.getComputerMapping().get(2).get(1);
		if(c.getID() != 2 || c.getTimestamp() != 8)
			fail("For C2 second node should be: (2, 8);, but got ("+c.getID()+", "+ c.getTimestamp()+")."); 
		String msg = checkCNList(c.getOutNeighbors(), new int[][]{{4, 8}});
		if(!msg.equals("")) fail(msg);
	}
	
	
	
	private String checkCNList(List<ComputerNode> cnList, int[][] cnArray) {
		String diff = "";
		if(cnList.size() != cnArray.length) return "Expected size of list is: "+cnArray.length+" but was: "+cnList.size();
		for(int i=0; i<cnArray.length; i++) {
			ComputerNode c = cnList.get(i);
			if(c.getID() != cnArray[i][0] || c.getTimestamp() != cnArray[i][1])
				return "Expected list: "+Arrays.deepToString(cnArray)+", differed at pos "+i
						+". Expcted node ("+cnArray[i][0]+", "+cnArray[i][1]+"),  but got: ("
						+c.getID() +", "+ c.getTimestamp() +").";
		}
		return diff;
	}
}
