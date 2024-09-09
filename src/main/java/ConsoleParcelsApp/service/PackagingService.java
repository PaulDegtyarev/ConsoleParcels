package ConsoleParcelsApp.service;

import java.io.IOException;

public interface PackagingService {


    void packPackages(String filePath) throws IOException;

    void printResults();
}
