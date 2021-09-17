package UI.clickable;

import state.State;

import java.sql.SQLException;

public interface ClickAction {

    void execute(State state) throws SQLException;
}
