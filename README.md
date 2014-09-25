p5_httpd
=========
p5_httpd is a simple HTTP Server library for Processing.
You can easily publish a sketch image as HTML content.


Usage
=========
<pre>
import p5_httpd.*;

Httpd httpd;

public void setup() {
  size(640, 480);
  frameRate(10);
  
  // start HTTP server on port 8080.
  httpd = new Httpd(8080);
  httpd.start();
  
  background(0, 0, 0);
}

public void draw() {
  // draw somthing
  fill(random(255), random(255), random(255));
  ellipse(random(width), random(height), 70, 70);

  // publish "this" sketch as HTML. (access to http://127.0.0.1:8080/)
  httpd.publish(this);
}
</pre>


Libraries
========
p5_httpd uses the following libraries.

NanoHttpd
* https://github.com/NanoHttpd/nanohttpd

