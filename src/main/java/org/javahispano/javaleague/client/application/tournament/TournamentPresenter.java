/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Pagination;
import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.tournament.TournamentPresenter.MyProxy;
import org.javahispano.javaleague.client.application.tournament.TournamentPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.security.CurrentUser;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchLeagueAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchResult;
import org.javahispano.javaleague.shared.dto.MatchDto;
import org.javahispano.javaleague.shared.parameters.LeagueParameters;

import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author alfonso
 *
 */
public class TournamentPresenter extends Presenter<MyView, MyProxy> implements
		TournamentUiHandlers {
	interface MyView extends View, HasUiHandlers<TournamentUiHandlers> {
		ListDataProvider<MatchDto> getListMatchs();

		SimplePager getPager();

		Pagination getPagination();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TOURNAMENT)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<TournamentPresenter> {
	}

	private static final Logger LOGGER = Logger
			.getLogger(TournamentPresenter.class.getName());

	private final DispatchAsync dispatcher;
	private final CurrentUser currentUser;
	
	private List<MatchDto> listMatchDto;

	@Inject
	TournamentPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			DispatchAsync dispatcher, CurrentUser currentUser) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.dispatcher = dispatcher;
		this.currentUser = currentUser;
	}
	
	@Override
	protected void onReveal() {
		getListMatch();
	}
	
	private void getListMatch() {
		ListMatchLeagueAction listMatchLeagueAction = new ListMatchLeagueAction();
		callListMatchAction(listMatchAction);
	}

	private void callListMatchAction(ListMatchAction listMatchAction) {
		dispatcher.execute(listMatchAction,
				new AsyncCallback<ListMatchResult>() {

					@Override
					public void onFailure(Throwable caught) {
						LOGGER.warning("Error on callListMatchAction: "
								+ caught.toString());

					}

					@Override
					public void onSuccess(ListMatchResult result) {
						if (result.getMatchs() == null) {
							listMatchDto = null;
						} else {
							listMatchDto = result.getMatchs();
							getView().getListMatchs().getList().clear();
							getView().getListMatchs().getList().addAll(listMatchDto);
							getView().getListMatchs().flush();
							getView().getPagination().rebuild(getView().getPager());
						}
					}

				});
	}
}
