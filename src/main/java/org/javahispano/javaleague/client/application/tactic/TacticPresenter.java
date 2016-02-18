/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploaderModal;

import java.util.List;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Small;
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
import org.javahispano.javaleague.shared.dispatch.match.ListMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchResult;
import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchResult;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticAction;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticResult;
import org.javahispano.javaleague.shared.dto.MatchDto;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author alfonso
 *
 */
public class TacticPresenter extends Presenter<MyView, MyProxy> implements
		TacticUiHandlers {
	interface MyView extends View, HasUiHandlers<TacticUiHandlers> {
		SingleUploaderModal getSingleUploader();

		TextBox getTeamName();

		Paragraph getPackageName();

		Small getPackageNameUser();

		Button getPlayGame();

		ListDataProvider<MatchDto> getListMatchs();

		SimplePager getPager();

		Pagination getPagination();

		ImageAnchor getImageTeam();

		void setImageTeam(ImageAnchor imageAnchor);
	}

	@ProxyStandard
	@NameToken(NameTokens.TACTIC)
	interface MyProxy extends ProxyPlace<TacticPresenter> {
	}

	private static final Logger LOGGER = Logger.getLogger(TacticPresenter.class
			.getName());

	private final DispatchAsync dispatcher;
	private final CurrentUser currentUser;
	private final TacticMessages messages;

	private List<MatchDto> listMatchDto;

	private Modal modalImage;

	@Inject
	TacticPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			DispatchAsync dispatcher, CurrentUser currentUser,
			TacticMessages messages) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);
		this.dispatcher = dispatcher;
		this.currentUser = currentUser;
		this.messages = messages;

		createModalImage();

		getView().setUiHandlers(this);
		getView().getSingleUploader().addOnStartUploadHandler(
				onStartUploaderHandler);
		getView().getSingleUploader().addOnFinishUploadHandler(
				onFinishUploaderHandler);
		getView().getSingleUploader().addOnCancelUploadHandler(
				onCancelUploaderHandler);
	}

	@Override
	protected void onReveal() {
		getView().getTeamName().setText(currentUser.getUser().getTeamName());
		getView().getPackageName().setText(messages.packageName());
		getView().getPackageNameUser().setText(
				UploadParameters.getPACKAGENAME()
						+ currentUser.getUser().getId().toString());
		getView().getPlayGame().setEnabled(
				!currentUser.getUser().isAwaitingMatch());
		getListMatch();
	}

	private IUploader.OnStartUploaderHandler onStartUploaderHandler = new IUploader.OnStartUploaderHandler() {
		@Override
		public void onStart(IUploader uploader) {
			OnStartUploadTactic();
		}
	};

	private IUploader.OnFinishUploaderHandler onFinishUploaderImageHandler = new IUploader.OnFinishUploaderHandler() {
		@Override
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				modalImage.hide();
				getView().getImageTeam().setUrl(
						uploader.getServerInfo().getFileUrl());
				Window.alert(uploader.getServerInfo().getFileUrl());
			}
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

	private void createModalImage() {
		modalImage = new Modal();
		modalImage.setTitle(messages.titleUploadImage());
		modalImage.setClosable(false);
		final ModalBody modalBody = new ModalBody();
		SingleUploaderModal singleUploaderModal = new SingleUploaderModal(
				FileInputType.BROWSER_INPUT);
		singleUploaderModal.avoidEmptyFiles(false);
		singleUploaderModal.setValidExtensions("gif", "jpg", "jpeg", "png");
		singleUploaderModal
				.addOnFinishUploadHandler(onFinishUploaderImageHandler);
		modalBody.add(singleUploaderModal);

		final ModalFooter modalFooter = new ModalFooter();

		Button closeButton = new Button(messages.closeModal(),
				new ClickHandler() {
					@Override
					public void onClick(final ClickEvent event) {
						modalImage.hide();
					}
				});
		closeButton.setType(ButtonType.DANGER);
		modalFooter.add(closeButton);

		modalImage.add(modalBody);
		modalImage.add(modalFooter);

		modalImage.hide();
	}

	private void OnErrorUploadTactic(String error) {
		Notify.hideAll();
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
		settings.setDelay(0);
		Notify.notify(messages.title(), messages.onStartUploadTactic(),
				IconType.FILE_CODE_O, settings);
	}

	private void OnSuccessfulUploadTactic() {
		Notify.hideAll();
		NotifySettings settings = NotifySettings.newSettings();
		settings.setType(NotifyType.SUCCESS);
		settings.setPlacement(NotifyPlacement.TOP_CENTER);
		settings.setAllowDismiss(false);
		Notify.notify(messages.title(), messages.onSuccessfulUploadTactic(),
				IconType.FILE_CODE_O, settings);
	}

	private void OnCancelUploadTactic() {
		Notify.hideAll();
		NotifySettings settings = NotifySettings.newSettings();
		settings.setType(NotifyType.DANGER);
		settings.setPlacement(NotifyPlacement.TOP_CENTER);
		settings.setAllowDismiss(false);
		Notify.notify(messages.title(), messages.onCancelUploadTactic(),
				IconType.FILE_CODE_O, settings);
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

	@Override
	public void playGame() {
		RegisterMatchAction registerMatchAction = new RegisterMatchAction(
				currentUser.getUser());
		callRegisterMatchAction(registerMatchAction);
	}

	private void callRegisterMatchAction(RegisterMatchAction registerMatchAction) {
		dispatcher.execute(registerMatchAction,
				new AsyncCallback<RegisterMatchResult>() {

					@Override
					public void onFailure(Throwable caught) {
						NotifySettings settings = NotifySettings.newSettings();
						settings.setType(NotifyType.DANGER);
						settings.setPlacement(NotifyPlacement.TOP_CENTER);
						settings.setAllowDismiss(false);
						Notify.notify(messages.title(),
								messages.onErrorRegisterMatch(),
								IconType.FILE_CODE_O, settings);

						LOGGER.warning("Error on callRegisterMatchAction: "
								+ caught.toString());
					}

					@Override
					public void onSuccess(RegisterMatchResult result) {
						NotifySettings settings = NotifySettings.newSettings();
						settings.setType(NotifyType.INFO);
						settings.setPlacement(NotifyPlacement.TOP_CENTER);
						settings.setAllowDismiss(false);
						Notify.notify(messages.title(),
								messages.onRegisterMatch(),
								IconType.FILE_CODE_O, settings);

						if (result.getMathDto() == null) {
							getView().getPlayGame().setEnabled(false);
						}

						LOGGER.info("callRegisterMatchAction: Solicitud partido amistoso registrada correctamente");
					}
				});
	}

	private void getListMatch() {
		ListMatchAction listMatchAction = new ListMatchAction(
				currentUser.getUser());
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
							getView().getListMatchs().getList()
									.addAll(listMatchDto);
							getView().getListMatchs().flush();
							getView().getPagination().rebuild(
									getView().getPager());
						}
					}

				});
	}

	@Override
	public void showUploadImageModal() {
		modalImage.show();
	}
}