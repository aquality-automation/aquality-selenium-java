package aquality.selenium.elements;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ElementServices {

    private static final ThreadLocal<Injector> injectorContainer = new ThreadLocal<>();

    public static void injectDependencies(Injector injector) {
        if(injectorContainer.get() != null) {
            injectorContainer.remove();
        }
        injectorContainer.set(injector);
    }

    private static Injector getInjector() {
        if(injectorContainer.get() == null) {
            injectorContainer.set(Guice.createInjector(new ElementsModule()));
        }
        return injectorContainer.get();
    }

    static <T> T getInstance(Class<T> elementsService) {
        return getInjector().getInstance(elementsService);
    }
}
