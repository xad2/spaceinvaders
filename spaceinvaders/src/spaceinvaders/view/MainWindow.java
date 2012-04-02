package spaceinvaders.view;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import spaceinvaders.controller.GameController;

public class MainWindow extends ApplicationWindow {

	private GameController gameController;

	public MainWindow() {
		super(null);
		
		gameController = new GameController();
	}

	@Override
	public void create() {
	    setShellStyle(SWT.DIALOG_TRIM);
	    super.create();
	}
	
	@Override
	protected Control createContents(Composite parent) {
	
		getShell().setText("Space invaders!");
		
		parent.setSize(480, 640);
		
		GameView gv = new GameView(parent, gameController);
		
		return parent;
	}

	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		mw.setBlockOnOpen(true);
		mw.open();
		Display.getCurrent().dispose();
	}

}
