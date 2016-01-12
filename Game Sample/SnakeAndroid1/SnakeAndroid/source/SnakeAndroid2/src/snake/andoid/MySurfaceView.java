/*    
    Copyright (C) 2012 http://software-talk.org/ (developer@software-talk.org)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package snake.andoid;

// TODO use this instaed of text adapter
public class MySurfaceView {
	// public class MySurfaceView extends SurfaceView implements Runnable {
	//
	// Thread thread = null;
	// SurfaceHolder surfaceHolder;
	// volatile boolean running = false;
	//
	// private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// Random random;
	//
	// public MySurfaceView(Context context) {
	// super(context);
	// surfaceHolder = getHolder();
	// random = new Random();
	// }
	//
	// public void onResumeMySurfaceView() {
	// running = true;
	// thread = new Thread(this);
	// thread.start();
	// }
	//
	// public void onPauseMySurfaceView() {
	// boolean retry = true;
	// running = false;
	// while (retry) {
	// try {
	// thread.join();
	// retry = false;
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// @Override
	// public void run() {
	// while (running) {
	// if (surfaceHolder.getSurface().isValid()) {
	// Canvas canvas = surfaceHolder.lockCanvas();
	// // ... actual drawing on canvas
	//
	// paint.setStyle(Paint.Style.STROKE);
	// paint.setStrokeWidth(3);
	//
	// int w = canvas.getWidth();
	// int h = canvas.getHeight();
	// int x = random.nextInt(w - 1);
	// int y = random.nextInt(h - 1);
	// int r = random.nextInt(255);
	// int g = random.nextInt(255);
	// int b = random.nextInt(255);
	// paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);
	// canvas.drawPoint(x, y, paint);
	//
	// surfaceHolder.unlockCanvasAndPost(canvas);
	// }
	// }
	// }

}
