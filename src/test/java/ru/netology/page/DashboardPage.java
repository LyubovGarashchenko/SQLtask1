package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.TypeOptions.text;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    public DashboardPage() {
        heading.shouldHave(Condition.text("  Личный кабинет")).shouldBe(visible);
    }

}
