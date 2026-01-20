package imecaret.util;

import org.eclipse.swt.internal.win32.OS;

public class IMEUtils {
	/**
	 * Sent by an application to direct the IME window to carry out the requested command.
	 * The application uses this message to control the IME window that it has created.
	 * To send this message, the application calls the SendMessage function with the following parameters.
	 */
	public static final int WM_IME_CONTROL = 643;
	
	/**
	 * This message is sent by an application to an IME window to determine whether the IME is open.
	 * lParam Not Used
	 * 이전 버전의 Microsoft IME설정을 켰다면 아래값을 사용한다.
	 */
	public static final int IMC_GETOPENSTATUS = 5;
	/**
	 * This message is sent by an application to an IME window to obtain the current conversion mode. The window retrieves the current conversion mode from the current input context.
	 * lParam Not Used
	 * 윈도우 11의 새 IME를 사용하면 아래값을 사용한다.
	 */
	public static final int IMC_GETCONVERSIONMODE = 1;
	
	/**
	 * check current window is IME Mode
	 * @return
	 */
	public static boolean isIME() {
		//현재 윈도우의 핸들 얻기
		long hwnd = OS.GetActiveWindow();
		//IME 윈도우 핸들 얻기
		long hime = OS.ImmGetDefaultIMEWnd(hwnd);
		//IME가 열려있는지 확인
		long status = OS.SendMessage(hime, WM_IME_CONTROL, IMC_GETCONVERSIONMODE, 0);
		//0이 아니면 IME OPEN
		return status != 0;
	}
	
}
