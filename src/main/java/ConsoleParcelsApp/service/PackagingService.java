package ConsoleParcelsApp.service;

import ConsoleParcelsApp.model.Parcel;

import java.util.List;

public interface PackagingService {
    void packPackages(List<Parcel> packages);

    void printResults();
}
