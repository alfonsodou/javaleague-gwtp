/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import gwtupload.client.SingleUploaderModal;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Small;
import org.javahispano.javaleague.shared.dto.MatchDto;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author alfonso
 *
 */
public class TacticView extends ViewWithUiHandlers<TacticUiHandlers> implements
		TacticPresenter.MyView {

	interface Binder extends UiBinder<Widget, TacticView> {
	}

	@UiField
	SingleUploaderModal singleUploader;
	@UiField
	TextBox teamName;
	@UiField
	Paragraph packageName;
	@UiField
	Small packageNameUser;
	@UiField
	Button playGame;
	@UiField(provided = true)
	CellTable<MatchDto> cellTable = new CellTable<MatchDto>(10);
	@UiField
	Pagination cellTablePagination;
	@UiField
	ImageAnchor imageTeam;

	private SimplePager cellTablePager = new SimplePager();
	private ListDataProvider<MatchDto> cellTableProvider = new ListDataProvider<MatchDto>();

	@Inject
	TacticView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));

		teamName.getElement().setAttribute("placeholder", "Nombre Equipo");

		initTable(cellTable, cellTablePager, cellTablePagination,
				cellTableProvider);
	}

	private void initTable(final AbstractCellTable<MatchDto> grid,
			final SimplePager pager, final Pagination pagination,
			final ListDataProvider<MatchDto> dataProvider) {
		final TextColumn<MatchDto> col1 = new TextColumn<MatchDto>() {

			@Override
			public String getValue(final MatchDto object) {
				return String.valueOf(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_MEDIUM).format(
						object.getDate()));
			}
		};
		grid.addColumn(col1, "Fecha");

		final TextColumn<MatchDto> col2 = new TextColumn<MatchDto>() {

			@Override
			public String getValue(final MatchDto object) {
				return String.valueOf(object.getUserHome().getTeamName()
						+ " vs " + object.getUserAway().getTeamName());
			}
		};
		grid.addColumn(col2, "Partido");

		final TextColumn<MatchDto> col3 = new TextColumn<MatchDto>() {

			@Override
			public String getValue(final MatchDto object) {
				if (object.getMatchPropertiesDto() != null) {
					return String.valueOf(object.getMatchPropertiesDto()
							.getGoalsHome()
							+ " - "
							+ object.getMatchPropertiesDto().getGoalsAway());
				} else {
					return "En juego";
				}
			}
		};
		grid.addColumn(col3, "Resultado");

		final Column<MatchDto, String> col4 = new Column<MatchDto, String>(
				new ButtonCell(ButtonType.PRIMARY, IconType.DOWNLOAD)) {
			@Override
			public String getValue(MatchDto object) {
				return "Descargar";
			}
		};
		col4.setFieldUpdater(new FieldUpdater<MatchDto, String>() {
			@Override
			public void update(int index, MatchDto object, String value) {
				Window.open(
						UploadParameters.getBASE_URL()
								+ "/serveMatchServlet?id="
								+ Long.toString(object.getId()), "_blank", "");
			}
		});
		grid.addColumn(col4, "Descargar");

		final Column<MatchDto, String> col5 = new Column<MatchDto, String>(
				new ButtonCell(ButtonType.PRIMARY, IconType.VIDEO_CAMERA)) {
			@Override
			public String getValue(MatchDto object) {
				return "Ver";
			}
		};

		col5.setFieldUpdater(new FieldUpdater<MatchDto, String>() {
			@Override
			public void update(int index, MatchDto object, String value) {
				Window.open(
						UploadParameters.getBASE_URL()
								+ "/visorwebgl/play.html?"
								+ Long.toString(object.getId()), "_blank", "");
			}
		});
		grid.addColumn(col5, "Ver");

		grid.addRangeChangeHandler(new RangeChangeEvent.Handler() {

			@Override
			public void onRangeChange(final RangeChangeEvent event) {
				pagination.rebuild(pager);
			}
		});

		pager.setDisplay(grid);
		pagination.clear();
		dataProvider.addDataDisplay(grid);
	}

	@Override
	public SingleUploaderModal getSingleUploader() {
		return singleUploader;
	}

	@Override
	public TextBox getTeamName() {
		return teamName;
	}

	@UiHandler("updateTactic")
	void onClickUpdteTactic(ClickEvent e) {
		doUpdateTactic();
	}

	@UiHandler("playGame")
	void onClickPlayGame(ClickEvent e) {
		doPlayGame();
	}
	
	@UiHandler("imageTeam")
	void onClickImageTeam(ClickEvent e) {
		doUploadImage();
	}

	private void doUploadImage() {
		getUiHandlers().showUploadImageModal();
	}
	
	private void doPlayGame() {
		getUiHandlers().playGame();
	}

	private void doUpdateTactic() {
		getUiHandlers().updateTeamNameTactic(teamName.getValue());
	}

	@Override
	public Paragraph getPackageName() {
		return packageName;
	}

	@Override
	public Small getPackageNameUser() {
		return packageNameUser;
	}

	@Override
	public Button getPlayGame() {
		return playGame;
	}

	@Override
	public ListDataProvider<MatchDto> getListMatchs() {
		return cellTableProvider;
	}

	@Override
	public SimplePager getPager() {
		return cellTablePager;
	}

	@Override
	public Pagination getPagination() {
		return cellTablePagination;
	}

	@Override
	public ImageAnchor getImageTeam() {
		return imageTeam;
	}

	@Override
	public void setImageTeam(ImageAnchor imageAnchor) {
		this.imageTeam = imageAnchor;
	}
	
	
}
