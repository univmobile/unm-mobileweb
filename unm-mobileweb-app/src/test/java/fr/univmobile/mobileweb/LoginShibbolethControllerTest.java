package fr.univmobile.mobileweb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoginShibbolethControllerTest {

	@Test
	public void testEncodeURL() {

		assertEquals("http%3A%2F%2Fgoogle.com",
				LoginShibbolethController.encodeURL("http://google.com"));
	}
}
