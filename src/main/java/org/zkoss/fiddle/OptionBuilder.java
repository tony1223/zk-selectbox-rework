package org.zkoss.fiddle;

import java.util.List;

public interface OptionBuilder {

	/**
	 * A way to get current option index , it's also including options in group if exist.
	 * @return
	 */
	public int getIndex();
	
	/**
	 * Append a option to select
	 * @param text
	 */
	public void appendOption(String text);

	/**
	 * Append a option to select , and have specific value.
	 * @param text
	 * @param value
	 */
	public void appendOption(String text, String inputvalue);
	
	/**
	 * Append a option to select , and have specific value.
	 * If you set the value option , you could get the value from 
	 * {@link Selectbox#getOptionValue(int)}
	 * @param text
	 * @param inputvalue
	 * @param value
	 */
	public void appendOption(String text, String inputvalue,Object value);
	/**
	 * Append a group to select .
	 * Note:you can't have nest optgroup in select since HTML didn't allow it.
	 * @param text
	 * @param subModel
	 * @param subOptionRender
	 * @throws Exception
	 */
	public void appendOptionGroup(String text, List subModel, OptionRenderer subOptionRender) throws Exception;
	
}
