import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestThingsToTest {
	@Test
	public void test() {
		CommunicationsMonitor cm = new CommunicationsMonitor();
		int[][] com = new int[200000][3];

		for (int i = 0; i < com.length; i++) {
			com[i][0] = i;
			com[i][1] = i+1;
			com[i][2] = 0;
		}

		for (int[] c : com) {
			cm.addCommunication(c[0], c[1], c[2]);
		}

		cm.createGraph();

		List<ComputerNode> node = cm.queryInfection(1, com.length, 0, 0);
		assertEquals(com.length, node.get(com.length - 1).getID());
		assertEquals(0, node.get(com.length - 1).getTimestamp());
	}
}
