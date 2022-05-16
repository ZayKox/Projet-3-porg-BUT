package tests;

import graphes.ihm.GrapheImporter;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import graphes.ihm.Arc;

class GraphImporterTest {

	@Test
	void test() {
		Arc a = GrapheImporter.parse("1 -5 3");
		assertEquals(1, a.getSource());
		assertEquals(3, a.getDestination());
		assertEquals(-5, a.getValuation());
		Assertions.assertThrows( IllegalArgumentException.class, () -> {
	           GrapheImporter.parse("a1 -5 3");
	  });

	}
	

}
