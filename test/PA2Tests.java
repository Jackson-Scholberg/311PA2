import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PA2Tests {

	CommunicationsMonitor monitor;

	@Before
	public void initialize() {
		// Start each test with an empty CommunicationsMonitor
		monitor = new CommunicationsMonitor();
	}

	@Test
	public void testCaseOne() {
		monitor.addCommunication(1, 2, 5);
		monitor.addCommunication(1, 3, 6);
		monitor.addCommunication(1, 4, 7);
		monitor.addCommunication(3, 4, 8);
		monitor.addCommunication(2, 3, 10);

		monitor.createGraph();
		List<ComputerNode> infectionPath = monitor.queryInfection(1, 2, 5, 5);
		assertEquals(1, infectionPath.get(0).getID());
		assertEquals(5, infectionPath.get(0).getTimestamp());
		assertEquals(2, infectionPath.get(1).getID());
		assertEquals(5, infectionPath.get(1).getTimestamp());
		assertEquals(2, infectionPath.size());

		infectionPath = monitor.queryInfection(1, 3, 6, 6);
		assertEquals(1, infectionPath.get(0).getID());
		assertEquals(6, infectionPath.get(0).getTimestamp());
		assertEquals(3, infectionPath.get(1).getID());
		assertEquals(6, infectionPath.get(1).getTimestamp());
		assertEquals(2, infectionPath.size());

		infectionPath = monitor.queryInfection(1, 4, 5, 7);
		assertEquals(1, infectionPath.get(0).getID());
		assertEquals(5, infectionPath.get(0).getTimestamp());
		assertEquals(1, infectionPath.get(1).getID());
		assertEquals(6, infectionPath.get(1).getTimestamp());
		assertEquals(1, infectionPath.get(2).getID());
		assertEquals(7, infectionPath.get(2).getTimestamp());
		assertEquals(4, infectionPath.get(3).getID());
		assertEquals(7, infectionPath.get(3).getTimestamp());
		assertEquals(4, infectionPath.size());

		infectionPath = monitor.queryInfection(1, 4, 6, 8);
		assertEquals(1, infectionPath.get(0).getID());
		assertEquals(6, infectionPath.get(0).getTimestamp());
		assertEquals(1, infectionPath.get(1).getID());
		assertEquals(7, infectionPath.get(1).getTimestamp());
		assertEquals(4, infectionPath.get(2).getID());
		assertEquals(7, infectionPath.get(2).getTimestamp());
		assertEquals(3, infectionPath.size());

		infectionPath = monitor.queryInfection(1, 2, 6, 10);
		assertEquals(1, infectionPath.get(0).getID());
		assertEquals(6, infectionPath.get(0).getTimestamp());
		assertEquals(3, infectionPath.get(1).getID());
		assertEquals(6, infectionPath.get(1).getTimestamp());
		assertEquals(3, infectionPath.get(2).getID());
		assertEquals(8, infectionPath.get(2).getTimestamp());
		assertEquals(3, infectionPath.get(3).getID());
		assertEquals(10, infectionPath.get(3).getTimestamp());
		assertEquals(2, infectionPath.get(4).getID());
		assertEquals(10, infectionPath.get(4).getTimestamp());
		assertEquals(5, infectionPath.size());
	}
	 	
	@Test
	public void testCaseTwo() {
	 	monitor.addCommunication(1, 2, 1);
		monitor.addCommunication(2, 3, 2);
		monitor.addCommunication(11, 2, 1);
		monitor.addCommunication(13, 15, 2);
		monitor.addCommunication(3, 5, 3);
		monitor.addCommunication(4, 8, 4);
		monitor.addCommunication(9, 14, 3);
		monitor.addCommunication(5, 7, 5);
		monitor.addCommunication(2, 10, 6);
		monitor.addCommunication(3, 14, 8);
		monitor.addCommunication(4, 5, 9);
		monitor.addCommunication(7, 8, 10);
		monitor.addCommunication(6, 15, 11);
		monitor.addCommunication(10, 12, 12);
		monitor.createGraph();
		 
		List<ComputerNode> infectionPath = monitor.queryInfection(1, 14, 1, 8);
		assertEquals(1, infectionPath.get(0).getID());
		assertEquals(1, infectionPath.get(0).getTimestamp());
		assertEquals(2, infectionPath.get(1).getID());
		assertEquals(1, infectionPath.get(1).getTimestamp());
		assertEquals(2, infectionPath.get(2).getID());
		assertEquals(2, infectionPath.get(2).getTimestamp());
		assertEquals(3, infectionPath.get(3).getID());
		assertEquals(2, infectionPath.get(3).getTimestamp());
		assertEquals(3, infectionPath.get(4).getID());
		assertEquals(3, infectionPath.get(4).getTimestamp());
		assertEquals(3, infectionPath.get(5).getID());
		assertEquals(8, infectionPath.get(5).getTimestamp());
		assertEquals(14, infectionPath.get(6).getID());
		assertEquals(8, infectionPath.get(6).getTimestamp());
		assertEquals(7, infectionPath.size());

		infectionPath = monitor.queryInfection(2, 12, 6, 12);
		assertEquals(2, infectionPath.get(0).getID());
		assertEquals(6, infectionPath.get(0).getTimestamp());
		assertEquals(10, infectionPath.get(1).getID());
		assertEquals(6, infectionPath.get(1).getTimestamp());
		assertEquals(10, infectionPath.get(2).getID());
		assertEquals(12, infectionPath.get(2).getTimestamp());
		assertEquals(12, infectionPath.get(3).getID());
		assertEquals(12, infectionPath.get(3).getTimestamp());
		assertEquals(4, infectionPath.size());

		infectionPath = monitor.queryInfection(13, 6, 2, 11);
		assertEquals(13, infectionPath.get(0).getID());
		assertEquals(2, infectionPath.get(0).getTimestamp());
		assertEquals(15, infectionPath.get(1).getID());
		assertEquals(2, infectionPath.get(1).getTimestamp());
		assertEquals(15, infectionPath.get(2).getID());
		assertEquals(11, infectionPath.get(2).getTimestamp());
		assertEquals(6, infectionPath.get(3).getID());
		assertEquals(11, infectionPath.get(3).getTimestamp());
		assertEquals(4, infectionPath.size());

		infectionPath = monitor.queryInfection(13, 10, 2, 12);
		assertEquals(null, infectionPath);
	}
}
