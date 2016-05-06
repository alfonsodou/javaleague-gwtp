/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyPlacement;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;
import org.gwtbootstrap3.extras.notify.client.ui.NotifySettings;
import org.javahispano.javaleague.client.application.ui.ResultMatchCell;
import org.javahispano.javaleague.shared.dto.ClasificationDto;
import org.javahispano.javaleague.shared.dto.FinalMatchDto;
import org.javahispano.javaleague.shared.dto.FinalMatchType;
import org.javahispano.javaleague.shared.dto.JourneyDto;
import org.javahispano.javaleague.shared.dto.MatchDto;
import org.javahispano.javaleague.shared.parameters.MatchParameters;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author alfonso
 *
 */
public class TournamentView extends ViewWithUiHandlers<TournamentUiHandlers> implements TournamentPresenter.MyView {

	interface Binder extends UiBinder<Widget, TournamentView> {
	}

	@UiField
	Container journeyContainer;
	@UiField
	Container clasificationContainer;
	@UiField
	Container finalMatchContainer;

	@Inject
	TournamentView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void initTableClasification(final AbstractCellTable<ClasificationDto> grid, final SimplePager pager,
			final Pagination pagination, final ListDataProvider<ClasificationDto> dataProvider) {
		final TextColumn<ClasificationDto> numColumn = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(ClasificationDto object) {
				return String.valueOf(object.getPosition());
			}

		};
		numColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		numColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(numColumn);

		final Column<ClasificationDto, String> imageColumn = new Column<ClasificationDto, String>(new ImageCell()) {

			@Override
			public String getValue(ClasificationDto object) {
				if (object.getTeam().isLogo()) {
					return UploadParameters.getBASE_URL() + "/serveTeamImageServlet?id=" + object.getTeam().getId()
							+ "&min=OK&" + System.currentTimeMillis();
				} else {
					return UploadParameters.getBASE_URL() + "/images/sin_escudo_min.png";
				}
			}
		};
		imageColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		imageColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(imageColumn);

