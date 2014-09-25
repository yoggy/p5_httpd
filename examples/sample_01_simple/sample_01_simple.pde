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

