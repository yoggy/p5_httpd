package p5_httpd.test;

import p5_httpd.*;
import processing.core.PApplet;

public class P5HttpdTestMain extends PApplet {
	private static final long serialVersionUID = 4258434090200713048L;

	Httpd httpd;
	
	public void setup() {
		size(640, 480);
		frameRate(10);
		
		httpd = new Httpd(8080);
		httpd.start();

		background(0, 0, 0);
	}

	public void draw() {
		// draw somthing...
		fill(random(255), random(255), random(255));
		ellipse(random(width), random(height), 30, 30);
		
		
		httpd.publish(this);
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "p5_httpd.test.P5HttpdTestMain" });
	}
}
