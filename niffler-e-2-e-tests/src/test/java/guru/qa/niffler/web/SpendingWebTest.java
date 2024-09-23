package guru.qa.niffler.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.extentions.BrowserExtension;
import guru.qa.niffler.extentions.Spending;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class SpendingWebTest {

  private static final Config CFG = Config.getInstance();

  @Spending(
      username = "duck",
      category = "Обучение",
      description = "Обучение Advanced 2.0",
      amount = 79990
  )
  @Test
  void categoryDescriptionShouldBeChangedFromTable(SpendJson spend) {
    final String newDescription = "Обучение Niffler Next Generation";

    Selenide.open(CFG.frontUrl(), LoginPage.class)
        .successLogin("duck", "12345678")
        .editSpending(spend.description())
        .setNewSpendingDescription(newDescription)
        .save();

    new MainPage().checkThatTableContainsSpending(newDescription);
  }
}

