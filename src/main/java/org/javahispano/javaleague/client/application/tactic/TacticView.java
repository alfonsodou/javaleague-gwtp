/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import gwtupload.client.SingleUploader;

import java.util.Date;

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
import org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;
import org.gwtbootstrap3.extras.notify.client.ui.NotifySettings;
import org.javahispano.javaleague.client.application.ui.ResultMatchCell;
import org.javahispano.javaleague.shared.dto.MatchDto;
import org.javahispano.javaleague.shared.parameters.MatchParameters;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
	SingleUploader singleUploader;
	@UiField
	TextBox teamName;
	@UiField
	Paragraph packageName;
	@UiField
	Small packageNameUser;
	@UiField
	Button playGame;
	@UiField
	Button refreshGame;
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
		final TextColumn<MatchDto> colFecha = new TextColumn<MatchDto>() {

			@Override
			public String getValue(final MatchDto object) {
				return String.valueOf(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_MEDIUM).format(
						object.getDate()));
			}
		};
		colFecha.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		colFecha.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerFecha = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">Fecha</p></strong>";
			}
		});
		grid.addColumn(colFecha, headerFecha);

		final TextColumn<MatchDto> col1 = new TextColumn<MatchDto>() {

			@Override
			public String getValue(final MatchDto object) {
				return String.valueOf(object.getUserHome().getTeamName());
			}
		};
		col1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol1 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">Local</p></strong>";
			}
		});
		grid.addColumn(col1, headerCol1);

		final Column<MatchDto, String> imageColumn = new Column<MatchDto, String>(
				new ImageCell()) {

			@Override
			public String getValue(MatchDto object) {
				if (object.getUserHome().isLogo()) {
					return UploadParameters.getBASE_URL()
							+ "/serveTeamImageServlet?id="
							+ object.getUserHome().getId() + "&min=OK&"
							+ System.currentTimeMillis();
				} else {
					return UploadParameters.getBASE_URL()
							+ "/images/sin_escudo_min.png";
				}
			}
		};
		imageColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		imageColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(imageColumn);

		final Column<MatchDto, SafeHtml> col2 = new Column<MatchDto, SafeHtml>(
				new ResultMatchCell()) {
			@Override
			public SafeHtml getValue(MatchDto object) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				if (object.getMatchPropertiesDto() != null) {
					Date now = new Date();
					long d = (now.getTime() - object.getDate().getTime())
							/ (1000 * 60);
					if (d < 60) {
						sb.appendHtmlConstant("<a href=\"javascript:;\">");
						sb.appendEscaped("Pulsa para ver el resultado");
						sb.appendHtmlConstant("</a>");
					} else {
						sb.appendHtmlConstant("<div>"
								+ object.getMatchPropertiesDto().getGoalsHome()
								+ " - "
								+ object.getMatchPropertiesDto().getGoalsAway()
								+ "</div>");
					}
				} else {
					sb.appendHtmlConstant("<div>En juego</div>");
				}
				return sb.toSafeHtml();
			}
		};

		col2.setFieldUpdater(new FieldUpdater<MatchDto, SafeHtml>() {
			@Override
			public void update(int index, MatchDto object, SafeHtml value) {
				NotifySettings settings = NotifySettings.newSettings();
				settings.setType(NotifyType.INFO);
				settings.setPlacement(NotifyPlacement.TOP_CENTER);
				settings.setAllowDismiss(true);
				Notify.notify("", object.getUserHome().getTeamName() + " "
						+ object.getMatchPropertiesDto().getGoalsHome() + " - "
						+ object.getMatchPropertiesDto().getGoalsAway() + " "
						+ object.getUserAway().getTeamName(),
						IconType.CALENDAR, settings);
			}
		});

		col2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(col2, "");

		final Column<MatchDto, String> imageColumn2 = new Column<MatchDto, String>(
				new ImageCell()) {

			@Override
			public String getValue(MatchDto object) {
				if (object.getUserAway().isLogo()) {
					return UploadParameters.getBASE_URL()
							+ "/serveTeamImageServlet?id="
							+ object.getUserAway().getId() + "&min=OK&"
							+ System.currentTimeMillis();
				} else {
					return UploadParameters.getBASE_URL()
							+ "/images/sin_escudo_min.png";
				}
			}
		};
		imageColumn2
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		imageColumn2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(imageColumn2);

		final TextColumn<MatchDto> col3 = new TextColumn<MatchDto>() {

			@Override
			public String getValue(final MatchDto object) {
				return String.valueOf(object.getUserAway().getTeamName());
			}
		};
		col3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol3 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">Visitante</p></strong>";
			}
		});
		grid.addColumn(col3, headerCol3);

		final Column<MatchDto, String> col4 = new Column<MatchDto, String>(
				new ButtonCell(ButtonType.PRIMARY, IconType.DOWNLOAD)) {
			@Override
			public String getValue(MatchDto object) {
				return "";
			}
		};
		col4.setFieldUpdater(new FieldUpdater<MatchDto, String>() {
			@Override
			public void update(int index, MatchDto object, String value) {
				if (object.getState() == MatchParameters.getMATCHSTATE_FINISH()) {
					Window.open(
							UploadParameters.getBASE_URL()
									+ "/serveMatchServlet?id="
									+ Long.toString(object.getId()), "_blank",
							"");
				} else {
					NotifySettings settings = NotifySettings.newSettings();
					settings.setType(NotifyType.INFO);
					settings.setPlacement(NotifyPlacement.TOP_CENTER);
					settings.setAllowDismiss(true);
					Notify.notify("", "Partido no disponible",
							IconType.CALENDAR, settings);
				}
			}
		});
		col4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(col4, "");

		final Column<MatchDto, String> col5 = new Column<MatchDto, String>(
				new ButtonCell(ButtonType.PRIMARY, IconType.VIDEO_CAMERA)) {
			@Override
			public String getValue(MatchDto object) {
				return "";
			}
		};

		col5.setFieldUpdater(new FieldUpdater<MatchDto, String>() {
			@Override
			public void update(int index, MatchDto object, String value) {
				if (object.getState() == MatchParameters.getMATCHSTATE_FINISH()) {
					Window.open(
							UploadParameters.getBASE_URL()
									+ "/visorwebgl/play.html?"
									+ Long.toString(object.getId()), "_blank",
							"");
				} else {
					NotifySettings settings = NotifySettings.newSettings();
					settings.setType(NotifyType.INFO);
					settings.setPlacement(NotifyPlacement.TOP_CENTER);
					settings.setAllowDismiss(true);
					Notify.notify("", "Partido no disponible",
							IconType.CALENDAR, settings);
				}
			}
		});
		col5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(col5, "");

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
	public SingleUploader getSingleUploader() {
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

	@UiHandler("refreshGame")
	void onClickRefreshGame(ClickEvent e) {
		doRefreshGame();
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

	private void doRefreshGame() {
		getUiHandlers().refreshGame();
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

	@Override
	public Button getRefreshGame() {
		return refreshGame;
	}

}
