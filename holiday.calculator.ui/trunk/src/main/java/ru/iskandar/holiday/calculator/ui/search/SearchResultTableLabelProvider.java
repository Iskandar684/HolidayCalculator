package ru.iskandar.holiday.calculator.ui.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.TextStyle;

import ru.iskandar.holiday.calculator.service.model.search.ISearchHit;

/**
 * Поставщик текста таблицы заявлений
 */
public class SearchResultTableLabelProvider extends StyledCellLabelProvider implements IStyledLabelProvider {

	private final Supplier<String> _highlightedTextSupplier;

	/**
	 * Конструктор
	 */
	public SearchResultTableLabelProvider(Supplier<String> aHghlightedTextSupplier) {
		_highlightedTextSupplier = Objects.requireNonNull(aHghlightedTextSupplier);
	}

	@Override
	public void addListener(ILabelProviderListener aListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object aElement, String aProperty) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener aListener) {
	}

	private String getColumnText(Object aElement) {
		if (aElement instanceof ISearchHit) {
			return getSearchHitText((ISearchHit) aElement);
		}
		return aElement.toString();
	}

	private String getSearchHitText(ISearchHit aSearchHit) {
		String text = aSearchHit.getSourceAsMap().toString();
		if (text == null) {
			return Util.ZERO_LENGTH_STRING;
		}
		return text += System.lineSeparator();
	}

	@Override
	public StyledString getStyledText(Object aElement) {
		String text = getColumnText(aElement);
		StyledString styledString = new StyledString(text);
		Styler matchStyler = new Styler() {

			@Override
			public void applyStyles(TextStyle aTextStyle) {
				aTextStyle.font = JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT);
			}

		};
		styledString.setStyle(0, text.length(), new Styler() {

			@Override
			public void applyStyles(TextStyle aTextStyle) {
			}
		});

		for (Point interval : getMatchIntervals(text)) {
			styledString.setStyle(interval.x, interval.y, matchStyler);
		}
		return styledString;
	}

	private List<Point> getMatchIntervals(String aText) {
		String highlightedText = _highlightedTextSupplier.get();
		if ((highlightedText == null) || highlightedText.isEmpty()) {
			return Collections.emptyList();
		}
		List<Point> intervals = new ArrayList<>();
		String space = " ";
		String[] parts = highlightedText.contains(space) ? highlightedText.split(space)
				: new String[] { highlightedText };
		for (String part : parts) {
			int length = part.length();
			int startIndex = 0;
			while (startIndex != -1) {
				startIndex = aText.indexOf(part, startIndex + 1);
				if (startIndex > 0) {
					intervals.add(new Point(startIndex, length));
				}
			}
		}
		return intervals;
	}

	@Override
	public void update(ViewerCell aCell) {
		Object element = aCell.getElement();
		StyledString styledText = getStyledText(element);
		aCell.setText(appendSeparator(styledText.getString()));
		aCell.setStyleRanges(styledText.getStyleRanges());
		super.update(aCell);
	}

	private String appendSeparator(String aText) {
		StringBuilder builder = new StringBuilder(aText);
		builder.append(System.lineSeparator());
		builder.append("____________________________________________________________________________________");
		return builder.toString();
	}

	@Override
	public Image getImage(Object aElement) {
		// TODO Auto-generated method stub
		return null;
	}

}
