package ru.iskandar.holiday.calculator.ui.user;

import java.util.UUID;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.service.model.search.ISearchHit;
import ru.iskandar.holiday.calculator.service.model.search.SearchConstants;
import ru.iskandar.holiday.calculator.service.model.user.UserId;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;
import ru.iskandar.holiday.calculator.ui.search.ISearchHitViewer;

/**
 * @author Искандар
 *
 */
public class UserSearchHitViewer implements ISearchHitViewer {

	@Override
	public boolean canView(ISearchHit aSearchHit) {
		return aSearchHit.getSourceAsMap().containsKey(SearchConstants.USER_ID_KEY);
	}

	@Override
	public void view(ISearchHit aSearchHit) {
		String userIdAsStr = (String) aSearchHit.getSourceAsMap().get(SearchConstants.USER_ID_KEY);
		UserId userId = UserId.from(UUID.fromString(userIdAsStr));
		ViewUsersEditorInput input = new ViewUsersEditorInput(ModelProviderHolder.getInstance().getModelProvider(),
				userId);
		try {
			UsersEditor editor = (UsersEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.openEditor(input, UsersEditor.EDITOR_ID, true, IWorkbenchPage.MATCH_ID);
			editor.setSelection(userId);
		} catch (PartInitException e) {
			throw new IllegalStateException("Ошибка открытия формы просмотра пользователей", e);
		}
	}

}
