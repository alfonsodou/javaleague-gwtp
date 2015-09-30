/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Span;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;
import org.gwtbootstrap3.extras.notify.client.ui.NotifySettings;
import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.tactic.TacticPresenter.MyProxy;
import org.javahispano.javaleague.client.application.tactic.TacticPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.resources.TacticMessages;
import org.javahispano.javaleague.client.security.CurrentUser;
import org.javahispano.javaleague.shared.api.UserResource;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticAction;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticResult;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author alfonso
 *
 */
public class TacticPresenter extends Presenter<MyView, MyProxy> implements
		TacticUiHandlers {
	interface MyView extends View, HasUiHandlers<TacticUiHandlers> {
		SingleUploader getSingleUploader();

		TextBox getTeamName();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TACTIC)
	interface MyProxy extends ProxyPlace<TacticPresenter> {
	}

	private static final Logger LOGGER = Logger.getLogger(TacticPresenter.class
			.getName());

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	private final ResourceDelegate<UserResource> userResource;
	private final CurrentUser currentUser;
	private final TacticMessages messages;

	@Inject
	TacticPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			ResourceDelegate<UserResource> userResource,
			CurrentUser currentUser, TacticMessages messages) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);
		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.userResource = userResource;
		this.currentUser = currentUser;
		this.messages = messages;

		getView().setUiHandlers(this);
		getView().getSingleUploader().addOnStartUploadHandler(
				onStartUploaderHandler);
		getView().getSingleUploader().addOnFinishUploadHandler(
				onFinishUploaderHandler);
		getView().getSingleUploader().addOnCancelUploadHandler(
				onCancelUploaderHandler);
	}

	private IUploader.OnStartUploaderHandler onStartUploaderHandler = new IUploader.OnStartUploaderHandler() {
		@Override
		public void onStart(IUploader uploader) {
			OnStartUploadTactic();
		}
	};

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		@Override
		public void onFinish(IUploader uploader) {
			if ((uploader.getStatus() == Status.SUCCESS)
					&& (uploader.getServerMessage().getMessage()
							.equals(UploadParameters.getVALIDATETACTICOK()))) {
				OnSuccessfulUploadTactic();
			} else if (uploader.getServerMessage().getMessage()
					.equals(UploadParameters.getERRORPACKAGENAME())) {
				OnErrorUploadTactic(messages.onErrorPackageNameUploadTactic());
			} else if (uploader.getServerMessage().getMessage()
					.equals(UploadParameters.getERRORINTERFACETACTIC())) {
				OnErrorUploadTactic(messages
						.onErrorExistsInterfaceTacticUploadTactic());
			} else
				OnErrorUploadTactic(messages.onErrorValidateUploadTactic()
						+ uploader.getServerMessage().getMessage());

		}
	};

	private IUploader.OnCancelUploaderHandler onCancelUploaderHandler = new IUploader.OnCancelUploaderHandler() {
		@Override
		public void onCancel(IUploader uploader) {
			OnCancelUploadTactic();
		}
	};

	private void OnErrorUploadTactic(String error) {

		final Modal modal = new Modal();
		modal.setTitle(messages.onErrorUploadTactic());
		modal.setClosable(true);
		final ModalBody modalBody = new ModalBody();
		modalBody.add(new Span(error));

		final ModalFooter modalFooter = new ModalFooter();
		Button closeButton = new Button(messages.closeModal(),
				new ClickHandler() {
					@Override
					public void onClick(final ClickEvent event) {
						modal.hide();
					}
				});
		closeButton.setType(ButtonType.DANGER);
		modalFooter.add(closeButton);

		modal.add(modalBody);
		modal.add(modalFooter);

		modal.show();
	}

	private void OnStartUploadTactic() {
		NotifySettings settings = NotifySettings.newSettings();
		settings.setType(NotifyType.INFO);
		settings.setPlacement(NotifyPlacement.TOP_CENTER);
		settings.setAllowDismiss(false);
		Notify.notify(messages.title(), messages.onStartUploadTactic(),
				IconType.FILE_CODE_O, settings);

		/*
		 * DisplayMessageEvent.fire(this, new
		 * Message(messages.onStartUploadTactic(), MessageStyle.SUCCESS,
		 * MessageCloseDelay.HIGH));
		 */
	}

	private void OnSuccessfulUploadTactic() {
		NotifySettings settings = NotifySettings.newSettings();
		settings.setType(NotifyType.SUCCESS);
		settings.setPlacement(NotifyPlacement.TOP_CENTER);
		settings.setAllowDismiss(false);
		Notify.notify(messages.title(), messages.onSuccessfulUploadTactic(),
				IconType.FILE_CODE_O, settings);

		/*
		 * DisplayMessageEvent.fire(this, new
		 * Message(messages.onSuccessfulUploadTactic(), MessageStyle.SUCCESS));
		 */
	}

	private void OnCancelUploadTactic() {
		NotifySettings settings = NotifySettings.newSettings();
		settings.setType(NotifyType.DANGER);
		settings.setPlacement(NotifyPlacement.TOP_CENTER);
		settings.setAllowDismiss(false);
		Notify.notify(messages.title(), messages.onCancelUploadTactic(),
				IconType.FILE_CODE_O, settings);

		/*
		 * DisplayMessageEvent .fire(this, new
		 * Message(messages.onCancelUploadTactic(), MessageStyle.ERROR));
		 */
	}

	@Override
	public void updateTeamNameTactic(String teamName) {
		UpdateTacticAction updateTacticAction = new UpdateTacticAction(
				currentUser.getUser().getId(), teamName);
		callUpdateTacticAction(updateTacticAction);
	}

	private void callUpdateTacticAction(UpdateTacticAction updateTacticAction) {
		dispatcher.execute(updateTacticAction,
				new AsyncCallback<UpdateTacticResult>() {

					@Override
					public void onFailure(Throwable caught) {
						NotifySettings settings = NotifySettings.newSettings();
						settings.setType(NotifyType.DANGER);
						settings.setPlacement(NotifyPlacement.TOP_CENTER);
						settings.setAllowDismiss(false);
						Notify.notify(messages.title(),
								messages.onErrorUpdateTactic(),
								IconType.FILE_CODE_O, settings);

						LOGGER.warning("Error on callUpdateTacticAction: "
								+ caught.toString());
					}

					@Override
					public void onSuccess(UpdateTacticResult result) {
						NotifySettings settings = NotifySettings.newSettings();
						settings.setType(NotifyType.INFO);
						settings.setPlacement(NotifyPlacement.TOP_CENTER);
						settings.setAllowDismiss(false);
						Notify.notify(messages.title(),
								messages.onUpdateTactic(),
								IconType.FILE_CODE_O, settings);

						LOGGER.info("callUpdateTacticAction: Nombre equipo actualizado correctamente");
					}

				});
	}
}