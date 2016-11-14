package lv.nixx.poc.mockito.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticMethodsTest.Mapper.class)
public class StaticMethodsTest {

	@Test
	public void test(){
        PowerMockito.mockStatic(Mapper.class);
        PowerMockito.when(Mapper.map("20")).thenReturn("mappedValueFor20");
        
        assertEquals("mappedValueFor20", Mapper.map("20"));
        assertNull(Mapper.map("x"));
	}
	
	static class Mapper {
		public static String map(String v){
			return v;
		}
	}
}
