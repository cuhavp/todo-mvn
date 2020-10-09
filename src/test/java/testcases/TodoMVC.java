package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Todos;
import supports.Browser;

import java.util.UUID;

public class TodoMVC {
    private Browser browser;
    private Todos todoMvc;

    @BeforeMethod
    void beforeEach() {
        browser = Browser.newBrowser();
        todoMvc = new Todos(browser);
        todoMvc.navigateTo();
    }

    @AfterMethod
    void afterEach() {
        browser.quit();
    }

    @Test(description = "Creates Todo with given name")
    void createsTodo() {
        String todoName = randomTodoName();
        todoMvc.createTodo(todoName);
        Assert.assertEquals(1, todoMvc.getTodosLeft());
        Assert.assertTrue(todoMvc.todoExists(todoName));
    }

    @Test(description = "Edits inline double-clicked Todo")
    void editsTodo() {
        String todoName = randomTodoName();
        String newTodoName = randomTodoName();
        todoMvc.createTodo(todoName);

        todoMvc.renameTodo(todoName, newTodoName);
        Assert.assertTrue(todoMvc.todoExists(newTodoName));
    }

    @Test(description = "Removes selected Todo")
    void removesTodo() {
        String todoName = randomTodoName();
        todoMvc.createTodo(todoName);

        todoMvc.removeTodo(todoName);
        Assert.assertFalse(todoMvc.todoExists(todoName));
    }

    @Test(description = "Toggles selected Todo as completed")
    void togglesTodoCompleted() {
        String todoName = randomTodoName();
        todoMvc.createTodos(todoName, randomTodoName());

        todoMvc.completeTodo(todoName);
        Assert.assertEquals(1, todoMvc.getTodosLeft());

        todoMvc.showCompleted();
        Assert.assertEquals(1, todoMvc.getTodoCount());

        todoMvc.showActive();
        Assert.assertEquals(1, todoMvc.getTodoCount());
    }

    @Test(description = "Toggles all Todos as completed")
    void togglesAllTodosCompleted() {
        todoMvc.createTodos(randomTodoName(), randomTodoName());

        todoMvc.completeAllTodos();
        Assert.assertEquals(0, todoMvc.getTodosLeft());

        todoMvc.showCompleted();
        Assert.assertEquals(2, todoMvc.getTodoCount());

        todoMvc.showActive();
        Assert.assertEquals(0, todoMvc.getTodoCount());
    }

    @Test(description = "Clears all completed Todos")
    void clearsCompletedTodos() {
        todoMvc.createTodos(randomTodoName(), randomTodoName());
        todoMvc.completeAllTodos();
        todoMvc.createTodo(randomTodoName());

        todoMvc.clearCompleted();
        Assert.assertEquals(1, todoMvc.getTodosLeft());

        todoMvc.showCompleted();
        Assert.assertEquals(0, todoMvc.getTodoCount());

        todoMvc.showActive();
        Assert.assertEquals(1, todoMvc.getTodoCount());
    }

    @Test(description = "Creates Todos all with the same name")
    void createsTodosWithSameName() {
        String todoName = randomTodoName();
        todoMvc.createTodos(todoName, todoName, todoName);
        Assert.assertEquals(3, todoMvc.getTodosLeft());

        todoMvc.showActive();
        Assert.assertEquals(3, todoMvc.getTodoCount());
    }

    private String randomTodoName() {
        return "My Todo " + UUID.randomUUID().toString();
    }

}
