package ru.geekbrains.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.geekbrains.products.persist.ProductsRepository;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class CartService implements Runnable {

    private static final Set<Action> actions;
    private static final Set<String> actionNames;

    static {
        actions = createActions();
        actionNames = actions.stream()
                .map(action -> action.name)
                .collect(Collectors.toSet());
    }

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public void run() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ProductsConfiguration.class);
        Cart cart = context.getBean("cart", Cart.class);
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            String action = getAction(scanner);
            switch (action) {
                case "cart":
                    cart = context.getBean("cart", Cart.class);
                    break;
                case "add":
                    productsRepository.findAll().forEach(System.out::println);
                    cart.addProductById(getId(scanner));
                    break;
                case "del":
                    cart.getProducts().forEach(System.out::println);
                    cart.removeProductById(getId(scanner));
                    break;
                case "print":
                    cart.getProducts().forEach(System.out::println);
                    break;
                case "quit":
                    isRunning = false;
                    break;
                default:
                    break;
            }

            System.out.println("Enter");
        }
        scanner.close();
    }

    private long getId(Scanner scanner) {
        System.out.println();
        System.out.print("Enter id: ");
        long id;
        if (scanner.hasNextLong()) {
            id = scanner.nextLong();
        } else {
            id = Long.parseLong(scanner.nextLine());
        }
        return id;
    }

    private static Set<Action> createActions() {
        Set<Action> actions = new TreeSet<>();
        actions.add(new Action("cart", "Creates new cart"));
        actions.add(new Action("add", "Adds product into cart"));
        actions.add(new Action("del", "Deletes product from cart"));
        actions.add(new Action("print", "Prints contents of cart"));
        actions.add(new Action("quit", "Quits this application"));

        return actions;
    }

    private void printActions() {
        actions.forEach(action -> System.out.println(action.name + " - " + action.description));
    }

    private String getAction(Scanner scanner) {
        String action;
        do {
            printActions();

            System.out.println();
            System.out.print("> ");
            action = scanner.nextLine();
        } while (!actionNames.contains(action));
        return action;
    }

    private static class Action implements Comparable<Action> {
        public final String name;
        public final String description;

        public Action(String name, String description) {
            this.name = name;
            this.description = description;
        }

        @Override
        public int compareTo(Action other) {
            return this.name.compareTo(other.name);
        }
    }
}
