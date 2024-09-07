package ConsoleParcelsApp.packer;

import ConsoleParcelsApp.model.Parcel;

import java.util.List;

public interface ParcelPacker {
    void packPackages(List<Parcel> packages);

    void printResults();
}
