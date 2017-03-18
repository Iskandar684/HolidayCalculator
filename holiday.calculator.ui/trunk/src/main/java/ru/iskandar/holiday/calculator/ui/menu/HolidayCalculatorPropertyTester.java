package ru.iskandar.holiday.calculator.ui.menu;

import org.eclipse.core.expressions.PropertyTester;

import ru.iskandar.holiday.calculator.ui.HolidayCalculatorModelProvider;
import ru.iskandar.holiday.calculator.ui.ILoadingProvider.LoadStatus;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;

/**
 * Тестер для определения видимости пунктов меню
 */
public class HolidayCalculatorPropertyTester extends PropertyTester {

	/** Видимость пункта меню "Открыть входящие заявления" */
	private static final String PROPERTY_OPEN_INCOMING_MENUITEM_VISIBLE = "openIncomingIsVisible";

	/**
	 * Конструктор
	 */
	public HolidayCalculatorPropertyTester() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean test(Object aReceiver, String aProperty, Object[] aArgs, Object aExpectedValue) {
		if (aProperty == null) {
			return false;
		}
		switch (aProperty) {
		case PROPERTY_OPEN_INCOMING_MENUITEM_VISIBLE:
			HolidayCalculatorModelProvider provider = ModelProviderHolder.getInstance().getModelProvider();
			boolean visible = false;
			if (LoadStatus.LOADED.equals(provider.getLoadStatus())) {
				visible = provider.getModel().canConsider();
			}
			return visible;
		}
		return false;
	}

}
