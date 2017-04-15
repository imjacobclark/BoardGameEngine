package xyz.jacobclark.unit.games;

import org.junit.Test;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.models.Board;
import xyz.jacobclark.exceptions.FullGameException;
import xyz.jacobclark.games.impl.Gomoku;
import xyz.jacobclark.models.GameTitle;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.rules.impl.GomokuRules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class GomokuTest {
    @Test
    public void shouldReturn_gomoku_ForGameTitle() throws Exception {
        Gomoku gomoku = new Gomoku();
        assertThat(gomoku.getTitle(), is(GameTitle.GOMOKU));
    }

    @Test
    public void uponNewGomokuGameCreation_ANewBoard_WithAUniqueId_IsCreated() throws Exception {
        Gomoku gomoku = new Gomoku();

        assertTrue(gomoku.getUuid().toString().contains("-"));
    }

    @Test
    public void uponNewGomokuGameCreation_AFirstBlackPlayerIsCreated() throws Exception {
        Game game = new Gomoku();

        assertTrue(game.getPlayers().get(0).getUuid().toString().contains("-"));
        assertThat(game.getPlayers().get(0).getPebbleType(), is(PebbleType.BLACK));
    }

    @Test
    public void uponNewGomokuGameCreation_ANewBoard_WithGomokuRules_IsCreated() throws Exception {
        Game game = new Gomoku();
        assertThat(game.getBoard(), samePropertyValuesAs(new Board(new GomokuRules())));
    }

    @Test
    public void canAddASecond_WhitePlayerToGomokuGame() throws Exception, FullGameException {
        Game game = new Gomoku();

        Player player = game.addPlayer();

        assertThat(player.getPebbleType(), is(PebbleType.WHITE));
        assertTrue(player.getUuid().toString().contains("-"));

        assertThat(game.getPlayers().size(), is(2));
        assertThat(game.getPlayers().get(0).getPebbleType(), is(PebbleType.BLACK));
        assertThat(game.getPlayers().get(1).getPebbleType(), is(PebbleType.WHITE));
    }

    @Test(expected = FullGameException.class)
    public void canNotAddMoreThanTwoPlayersToGomokuGame() throws Exception, FullGameException {
        Game game = new Gomoku();
        game.addPlayer();
        game.addPlayer();
    }
}
