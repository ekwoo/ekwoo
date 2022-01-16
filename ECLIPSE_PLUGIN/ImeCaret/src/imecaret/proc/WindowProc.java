package imecaret.proc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.OS;

import imecaret.util.StyledTextUtils;

/**
 * 한/영키를 swt 이벤트에서는 무시하므로 따로 입력받음
 * @author oocan
 *
 */
public class WindowProc {
	private long windowProc;
	private long hook;
	
	//if null if null...
	long windowProc(long hwnd, long msg, long wParam, long lParam) {
		if(msg == 21) {	//21이 한/영, 45는 insert wParam >16777216 == keyUp
			StyledText styledText = StyledTextUtils.getCurrentStyledText();
			if(styledText != null) {
				StyledTextUtils.calcEditorImeCaretReverse(styledText, true);	//이벤트 후에 한/영이 바뀐다..
			}
		}else if(msg == 45 && wParam > 16777216) {
			StyledText styledText = StyledTextUtils.getCurrentStyledText();
			if(styledText != null) {
				StyledTextUtils.calcEditorImeCaret(styledText);
			}
		}
		return OS.DefWindowProc(hwnd, (int)msg, wParam, lParam);
	}
	
	public void register() {
		Callback windowCallback = new Callback(this, "windowProc", 4);
		windowProc = windowCallback.getAddress();
		if(windowProc != 0) {
			hook = OS.SetWindowsHookEx(2, windowProc, 0L, OS.GetCurrentThreadId());
		}else {
			SWT.error(0, null, "cannot create IME hook");
		}
	}
	
	public void deRegister() {
		OS.UnhookWindowsHookEx(hook);
	}
	
}
