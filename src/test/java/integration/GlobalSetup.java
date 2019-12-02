package integration;

import com.github.isenng.libraryintegrationtesting.Main;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalSetup implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(MainApplication.class);
    }

    static class MainApplication implements ExtensionContext.Store.CloseableResource {
        public MainApplication() {
            // setup
            Main.main(new String[0]);
        }

        @Override
        public void close() throws Throwable {
            // Your "after all tests" logic goes here
        }
    }
}