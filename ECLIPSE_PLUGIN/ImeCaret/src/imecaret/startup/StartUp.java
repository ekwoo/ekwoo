package imecaret.startup;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import imecaret.Activator;
import imecaret.caret.CaretFactory;
import imecaret.caret.ImeCaret;
import imecaret.caret.WideCaret;
import imecaret.preferences.PreferenceConstants;
import imecaret.proc.WindowProc;
import imecaret.util.StyledTextUtils;

public class StartUp implements IStartup{
	private static WindowProc proc;
	private static IPropertyChangeListener fontPropertyChangeListener;
	private static PartListener partListener;
	
	@Override
	public void earlyStartup() {
		//run after display is initialized
		Display.getDefault().asyncExec(() -> {
			if(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.USE_IME_CARET)) {
				activate();
			}
			try {
				CaretFactory.caretClass = (Class<ImeCaret>) Class.forName(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.CARET_TYPE));
			} catch (ClassNotFoundException e2) {
				CaretFactory.caretClass = WideCaret.class;
				e2.printStackTrace();
			}
			//listen property change
			Activator.getDefault().getPreferenceStore().addPropertyChangeListener(e -> {
				switch (e.getProperty()) {
				case PreferenceConstants.USE_IME_CARET:
					if(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.USE_IME_CARET)) {
						StartUp.activate();
					}else {
						StartUp.deActivate();
					}
					break;
				case PreferenceConstants.CARET_TYPE:
					if(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.USE_IME_CARET)) {
						try {
							CaretFactory.caretClass = (Class<ImeCaret>) Class.forName(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.CARET_TYPE));
							StyledText styledText = StyledTextUtils.getCurrentStyledText();
							if(styledText != null) {
								StyledTextUtils.calcEditorImeCaret(styledText);
							}
						} catch (ClassNotFoundException e1) {
							System.out.println("Fail to get Caret Class ");
							e1.printStackTrace();
						}
					}
					break;

				default:
					break;
				}
			});
		});
	}
	
	//activate caret change
	public static void activate() {

		//create 한/영, insert listener & register
		proc = new WindowProc();
		proc.register();
		
		//create Zoom listener
		fontPropertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if("org.eclipse.jdt.ui.editors.textfont".equals(e.getProperty())) {
					StyledText styledText = StyledTextUtils.getCurrentStyledText();
					if(styledText != null) {
						StyledTextUtils.calcEditorImeCaret(styledText);
					}
				}
			}
		};
		JFaceResources.getFontRegistry().addListener(fontPropertyChangeListener);
		
		//create Part activation listener & register
		partListener = new PartListener();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(partListener);

		
		//calc current activated part
		StyledText styledText = StyledTextUtils.getCurrentStyledText();
		if(styledText != null) {
			StyledTextUtils.calcEditorImeCaret(styledText);
		}

		//seems not working..
		Display.getDefault().disposeExec(new Runnable() {
			@Override
			public void run() {
				proc.deRegister();
			}
		});
	}

	//deactivate caret change
	public static void deActivate() {
		//remove 한/영
		proc.deRegister();
		
		//remove Zoom
		JFaceResources.getFontRegistry().removeListener(fontPropertyChangeListener);
		fontPropertyChangeListener=null;
		
		//remove partListener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().removePartListener(partListener);
		partListener = null;
		
		//reset caret of all editor
		IWorkbench wb = PlatformUI.getWorkbench();
		if(wb != null) {
			IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
			if(window != null) {
				for(IWorkbenchPage page : window.getPages()) {
					for(IEditorReference partReference: page.getEditorReferences()) {
						StyledText styledText =  StyledTextUtils.getStyledTextFromPartRef(partReference);
						Caret caret = styledText.getCaret();
						if(caret instanceof ImeCaret) {
							styledText.setCaret(((ImeCaret) caret).getOri());
						}
					}
				}
			}
		}
	}
	
	/**
	 * part listener for calc caret when part is activated
	 */
	static class PartListener implements IPartListener2 {
		
		@Override
		public void partActivated(IWorkbenchPartReference partReference) {
			StyledText styledText = StyledTextUtils.getStyledTextFromPartRef(partReference);
			if(styledText != null) {
				StyledTextUtils.calcEditorImeCaret(styledText);
			}
		}
		@Override
		public void partVisible(IWorkbenchPartReference partReference) {}
		@Override
		public void partOpened(IWorkbenchPartReference partReference) {
			//remove & add Zoom Listener because AbstractTextEditor adds new Listener when it's opened
			JFaceResources.getFontRegistry().removeListener(fontPropertyChangeListener);
			JFaceResources.getFontRegistry().addListener(fontPropertyChangeListener);
		}
		@Override
		public void partInputChanged(IWorkbenchPartReference partReference) {}
		@Override
		public void partHidden(IWorkbenchPartReference partReference) {}
		@Override
		public void partDeactivated(IWorkbenchPartReference partReference) {}
		@Override
		public void partClosed(IWorkbenchPartReference partReference) {}
		@Override
		public void partBroughtToTop(IWorkbenchPartReference partReference) {}
		
	}
}
