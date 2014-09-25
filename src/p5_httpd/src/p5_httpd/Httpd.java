package p5_httpd;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import processing.core.PApplet;
import processing.core.PImage;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class Httpd extends NanoHTTPD {
	PImage pimage;
	int jpeg_quality = 90;
	int update_interval = 200;
	String background_color = "#000000";

	public Httpd(int port) {
		super(port);
	}

	public String getBackgroundColor() {
		return background_color;
	}
	
	public void setBackgroundColor(String val) {
		this.background_color = val;
	}
	
	public int getJpegQuality() {
		return jpeg_quality;
	}

	public void setJpegQuality(int val) {
		this.jpeg_quality = val;
	}

	public int getUpdateInterval() {
		return update_interval;
	}

	public void setUpdateInterval(int val) {
		this.update_interval = val;
	}

	synchronized void setPImage(PImage img) {
		if (img == null) {
			this.pimage = null;
			return;
		}

		try {
			this.pimage = (PImage) img.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	synchronized PImage getPImage() {
		return this.pimage;
	}

	public void start() {
		try {
			super.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void publish(PApplet papplet) {
		if (papplet == null) {
			setPImage(null);
			return;
		}
		setPImage(papplet.get());
	}

	public void publish(PImage img) {
		setPImage(img);
	}

	@Override
	public Response serve(IHTTPSession session) {
		String uri = session.getUri();
		String[] paths = uri.split("\\?");

		if (paths != null && "/sketch.jpg".equals(paths[0])) {
			return serveSketchJpeg(session);
		}

		// index.html
		String html = "<html><head><script>function reload(){document.sketch_jpg.src='sketch.jpg?t='+new Date().getTime();}function init() {setInterval(reload, "
				+ update_interval
				+ ");}</script></head><body onLoad='init()' style='height:100%;margin:0px;text-align:center;background-color:"
				+ background_color
				+ "'><img name='sketch_jpg' style='height:100%;margin:0px;' src='sketch.jpg'/></body></html>";
		return new NanoHTTPD.Response(html);
	}

	private Response serveSketchJpeg(IHTTPSession session) {
		PImage image = getPImage();
		if (image == null) {
			image = new PImage(400, 400);
		}

		byte[] jpeg_data = PImageUtils
				.toJpegByteArray(image, this.jpeg_quality);

		ByteArrayInputStream bis = new ByteArrayInputStream(jpeg_data);

		NanoHTTPD.Response res = new NanoHTTPD.Response(Status.OK,
				"image/jpeg", bis);
		res.addHeader("X-Content-Type-Options", "nosniff");
		res.addHeader("Access-Control-Allow-Origin", "*");
		return res;
	}

}
