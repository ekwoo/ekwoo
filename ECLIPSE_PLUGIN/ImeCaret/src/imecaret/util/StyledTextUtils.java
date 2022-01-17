package imecaret.util;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import imecaret.caret.CaretFactory;
import imecaret.caret.ImeCaret;
import imecaret.caret.WideCaret;

public class StyledTextUtils {
	
	/**
	 * set StyledText's Caret by IME mode
	 * @param styledText
	 */
	public static void calcEditorImeCaret(StyledText styledText) {
		calcEditorImeCaretReverse(styledText, false);
	}

	/**
	 * set StyledText's Caret by IME mode, Reverse
	 * - 한/영 키 이벤트가 IME 변경보다 먼저 일어나서 반대로 동작하는 함수 생성.......
	 * @param styledText
	 */
	public static void calcEditorImeCaretReverse(StyledText styledText, boolean reverse) {
		Caret old = styledText.getCaret();
		if(reverse != IMEUtils.isIME()) {
			if(old.getClass() != CaretFactory.caretClass) {
				if(old instanceof ImeCaret) {
					Caret wrap = old;
					old = ((ImeCaret)wrap).getOri();
					wrap.dispose();
				}
				styledText.setCaret(CaretFactory.createCaret(old));
			}
		}else {
			if(old instanceof ImeCaret) {
				styledText.setCaret(((ImeCaret) old).getOri());
				old.dispose();
			}
		}
	}
	
	/**
	 * get StyledText from PartRefernce
	 * @param partReference
	 * @return
	 */
	public static StyledText getStyledTextFromPartRef(IWorkbenchPartReference partReference) {
		if(partReference instanceof IEditorReference) {
			IEditorPart editorPart = ((IEditorReference)partReference).getEditor(false);
			if(editorPart instanceof AbstractTextEditor) {
				Control control = ((AbstractTextEditor) editorPart).getAdapter(Control.class);
				if(control instanceof StyledText) {
					StyledText styledText = ((StyledText)control);
					return styledText;
				}
			}
		}
		return null;
	}
	
	/**
	 * get StyledText from Current Activated Part
	 * @return
	 */
	public static StyledText getCurrentStyledText() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if(wb != null) {
			IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
			if(window != null) {
				IWorkbenchPage page = window.getActivePage();
				if(page != null) {
					IWorkbenchPartReference partReference =  page.getActivePartReference();
					if(partReference != null) {
						return StyledTextUtils.getStyledTextFromPartRef(partReference);
					}
				}
			}
		}
		return null;
	}
}