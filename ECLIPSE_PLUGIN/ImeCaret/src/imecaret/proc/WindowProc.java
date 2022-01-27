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
	private static final long KF_UP = 2147483648l;
	private static final int WH_KEYBOARD = 2;
	private long windowProc;
	private long hook;
	
	//if null if null...
	long windowProc(long hwnd, long msg, long wParam, long lParam) {
		if(msg == 21) {	//21이 한/영, 45는 insert
			StyledText styledText = StyledTextUtils.getCurrentStyledText();
			if(styledText != null) {
				StyledTextUtils.calcEditorImeCaretReverse(styledText, true);	//이벤트 후에 한/영이 바뀐다..
			}
		}else if(msg == 45 && (wParam & KF_UP) == KF_UP) {
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
			hook = OS.SetWindowsHookEx(WH_KEYBOARD, windowProc, 0L, OS.GetCurrentThreadId());
		}else {
			SWT.error(SWT.ERROR_NO_MORE_CALLBACKS);
		}
	}
	
	public void deRegister() {
		OS.UnhookWindowsHookEx(hook);
	}
	
}
