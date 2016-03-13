/**
 * 
 */
package org.javahispano.javaleague.client.application.ui;

import org.gwtbootstrap3.extras.card.client.ui.Back;
import org.gwtbootstrap3.extras.card.client.ui.Card;
import org.gwtbootstrap3.extras.card.client.ui.Front;
import org.javahispano.javaleague.shared.dto.MatchDto;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * @author alfonso
 *
 */
public class ResultMatchCell extends AbstractCell<MatchDto> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			MatchDto value, SafeHtmlBuilder sb) {
		if (value.getMatchPropertiesDto() != null) {
			Card card = new Card();
			Front front = new Front();
			front.setTitle("Pulsa para ver el resultado");
			Back back = new Back();
			back.setTitle(String.valueOf(value.getMatchPropertiesDto()
					.getGoalsHome()
					+ " - "
					+ value.getMatchPropertiesDto().getGoalsAway()));
			card.add(front);
			card.add(back);
			sb.appendEscaped(card.getElement().getString());
		} else {
			sb.appendHtmlConstant("<p>Sin comenzar</p>");
		}
	}

}
