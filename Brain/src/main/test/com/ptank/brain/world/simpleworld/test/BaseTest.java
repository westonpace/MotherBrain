package com.ptank.brain.world.simpleworld.test;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Before;

public class BaseTest {

	@Retention(RetentionPolicy.RUNTIME)
	public @interface ExternalDebug {};
	
	@Before
	public void hookUpExternalDebug() throws IOException {
		if(this.getClass().isAnnotationPresent(ExternalDebug.class)) {
			System.out.println("Please hook up any external device and press enter.");
			System.in.read();
		}
	}
	
}
