/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.CellTable;
import org.javahispano.javaleague.shared.dto.ClasificationDto;
import org.javahispano.javaleague.shared.dto.MatchDto;
import org.javahispano.javaleague.shared.parameters.UploadParameters;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RangeChangeEvent;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author alfonso
 *
 */
public class TournamentView extends ViewWithUiHandlers<TournamentUiHandlers>
		implements TournamentPresenter.MyView {

	interface Binder extends UiBinder<Widget, TournamentView> {
	}

	@UiField(provided = true)
	CellTable<MatchDto> cellTable = new CellTable<MatchDto>(2);
	@UiField
	Pagination cellTablePagination;

	private SimplePager cellTablePager = new SimplePager();
	private ListDataProvider<MatchDto> cellTableProvider = new ListDataProvider<MatchDto>();

	@UiField(provided = true)
	CellTable<ClasificationDto> cellTableClasification = new CellTable<ClasificationDto>(
			4);
	@UiField
	Pagination cellTableClasificationPagination;

	private SimplePager cellTableClasificationPager = new SimplePager();
	private ListDataProvider<ClasificationDto> cellTableClasificationProvider = new ListDataProvider<ClasificationDto>();

	@Inject
	TournamentView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		initTable(cellTable, cellTablePager, cellTablePagination,
				cellTableProvider);
		initTableClasification(cellTableClasification,
				cellTableClasificationPager, cellTableClasificationPagination,
				cellTableClasificationProvider);
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

	private void initTableClasification(
			final AbstractCellTable<ClasificationDto> grid,
			final SimplePager pager, final Pagination pagination,
			final ListDataProvider<ClasificationDto> dataProvider) {
		final TextColumn<ClasificationDto> col1 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getTeam().getTeamName());
			}
		};
		grid.addColumn(col1, "Equipo");

		final TextColumn<ClasificationDto> col2 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getPoints());
			}
		};
		grid.addColumn(col2, "Puntos");

		final TextColumn<ClasificationDto> col3 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getMyGoals());
			}
		};
		grid.addColumn(col3, "Goles a favor");

		final TextColumn<ClasificationDto> col4 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getGoalsAgainst());
			}
		};
		grid.addColumn(col4, "Goles en contra");

		final TextColumn<ClasificationDto> col5 = new TextColumn<ClasificationDto>() {

			@Override
			public String getValue(final ClasificationDto object) {
				return String.valueOf(object.getMyGoals()
						- object.getGoalsAgainst());
			}
		};
		grid.addColumn(col5, "Diferencia de goles");

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
	public ListDataProvider<ClasificationDto> getListClasification() {
		return cellTableClasificationProvider;
	}

	@Override
	public SimplePager getClasificationPager() {
		return cellTableClasificationPager;
	}

	@Override
	public Pagination getClasificationPagination() {
		return cellTableClasificationPagination;
	}
}
