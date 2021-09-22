package input.mouse;

import state.State;

import java.sql.SQLException;

public interface MouseConsumer {

    void onClick(State state) throws SQLException;
    void onDrag(State state);
}
