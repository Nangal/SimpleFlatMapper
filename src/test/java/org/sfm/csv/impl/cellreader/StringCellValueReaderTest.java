package org.sfm.csv.impl.cellreader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sfm.csv.impl.cellreader.StringCellValueReader;

public class StringCellValueReaderTest {

	StringCellValueReader reader = new StringCellValueReader();
	@Test
	public void testReadStringNoEscaping() {
		char[] chars = "Hello!".toCharArray();
		assertEquals("Hello!", reader.read(chars, 0, chars.length, null));

	
	}
	
	@Test
	public void testReadStringToUnescape() {
		char[] chars = "\"Hello!\"\"Sir\"\"\"".toCharArray();
		assertEquals("Hello!\"Sir\"", reader.read(chars, 0, chars.length, null));
		
		
		chars = "\"Hello!\"\"Sir".toCharArray();
		assertEquals("Hello!\"Sir", reader.read(chars, 0, chars.length, null));
		
		chars = "HHH\"Hello!\"HHH".toCharArray();
		assertEquals("Hello!", reader.read(chars, 3, chars.length - 6, null));
	}

}