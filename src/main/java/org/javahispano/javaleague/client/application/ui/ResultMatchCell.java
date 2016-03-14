/**
 * 
 */
package org.javahispano.javaleague.client.application.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * @author alfonso
 *
 */
public class ResultMatchCell extends AbstractCell<SafeHtml> {

	public ResultMatchCell() {
		super("click", "keydown");
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			SafeHtml value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.append(value);
		}
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, SafeHtml value,
			NativeEvent event, ValueUpdater<SafeHtml> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("click".equals(event.getType())) {
			onEnterKeyDown(context, parent, value, event, valueUpdater);
		}
	}

	@Override
	protected void onEnterKeyDown(Context context, Element parent,
			SafeHtml value, NativeEvent event,
			ValueUpdater<SafeHtml> valueUpdater) {
		if (valueUpdater != null) {
			valueUpdater.update(value);
		}
	}

}
