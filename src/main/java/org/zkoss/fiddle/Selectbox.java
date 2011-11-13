package org.zkoss.fiddle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.fiddle.event.OptionSelectedEvent;
import org.zkoss.lang.Classes;
import org.zkoss.lang.Objects;
import org.zkoss.zk.au.out.AuInvoke;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;


public class Selectbox extends HtmlBasedComponent {

	private String _name;
	private boolean _disabled;
	private int _jsel = -1;
	private String _value = null;
	private int _optionSize = 0;
	
	private transient ListModel _model;
	private transient ListDataListener _dataListener;
	private transient OptionRenderer _renderer;
	private static final String ATTR_ON_INIT_RENDER_POSTED = "org.zkoss.zul.onInitLaterPosted";
	
	private Map _optionValues = new HashMap<Integer,Object>();
	
	static {
		addClientEvent(Selectbox.class, Events.ON_SELECT, 0);
		addClientEvent(Selectbox.class, Events.ON_FOCUS, 0);
		addClientEvent(Selectbox.class, Events.ON_BLUR, 0);
	}
	/**
	 * Returns the index of the selected item (-1 if no one is selected).
	 */
	public int getSelectedIndex() {
		return _jsel;
	}

	public void setSelectedIndex(int jsel) {
		if (jsel < -1)
			jsel = -1;
		if (jsel != _jsel) {
			_jsel = jsel;
			smartUpdate("selectedIndex", jsel);
		}
	}
	
	/**
	 * If value not found , will do nothing,
	 * and this one will not effect immediately.
	 * It's actually a client side method.
	 * @param value
	 */
	public void doSelectedValue(String value) {
		response(new AuInvoke(this,"selectValue",value));
	}
	
	public OptionRenderer getOptionRenderer() {
		return _renderer;
	}

	public void setOptionRenderer(OptionRenderer renderer) {
		if (_renderer != renderer) {
			_renderer = renderer;
			invalidate();
		}
	}
	public void setOptionRenderer(String clsnm) throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InstantiationException, java.lang.reflect.InvocationTargetException {
		if (clsnm != null)
			setOptionRenderer((OptionRenderer) Classes
					.newInstanceByThread(clsnm));
	}

	/**
	 * Returns whether it is disabled.
	 * <p>
	 * Default: false.
	 */
	public boolean isDisabled() {
		return _disabled;
	}

	
	protected boolean isChildable() {
		return false;
	}
	/**
	 * Sets whether it is disabled.
	 */
	public void setDisabled(boolean disabled) {
		if (_disabled != disabled) {
			_disabled = disabled;
			smartUpdate("disabled", _disabled);
		}
	}

	/**
	 * Returns the name of this component.
	 * <p>
	 * Default: null.
	 * <p>
	 * The name is used only to work with "legacy" Web application that handles
	 * user's request by servlets. It works only with HTTP/HTML-based browsers.
	 * It doesn't work with other kind of clients.
	 * <p>
	 * Don't use this method if your application is purely based on ZK's
	 * event-driven model.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Sets the name of this component.
	 * <p>
	 * The name is used only to work with "legacy" Web application that handles
	 * user's request by servlets. It works only with HTTP/HTML-based browsers.
	 * It doesn't work with other kind of clients.
	 * <p>
	 * Don't use this method if your application is purely based on ZK's
	 * event-driven model.
	 * 
	 * @param name
	 *            the name of this component.
	 */
	public void setName(String name) {
		if (name != null && name.length() == 0)
			name = null;
		if (!Objects.equals(_name, name)) {
			_name = name;
			smartUpdate("name", name);
		}
	}

	private void initDataListener() {
		if (_dataListener == null)
			_dataListener = new ListDataListener() {
				public void onChange(ListDataEvent event) {
					postOnInitRender();
				}
			};
		_model.addListDataListener(_dataListener);
	}
	
	public void setModel(ListModel model) {
		if (model != null) {
			if (_model != model) {
				if (_model != null) {
					_model.removeListDataListener(_dataListener);
				}
				_model = model;
				initDataListener();
				postOnInitRender();
			}
		} else if (_model != null) {
			_model.removeListDataListener(_dataListener);
			_model = null;
			invalidate();
		}
	}

	public void onInitRender() {
		removeAttribute(ATTR_ON_INIT_RENDER_POSTED);
		invalidate();
	}
	private void postOnInitRender() {
		if (getAttribute(ATTR_ON_INIT_RENDER_POSTED) == null) {
			setAttribute(ATTR_ON_INIT_RENDER_POSTED, Boolean.TRUE);
			Events.postEvent("onInitRender", this, null);
		}
	}
	
	public ListModel getModel() {
		return _model;
	}

	public OptionRenderer getRealRenderer() {
		final OptionRenderer renderer = getOptionRenderer();
		return renderer != null ? renderer : _defRend; 
	}
	
	private static final OptionRenderer _defRend = new OptionRenderer() {
		public void render(Selectbox comp,OptionBuilder builder, Object data) throws Exception {
			builder.appendOption(Objects.toString(data));
		}
	};
	
