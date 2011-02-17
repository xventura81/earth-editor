package earth.editor;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class Earth_editor implements EntryPoint {
        
        public void onModuleLoad() {
    		try{
    			TabPanel tp = new TabPanel();
    	
    			EarthEditorViewer earthEditorViewer = new EarthEditorViewer();
    			tp.add(earthEditorViewer, "Earth Editor");
    			
    			tp.selectTab(0);
    			RootPanel.get("gwtGL").add(tp);
    		} catch (Exception e) {
    			Window.alert("Sorry, Your Browser doesn't support WebGL!");
    		}
        }
}
