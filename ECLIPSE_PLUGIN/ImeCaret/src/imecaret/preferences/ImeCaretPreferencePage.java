package imecaret.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import imecaret.Activator;

/**
 * CustomCaretPreferencePage
 */
public class ImeCaretPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Use Alternative Caret when IME mode on
	 */
	BooleanFieldEditor useAltCaret;
	
	/**
	 * Alternative Caret Type
	 */
	RadioGroupFieldEditor caretType;
	
	public ImeCaretPreferencePage() {
		super(GRID);
	}
	
	/**
	 * generate preference page
	 */
	public void createFieldEditors() {
		useAltCaret = new BooleanFieldEditor(
				PreferenceConstants.USE_IME_CARET,
				"Change Caret in IME mode",
				getFieldEditorParent());
		addField(useAltCaret);
		
		caretType = new RadioGroupFieldEditor(
				PreferenceConstants.CARET_TYPE,
				"IME Caret Type",
				1,
				new String[][] { { "&Wide", "imecaret.caret.WideCaret" }, {"&Underline", "imecaret.caret.UnderCaret" }
		}, getFieldEditorParent());
		addField(caretType);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		//setDescription("IME Caret");
	}
	
}