package ru.iskandar.holiday.calculator.ui.statement;

import java.util.UUID;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import ru.iskandar.holiday.calculator.service.model.search.ISearchHit;
import ru.iskandar.holiday.calculator.service.model.search.SearchConstants;
import ru.iskandar.holiday.calculator.service.model.statement.StatementId;
import ru.iskandar.holiday.calculator.ui.ModelProviderHolder;
import ru.iskandar.holiday.calculator.ui.outgoing.OutgoingStatementsEditor;
import ru.iskandar.holiday.calculator.ui.outgoing.OutgoingStatementsEditorInput;
import ru.iskandar.holiday.calculator.ui.search.ISearchHitViewer;

/**
 * @author Искандар
 *
 */
public class StatementSearchHitViewer implements ISearchHitViewer {

	@Override
	public boolean canView(ISearchHit aSearchHit) {
		return aSearchHit.getSourceAsMap().containsKey(SearchConstants.STATEMENT_ID_KEY);
	}

	@Override
	public void view(ISearchHit aSearchHit) {
		String statementIdStr = (String) aSearchHit.getSourceAsMap().get(SearchConstants.STATEMENT_ID_KEY);
		StatementId statementId = StatementId.from(UUID.fromString(statementIdStr));
		OutgoingStatementsEditorInput editorInput = new OutgoingStatementsEditorInput(
				ModelProviderHolder.getInstance().getModelProvider());
		try {
			OutgoingStatementsEditor editor = (OutgoingStatementsEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.openEditor(editorInput, OutgoingStatementsEditor.EDITOR_ID, true, IWorkbenchPage.MATCH_ID);
			editor.showStatement(statementId);
		} catch (PartInitException e) {
			throw new RuntimeException("Ошибка открытия формы заявлений текущего пользователя", e);
		}
	}

}
