package ru.iskandar.holiday.calculator.ui.search;

import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import ru.iskandar.holiday.calculator.dataconnection.ISearchHit;

/**
 * Поставщик текста таблицы заявлений
 */
public class SearchResultTableLabelProvider implements ITableLabelProvider {

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

	@Override
	public Image getColumnImage(Object aElement, int aColumnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object aElement, int aColumnIndex) {
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

}
