package Testing;

import static org.junit.Assert.*;

import org.junit.Test;
import Back.CreatEvent

public class eventTest {

	@Test
	public void testGetSet() {
		
		CreatEvent e1 = new CreatEvent("name","desc","start","end");
		assertEquals("name",e1.getName());
		assertEquals("desc",e1.getDescription());
		assertEquals("start",e1.getStartDate());
		assertEquals("end",e1.getEndDate());
		
		e1.setDescription("newdesc");
		e1.setName("newname");
		e1.etEndDate("newend");
		e1.setStartDate("newstart");
		
		assertEquals("newname",e1.getName());
		assertEquals("newdesc",e1.getDescription());
		assertEquals("newstart",e1.getStartDate());
		assertEquals("newend",e1.getEndDate());
	}

}
