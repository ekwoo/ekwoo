package imecaret.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.IWorkbench;
import imecaret.Activator;
import imecaret.startup.StartUp;

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
				new String[][] { { "&Wide", "wide" }, {"&Underline", "underline" }
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