package imecaret.caret;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Caret;

/**
 * wide Caret
 * @author oocan
 */
public class WideCaret extends Caret implements ImeCaret{
	/**
	 * original Caret
	 */
	protected Caret ori;
	
	public WideCaret(Caret caret) {
		super(caret.getParent(), caret.getStyle());
		ori = caret;
		Image image = caret.getImage();
		if(image != null) {
			ImageData imageData = image.getImageData();
			ImageData imageDataNew;
			if(imageData.width > imageData.height) {
				imageDataNew = imageData.scaledTo(imageData.width, 2*imageData.height);
			}else {
				imageDataNew = imageData.scaledTo(2*imageData.width, imageData.height);
			}
			super.setImage(new Image(image.getDevice(), imageDataNew));
		}else {
			Point p = caret.getSize();
			if(p.x > p.y) {
				p.y = 2 * p.y;
			}else {
				p.x = 2 * p.x;
			}
			this.setSize(p);
		}
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
