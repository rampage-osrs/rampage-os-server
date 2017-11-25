package org.brutality.net.captcha;

public interface Captcha {

	Object getKey(int identifier);
	Object getAnswer();
	int getExpectedLength();
	
}
