package test;

import java.util.Arrays;

import org.zkoss.fiddle.OptionBuilder;
import org.zkoss.fiddle.OptionRenderer;
import org.zkoss.fiddle.Selectbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;

public class SelectboxTestComposer extends GenericForwardComposer {

	private Selectbox select;
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	public ListModelList getList(){
		return new ListModelList(
				Arrays.asList(new String[]{"test","test2","test3","test4"})		
			);
	}
}
