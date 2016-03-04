/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author alfonso
 *
 */
public interface TacticUiHandlers extends UiHandlers {
	void updateTeamNameTactic(String teamName);
	void playGame();
	void showUploadImageModal();
	void refreshGame();
}
