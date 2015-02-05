package com.kiroule.ocpupgradejava8.topic7_2;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
public class DefaultMethodsExample {

    public static void main(String... args) {

        Greeting defaultGreeting = new Greeting() {
            @Override
            public String getGreeting(String name) {
                return format(getTemplate(), name);
            }
        };
        System.out.println("Default greeting: " + defaultGreeting.getGreeting("Igor"));

        UpperCaseGreeting upperCaseGreeting = new UpperCaseGreeting() {
            @Override
            public String getGreeting(String name) {
                return format(getTemplate(), name);
            }
            @Override
            public String getGreetingInUpperCase(String name) {
                return getGreeting(name).toUpperCase();
            }
        };
        System.out.println("\nUpper case greeting: " + upperCaseGreeting.getGreetingInUpperCase("Igor"));

        Greeting abstractGreeting =  new AbstractGreeting() {
            @Override
            public String getTemplate() {
                return "Abstract hello, %s!";
            }

            @Override
            public String getGreeting(String name) {
                return format(getTemplate(), name);
            }
        };
        System.out.println("\nAbstract greeting: " + abstractGreeting.getGreeting("Igor"));

        Greeting overriddenGreeting = new OverriddenGreeting() {
            @Override
            public String getGreeting(String name) {
                return format(getTemplate(), name);
            }
        };
        System.out.println("\nOverridden greeting: " + overriddenGreeting.getGreeting("Igor"));
    }
}

interface Greeting {
    String getGreeting(String name);

    // default method in question
    default String getTemplate() {
        return "Hello, %s!";
    }
}
// Inheritance rules when extending interfaces that contain default methods:

// 1) Not mention the default method at all, which lets your extended interface inherit the default method.
interface UpperCaseGreeting extends Greeting {
    String getGreetingInUpperCase(String name);
}

// 2) Redeclare the default method, which makes it abstract.
interface AbstractGreeting extends Greeting {
    String getTemplate();
}

// 3) Redefine the default method, which overrides it.
interface OverriddenGreeting extends Greeting {
    @Override
    default String getTemplate() {
        return "Overridden hello to you, %s!";
    }
}
