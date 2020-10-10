package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import supports.Browser;

import java.util.List;
import java.util.stream.Collectors;

public class Todos {
    private Browser browser;
    private final String BASE_URL="http://todomvc.com/examples/vanillajs";
    private final By newTodoTxt = By.className("new-todo");
    private final By todosLeftLbl = By.cssSelector(".todo-count > strong");
    private final By editTodoBtn = By.cssSelector("input.edit");
    private final By removeTodoBtn = By.cssSelector("button.destroy");
    private final By completeTodoBtn = By.cssSelector("input.toggle");
    private final By markCompleteAllBtn = By.className("toggle-all");
    private final By activeTab  =By.cssSelector("a[href='#/active']");
    private final By completedTab  =By.cssSelector("a[href='#/completed']");
    private final By clearCompletedBtn = By.className("clear-completed");
    private final By todosLbl = By.cssSelector(".todo-list li");


    public Todos(Browser browser) {
        this.browser = browser;
    }

    public void navigateTo() {
        browser.open(BASE_URL);
    }

    public void createTodo(String todoName) {
        browser.type(newTodoTxt, todoName + Keys.ENTER);
    }

    public void createTodos(String... todoNames) {
        for (String todoName : todoNames) {
            createTodo(todoName);
        }
    }

    public int getTodosLeft() {
        return Integer.parseInt(browser.find(todosLeftLbl).getText());
    }

    public boolean todoExists(String todoName) {
        return getTodos().stream().anyMatch(todoName::equals);
    }

    public int getTodoCount() {
        return getTodoElements().size();
    }

    public List<String> getTodos() {
        return getTodoElements()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void renameTodo(String todoName, String newTodoName) {
        WebElement todoToEdit = getTodoElementByName(todoName);
        browser.doubleClick(todoToEdit);

        WebElement todoEditInput = browser.find(editTodoBtn, todoToEdit);
        browser.executeScript("arguments[0].value = ''", todoEditInput);

        browser.type(todoEditInput, newTodoName + Keys.ENTER);
    }

    public void removeTodo(String todoName) {
        WebElement todoToRemove = getTodoElementByName(todoName);
        browser.moveToElement(todoToRemove);
        browser.click(removeTodoBtn, todoToRemove);
    }

    public void completeTodo(String todoName) {
        WebElement todoToComplete = getTodoElementByName(todoName);
        browser.click(completeTodoBtn, todoToComplete);
    }

    public void completeAllTodos() {
        browser.click(markCompleteAllBtn);
    }

    public void showActive() {
        browser.click(activeTab);
    }

    public void showCompleted() {
        browser.click(completedTab);
    }

    public void clearCompleted() {
        browser.click(clearCompletedBtn);
    }

    private List<WebElement> getTodoElements() {
        return browser.findAll(todosLbl);
    }

    private WebElement getTodoElementByName(String todoName) {
        return getTodoElements()
                .stream()
                .filter(el -> todoName.equals(el.getText()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test data missing!"));
    }

}
