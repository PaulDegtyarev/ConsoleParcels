package ConsoleParcelsApp;

import ConsoleParcelsApp.controller.PackagingController;

public class ConsoleParcelsApplication {
    private static PackagingController packagingController = new PackagingController();

    public static void main(String[] args) {
        packagingController.handleUserSelection();
    }
}
