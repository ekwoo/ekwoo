package imecaret.caret;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Display;

import imecaret.Activator;

/**
 * Underline Caret
 * @author oocan
 */
public class UnderCaret extends Caret implements ImeCaret{
	/**
	 * original Caret
	 */
	protected Caret ori;
	
	public UnderCaret(Caret caret) {
		super(caret.getParent(), caret.getStyle());
		ori = caret;
		Display d = caret.getParent().getDisplay();
		Image image = new Image(d, 20, 20);
		GC gc = new GC(image);
		gc.setBackground(d.getSystemColor(SWT.COLOR_BLACK));
		gc.fillRectangle(0, 0, 20 ,20);
		gc.setBackground(d.getSystemColor(SWT.COLOR_WHITE));
		int width = gc.getCharWidth('가');
		gc.fillRectangle(0, caret.getSize().y, width, 2);
		gc.dispose();
		this.setImage(image);;
	}
	
	/**
	 * return original Caret
	 */
	@Override
	public Caret getOri() {
		return ori;
	}
	
	/**
	 * dispose
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.ori = null;
	}
	
	/**
	 * widgets cannot subclassing default...
	 */
	@Override
	protected void checkSubclass() {
	}
}
