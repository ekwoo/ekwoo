package imecaret.caret;

import java.lang.reflect.Constructor;

import org.eclipse.swt.widgets.Caret;

import imecaret.Activator;
import imecaret.preferences.PreferenceConstants;


public class CaretFactory {
	public static Class caretClass;
		
	public static Caret createCaret(Caret caret) {
		
		if(caretClass != null) {
			Constructor<ImeCaret> c;
			try {
				c = caretClass.getConstructor(Caret.class);
				return (Caret) c.newInstance(caret);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new WideCaret(caret);
	}
	
}