	/**
	 * A way to keep option's value ,
	 * it's a implicit operator with {@link OptionBuilder#appendOption(String, String, Object)}. 
	 * @param index
	 * @param val
	 */
	protected void setOptionValue(int index,Object val){
		_optionValues.put(index,val);
	}
	
	/**
	 * A way to get the value set in OptionRenderer,to use this method, 
	 * you must invoke the setOptionValue in OptionRenderer first.
	 * 
	 * Note: it's not getting the number immediately ,
	 * Cause to our component lifecycle ,
	 * if you invoke setModel or setRenderer or you are rendering the component, 
	 * you have to wait for next AU to get correct option size.
	 * 
	 * @param index
	 * @return
	 */
	public Object getOptionValue(int index){
		return (Object) _optionValues.get(index);
	}
	
	/**
	 * A method to know how many options we have now . 
	 * Note: it's not getting the number immediately ,
	 * Cause to our component lifecycle ,
	 * if you invoke setModel or setRenderer or you are rendering the component, 
	 * you have to wait for next AU to get correct option size.
	 * 
	 * @return
	 */
	public int getOptionSize(){
		return _optionSize;
	}
	
	private static class InnerOptionBuilder implements OptionBuilder{
		private List<String[]> items;
		private boolean grouping = false;
		private Selectbox selectbox;

		private int index = 0;
		
		public InnerOptionBuilder(Selectbox selectbox){
			this.selectbox = selectbox;
			items = new ArrayList<String[]>();
		}
		
		private void doGroup(String text){
			if(grouping) {
				throw new IllegalStateException("HTML doesn't support nest option group (optgroup).");
			}
			items.add(new String[]{text,null,"1"});
			grouping = true;
		}
		private void endGroup(){
			grouping = false;
			items.add(new String[]{null,null,"0"});
		}
		
		public int getIndex() {
			return index;
		}
		
		private void addItem(String[] item){
			index++;
			items.add(item);
		}
		
		public void appendOption(String text) {
			addItem(new String[]{text});
		}

		public void appendOption(String text, String value) {
			addItem(new String[]{text,value});
		}

		public void appendOptionGroup(String text, List subModel, OptionRenderer subOptionRender) throws Exception {
			doGroup(text);
			
			for(Object o : subModel){
				subOptionRender.render(selectbox,this,o);
			}
			
			endGroup();
		}
		
		public List<String[]> getItems() {
			return items;
		}

		public void appendOption(String text, String inputvalue, Object value) {
			selectbox.setOptionValue(index,value);
			appendOption(text,inputvalue);
		}
		
	};
	
	// -- ComponentCtrl --//
	protected void renderProperties(org.zkoss.zk.ui.sys.ContentRenderer renderer)
			throws java.io.IOException {
		super.renderProperties(renderer);

		render(renderer, "name", _name);
		render(renderer, "disabled", isDisabled());
		renderer.render("selectedIndex", _jsel);
		
		if (_model != null) {
			_optionValues.clear();
			_optionSize = 0 ;
	        OptionRenderer render = getRealRenderer();
	        
	        InnerOptionBuilder builder = new InnerOptionBuilder(this);
			for (int i = 0; i < _model.getSize(); i++) {	            
				Object value = _model.getElementAt(i);
				try {
					render.render(this,builder, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			_optionSize = builder.getIndex();
			render(renderer, "items",builder.getItems());
		}
	}

	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
		final String cmd = request.getCommand();
		if (cmd.equals(Events.ON_SELECT)) {
			Map data = request.getData();
				
			_jsel = ((Integer)data.get("index")).intValue();
			_value = ((String)data.get("value"));
			Events.postEvent(new OptionSelectedEvent(
					Events.ON_SELECT, this, _jsel,_value,getOptionValue(_jsel)));
		}
	}
	// Cloneable//
	public Object clone() {
		final Selectbox clone = (Selectbox) super.clone();
		if (clone._model != null) {
			// we use the same data model but we have to create a new listener
			clone._dataListener = null;
			clone.initDataListener();
		}
		return clone;
	}


	// -- Serializable --//
	// NOTE: they must be declared as private
	private synchronized void writeObject(java.io.ObjectOutputStream s)
			throws java.io.IOException {
		s.defaultWriteObject();

		willSerialize(_model);
		s.writeObject(_model instanceof java.io.Serializable
				|| _model instanceof java.io.Externalizable ? _model : null);
		willSerialize(_renderer);
		s.writeObject(_renderer instanceof java.io.Serializable
				|| _renderer instanceof java.io.Externalizable ? _renderer
				: null);
	}

	private synchronized void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();

		_model = (ListModel) s.readObject();
		didDeserialize(_model);
		_renderer = (OptionRenderer) s.readObject();
		didDeserialize(_renderer);
		if (_model != null) {
			initDataListener();
		}
	}

	public void sessionWillPassivate(Page page) {
		super.sessionWillPassivate(page);
		willPassivate(_model);
		willPassivate(_renderer);
	}

	public void sessionDidActivate(Page page) {
		super.sessionDidActivate(page);
		didActivate(_model);
		didActivate(_renderer);
	}
	
	public String getValue() {
		return _value;
	}
	
	public void setValue(String _value) {
		this._value = _value;
	}
}
