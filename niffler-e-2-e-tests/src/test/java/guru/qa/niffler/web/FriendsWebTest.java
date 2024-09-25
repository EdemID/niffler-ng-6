package guru.qa.niffler.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.extensions.BrowserExtension;
import guru.qa.niffler.extensions.UsersQueueExtension;
import guru.qa.niffler.extensions.UsersQueueExtension.StaticUser;
import guru.qa.niffler.extensions.UsersQueueExtension.UserType;
import guru.qa.niffler.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.extensions.UsersQueueExtension.UserType.Type.EMPTY;
import static guru.qa.niffler.extensions.UsersQueueExtension.UserType.Type.WITH_FRIEND;
import static guru.qa.niffler.extensions.UsersQueueExtension.UserType.Type.WITH_INCOME_REQUEST;
import static guru.qa.niffler.extensions.UsersQueueExtension.UserType.Type.WITH_OUTCOME_REQUEST;

@ExtendWith(BrowserExtension.class)
public class FriendsWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin(user.username(), user.password())
                .checkThatPageLoaded()
                .friendsPage()
                .checkExistingFriends(user.friend());
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin(user.username(), user.password())
                .checkThatPageLoaded()
                .friendsPage()
                .checkNoExistingFriends();
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin(user.username(), user.password())
                .checkThatPageLoaded()
                .friendsPage()
                .checkExistingInvitations(user.income());
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin(user.username(), user.password())
                .checkThatPageLoaded()
                .allPeoplesPage()
                .checkInvitationSentToUser(user.outcome());
    }
}