		final TextColumn<ClasificationDto> col1 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getTeam().getTeamName());
			}
		};
		col1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol1 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">Equipo</p></strong>";
			}
		});
		grid.addColumn(col1, headerCol1);

		final TextColumn<ClasificationDto> col2 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getPoints());
			}
		};
		col2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol2 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">Puntos</p></strong>";
			}
		});
		grid.addColumn(col2, headerCol2);

		final TextColumn<ClasificationDto> col3 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getMatchs());
			}
		};
		col3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol3 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">PJ</p></strong>";
			}
		});
		grid.addColumn(col3, headerCol3);

		final TextColumn<ClasificationDto> col4 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getWins());
			}
		};
		col4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col4.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol4 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">PG</p></strong>";
			}
		});
		grid.addColumn(col4, headerCol4);

		final TextColumn<ClasificationDto> col5 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getTied());
			}
		};
		col5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol5 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">PE</p></strong>";
			}
		});
		grid.addColumn(col5, headerCol5);

		final TextColumn<ClasificationDto> col6 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getLost());
			}
		};
		col6.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col6.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol6 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">PP</p></strong>";
			}
		});
		grid.addColumn(col6, headerCol6);

		final TextColumn<ClasificationDto> col7 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getMyGoals());
			}
		};
		col7.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col7.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol7 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">GF</p></strong>";
			}
		});
		grid.addColumn(col7, headerCol7);

		final TextColumn<ClasificationDto> col8 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getGoalsAgainst());
			}
		};
		col8.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col8.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		SafeHtmlHeader headerCol8 = new SafeHtmlHeader(new SafeHtml() {
			private static final long serialVersionUID = 1L;

			@Override
			public String asString() {
				return "<strong><p style=\"text-align:center;\">GC</p></strong>";
			}
		});
		grid.addColumn(col8, headerCol8);

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
	public Container getJourneyContainer() {
		return journeyContainer;
	}

	@Override
	public void viewJourney(List<JourneyDto> listJourneyDto, Date serverDate) {
		Container container = new Container();
		container.setFluid(true);
		Row row = new Row();
		int count = 0;
		int journey = 0;
		for (JourneyDto journeyDto : listJourneyDto) {
			count++;
			journey++;
			org.gwtbootstrap3.client.ui.Column column = new org.gwtbootstrap3.client.ui.Column(ColumnSize.XS_6,
					ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_6);
			Panel panel = new Panel();
			panel.setType(PanelType.INFO);
			PanelHeader panelHeader = new PanelHeader();
			panelHeader.setText("Jornada " + journey + " :: "
					+ DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(journeyDto.getDate()));
			panel.add(panelHeader);
			PanelBody panelBody = new PanelBody();
			CellTable<MatchDto> cellTableJourney = new CellTable<MatchDto>(journeyDto.getMatchs().size());
			ListDataProvider<MatchDto> listMatchDto = new ListDataProvider<MatchDto>();
			listMatchDto.setList(journeyDto.getMatchs());
			Pagination pagination = new Pagination();
			SimplePager simplePager = new SimplePager();
			initJourneyTable(cellTableJourney, simplePager, pagination, listMatchDto, serverDate);
			panelBody.add(cellTableJourney);
			panel.add(panelBody);
			column.add(panel);
			row.add(column);
			container.add(row);
			if (count == 2) {
				journeyContainer.add(row);
				row = new Row();
				count = 0;
			}
		}
	}

	private void initJourneyTable(final AbstractCellTable<MatchDto> grid, final SimplePager pager,
			final Pagination pagination, final ListDataProvider<MatchDto> dataProvider, final Date serverDate) {
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

		final Column<MatchDto, String> imageColumn = new Column<MatchDto, String>(new ImageCell()) {

			@Override
			public String getValue(MatchDto object) {
				if (object.getUserHome().isLogo()) {
					return UploadParameters.getBASE_URL() + "/serveTeamImageServlet?id=" + object.getUserHome().getId()
							+ "&min=OK&" + System.currentTimeMillis();
				} else {
					return UploadParameters.getBASE_URL() + "/images/sin_escudo_min.png";
				}
			}
		};
		imageColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		imageColumn.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(imageColumn);

		final Column<MatchDto, SafeHtml> col2 = new Column<MatchDto, SafeHtml>(new ResultMatchCell()) {
			@Override
			public SafeHtml getValue(MatchDto object) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				if (object.getMatchPropertiesDto() != null) {
					long d = (serverDate.getTime() - object.getDate().getTime()) / (1000 * 60);
					if (d < 60) {
						sb.appendHtmlConstant("<a href=\"javascript:;\">");
						sb.appendEscaped("Pulsa para ver el resultado");
						sb.appendHtmlConstant("</a>");
					} else {
						sb.appendHtmlConstant("<div>" + object.getMatchPropertiesDto().getGoalsHome() + " - "
								+ object.getMatchPropertiesDto().getGoalsAway() + "</div>");
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
				Notify.notify("",
						object.getUserHome().getTeamName() + " " + object.getMatchPropertiesDto().getGoalsHome() + " - "
								+ object.getMatchPropertiesDto().getGoalsAway() + " "
								+ object.getUserAway().getTeamName(),
						IconType.CALENDAR, settings);
			}
		});
		col2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(col2);

		final Column<MatchDto, String> imageColumn2 = new Column<MatchDto, String>(new ImageCell()) {

			@Override
			public String getValue(MatchDto object) {
				if (object.getUserAway().isLogo()) {
					return UploadParameters.getBASE_URL() + "/serveTeamImageServlet?id=" + object.getUserAway().getId()
							+ "&min=OK&" + System.currentTimeMillis();
				} else {
					return UploadParameters.getBASE_URL() + "/images/sin_escudo_min.png";
				}
			}
		};
		imageColumn2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
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
							UploadParameters.getBASE_URL() + "/serveMatchServlet?id=" + Long.toString(object.getId()),
							"_blank", "");
				} else {
					NotifySettings settings = NotifySettings.newSettings();
					settings.setType(NotifyType.INFO);
					settings.setPlacement(NotifyPlacement.TOP_CENTER);
					settings.setAllowDismiss(true);
					Notify.notify("", "Partido no disponible", IconType.CALENDAR, settings);
				}
			}
		});
		col1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(col4);

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
							UploadParameters.getBASE_URL() + "/visorwebgl/play.html?" + Long.toString(object.getId()),
							"_blank", "");
				} else {
					NotifySettings settings = NotifySettings.newSettings();
					settings.setType(NotifyType.INFO);
					settings.setPlacement(NotifyPlacement.TOP_CENTER);
					settings.setAllowDismiss(true);
					Notify.notify("", "Partido no disponible", IconType.CALENDAR, settings);
				}
			}
		});
		col5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		col5.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		grid.addColumn(col5);

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
	public void viewClasification(List<ClasificationDto> listClasificationDto) {
		Row row = new Row();
		org.gwtbootstrap3.client.ui.Column column = new org.gwtbootstrap3.client.ui.Column(ColumnSize.XS_12,
				ColumnSize.SM_12, ColumnSize.MD_12, ColumnSize.LG_12);
		Panel panel = new Panel();
		panel.setType(PanelType.INFO);
		PanelHeader panelHeader = new PanelHeader();
		panelHeader.setText("Clasificación");
		panel.add(panelHeader);
		PanelBody panelBody = new PanelBody();
		CellTable<ClasificationDto> cellTableClasification = new CellTable<ClasificationDto>(
				listClasificationDto.size());
		ListDataProvider<ClasificationDto> listClasificationDataDto = new ListDataProvider<ClasificationDto>();
		listClasificationDataDto.setList(listClasificationDto);
		Pagination pagination = new Pagination();
		SimplePager simplePager = new SimplePager();
		initTableClasification(cellTableClasification, simplePager, pagination, listClasificationDataDto);
		panelBody.add(cellTableClasification);
		panel.add(panelBody);
		column.add(panel);
		row.add(column);
		clasificationContainer.add(row);
	}

	@Override
	public Container getClasificationContainer() {
		return clasificationContainer;
	}

	@Override
	public Container getFinalMatchContainer() {
		return finalMatchContainer;
	}

	@Override
	public void viewFinalMatch(List<FinalMatchDto> listFinalMatchDto) {
		FinalMatchDto finalMatchDto = getFinalMatch(listFinalMatchDto, FinalMatchType.OCTAVOS, 0);

		Row row = new Row();
		org.gwtbootstrap3.client.ui.Column column = new org.gwtbootstrap3.client.ui.Column(ColumnSize.XS_1,
				ColumnSize.SM_1, ColumnSize.MD_1, ColumnSize.LG_1);

		Image image = new Image();
		if (finalMatchDto.getMatchDto().getUserHome().isLogo()) {
			image.setUrl(UploadParameters.getBASE_URL() + "/serveTeamImageServlet?id="
					+ finalMatchDto.getMatchDto().getUserHome().getId() + "&min=OK&" + System.currentTimeMillis());
		} else {
			image.setUrl(UploadParameters.getBASE_URL() + "/images/sin_escudo_min.png?" + System.currentTimeMillis());
		}
		column.add(image);
		row.add(column);
		
		org.gwtbootstrap3.client.ui.Column column2 = new org.gwtbootstrap3.client.ui.Column(ColumnSize.XS_1,
				ColumnSize.SM_1, ColumnSize.MD_1, ColumnSize.LG_1);
		Paragraph paragraph = new Paragraph();
		paragraph.setText(finalMatchDto.getMatchDto().getUserHome().getTeamName());
		column2.add(paragraph);
		row.add(column2);

		finalMatchContainer.add(row);
		
		Row row2 = new Row();
		finalMatchContainer.add(row2);
		
		Row row3 = new Row();
		org.gwtbootstrap3.client.ui.Column column3 = new org.gwtbootstrap3.client.ui.Column(ColumnSize.XS_1,
				ColumnSize.SM_1, ColumnSize.MD_1, ColumnSize.LG_1);


		Image image2 = new Image();
		if (finalMatchDto.getMatchDto().getUserAway().isLogo()) {
			image2.setUrl(UploadParameters.getBASE_URL() + "/serveTeamImageServlet?id="
					+ finalMatchDto.getMatchDto().getUserAway().getId() + "&min=OK&" + System.currentTimeMillis());
		} else {
			image2.setUrl(UploadParameters.getBASE_URL() + "/images/sin_escudo_min.png?" + System.currentTimeMillis());
		}
		column3.add(image2);
		row3.add(column3);
		
		org.gwtbootstrap3.client.ui.Column column4 = new org.gwtbootstrap3.client.ui.Column(ColumnSize.XS_1,
				ColumnSize.SM_1, ColumnSize.MD_1, ColumnSize.LG_1);
		Paragraph paragraph2 = new Paragraph();
		paragraph2.setText(finalMatchDto.getMatchDto().getUserAway().getTeamName());
		column4.add(paragraph2);
		row3.add(column4);
		
		finalMatchContainer.add(row3);
	}

	private FinalMatchDto getFinalMatch(List<FinalMatchDto> listFinalMatchDto, FinalMatchType finalMatchType,
			Integer order) {
		for (FinalMatchDto finalMatchDto : listFinalMatchDto) {
			if ((finalMatchDto.getFinalMatchType() == finalMatchType) && (finalMatchDto.getOrder() == order)) {
				return finalMatchDto;
			}
		}

		return null;
	}
}
