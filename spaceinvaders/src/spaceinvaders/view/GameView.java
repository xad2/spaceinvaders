package spaceinvaders.view;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import spaceinvaders.controller.GameController;
import spaceinvaders.math.AABB2d;

public class GameView extends Canvas implements Observer {

	private final GameController gameController;
	private boolean stopPump = false;
	private GameView instance = this;

	public GameView(Composite parent, final GameController gameController) {
		super(parent, SWT.NONE);
		this.gameController = gameController;
		gameController.addObserver(this);

		//setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));

		addPaintListener(paintHandler);
		addKeyListener(keyHandler);
		addControlListener(resizeHandler);
		addDisposeListener(disposeHandler);
		installTickPump();
	}

	private void installTickPump() {
		getDisplay().timerExec(5, controllerPump);
	}

	PaintListener paintHandler = new PaintListener() {
		@Override
		public void paintControl(PaintEvent e) {
			Render render = new Render(e.gc, e.display);
			render.draw(gameController);
		}
	};

	KeyAdapter keyHandler = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch (e.keyCode) {
			case SWT.ARROW_LEFT:
				gameController.keyEvent(GameController.ARROW_LEFT);
				break;
			case SWT.ARROW_RIGHT:
				gameController.keyEvent(GameController.ARROW_RIGHT);
				break;
			case SWT.ARROW_UP:
				gameController.keyEvent(GameController.ARROW_UP);
				break;
			}
		}
	};

	ControlAdapter resizeHandler = new ControlAdapter() {
		@Override
		public void controlResized(ControlEvent e) {
			Point viewSize = getSize();
			gameController.setViewSize(new AABB2d(0, 0, viewSize.x, viewSize.y));
		}
	};

	DisposeListener disposeHandler = new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			stopPump = true;
			gameController.deleteObserver(instance);
		}
	};

	Runnable controllerPump = new Runnable() {
		@Override
		public void run() {
			gameController.sendTick();
			if (!stopPump)
				getDisplay().timerExec(5, controllerPump);
		}
	};

	@Override
	public void update(Observable o, Object arg) {
		redraw();
		//update();
	}

}
