/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Container;
import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.tournament.TournamentPresenter.MyProxy;
import org.javahispano.javaleague.client.application.tournament.TournamentPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.shared.dispatch.clasification.ListClasificationAction;
import org.javahispano.javaleague.shared.dispatch.clasification.ListClasificationResult;
import org.javahispano.javaleague.shared.dispatch.journey.ListJourneyAction;
import org.javahispano.javaleague.shared.dispatch.journey.ListJourneyResult;
import org.javahispano.javaleague.shared.dispatch.match.ListFinalMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListFinalMatchResult;
import org.javahispano.javaleague.shared.dispatch.time.GetServerTimeAction;
import org.javahispano.javaleague.shared.dispatch.time.GetServerTimeResult;
import org.javahispano.javaleague.shared.dto.ClasificationDto;
import org.javahispano.javaleague.shared.dto.FinalMatchDto;
import org.javahispano.javaleague.shared.dto.FinalMatchType;
import org.javahispano.javaleague.shared.dto.JourneyDto;
import org.javahispano.javaleague.shared.parameters.LeagueParameters;

import com.google.gwt.user.client.rpc.AsyncCallback;
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
		Container getJourneyContainer();

		Container getClasificationContainer();

		Container getFinalMatchContainer();

		void viewJourney(List<JourneyDto> listJourneyDto, Date serverDate);

		void viewClasification(List<ClasificationDto> listClasificationDto, int round);

		void viewFinalMatch(List<FinalMatchDto> listFinalMatchDto);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TOURNAMENT)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<TournamentPresenter> {
	}

	private static final Logger LOGGER = Logger
			.getLogger(TournamentPresenter.class.getName());

	private final DispatchAsync dispatcher;

	private Date serverDate;

	@Inject
	TournamentPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			DispatchAsync dispatcher) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.dispatcher = dispatcher;
	}

	@Override
	protected void onReveal() {
		getListClasification();
		getListJourney();
		//getListFinalMatch();
	}

	private void getListFinalMatch() {
		getView().getFinalMatchContainer().clear();
		ListFinalMatchAction listFinalMatchAction = new ListFinalMatchAction(
				FinalMatchType.OCTAVOS);
		callListFinalMatchAction(listFinalMatchAction);
	}

	private void callListFinalMatchAction(
			ListFinalMatchAction listFinalMatchAction) {
		dispatcher.execute(listFinalMatchAction,
				new AsyncCallback<ListFinalMatchResult>() {

					@Override
					public void onFailure(Throwable caught) {
						LOGGER.warning("Error on callListFinalMatchAction: "
								+ caught.toString());
					}

					@Override
					public void onSuccess(ListFinalMatchResult result) {
						if (result.getListFinalMatchDto() == null) {
							LOGGER.warning("*** No están creados los partidos finales ***");
						} else {
							getView().viewFinalMatch(
									result.getListFinalMatchDto());
						}
					}
				});
	}

	private void getListClasification() {
		getView().getClasificationContainer().clear();
		ListClasificationAction listClasificationAction = new ListClasificationAction(
				LeagueParameters.getLeagueId());
		callListClasificationAction(listClasificationAction);
	}

	private void callListClasificationAction(
			ListClasificationAction listClasificationAction) {
		dispatcher.execute(listClasificationAction,
				new AsyncCallback<ListClasificationResult>() {

					@Override
					public void onFailure(Throwable caught) {
						LOGGER.warning("Error on callListClasificationAction: "
								+ caught.toString());

					}

					@Override
					public void onSuccess(ListClasificationResult result) {
						if (result.getListClasification() == null) {
							LOGGER.warning("*** No hay clasificación creada ***");
						} else {
							getView().viewClasification(
									result.getListClasification(), result.getRound());
						}

					}

				});
	}

	private void getListJourney() {
		getView().getJourneyContainer().clear();
		ListJourneyAction listJourneyAction = new ListJourneyAction(
				LeagueParameters.getLeagueId());
		callListJourneyAction(listJourneyAction);
	}

	private void callListJourneyAction(ListJourneyAction listJourneyAction) {
		dispatcher.execute(listJourneyAction,
				new AsyncCallback<ListJourneyResult>() {

					@Override
					public void onFailure(Throwable caught) {
						LOGGER.warning("Error on callListJourneyAction: "
								+ caught.toString());
					}

					@Override
					public void onSuccess(ListJourneyResult result) {
						if (result.getListJourneyDto() == null) {
							LOGGER.warning("** No hay jornadas creadas **");
						} else {
							serverDate = result.getServerDate();
							getView().viewJourney(result.getListJourneyDto(),
									serverDate);
						}
					}

				});
	}

	private void getServerTime() {
		callGetServerTime(new GetServerTimeAction());
	}

	private void callGetServerTime(GetServerTimeAction getServerTimeAction) {
		dispatcher.execute(getServerTimeAction,
				new AsyncCallback<GetServerTimeResult>() {

					@Override
					public void onFailure(Throwable caught) {
						LOGGER.warning("Error on callGetServerTime: "
								+ caught.toString());
					}

					@Override
					public void onSuccess(GetServerTimeResult result) {
						if (result != null) {
							serverDate = result.getDate();
						}
					}
				});
	}

	@Override
	public Date getServerDate() {
		return serverDate;
	}
}
