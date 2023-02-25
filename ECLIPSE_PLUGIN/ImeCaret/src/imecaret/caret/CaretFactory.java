package imecaret.caret;

import java.lang.reflect.Constructor;

import org.eclipse.swt.widgets.Caret;

/**
 * 커서를 만들어주.. 는데 사실상 클래스 받아서 인스턴스 생성해주는게 다라..
 * @author oocan
 *
 */
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
