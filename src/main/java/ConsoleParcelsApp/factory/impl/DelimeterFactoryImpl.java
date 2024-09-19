package ConsoleParcelsApp.factory.impl;

import ConsoleParcelsApp.factory.DelimeterFactory;

public class DelimeterFactoryImpl implements DelimeterFactory {
    @Override
    public int createDelimeter(String packageId) {
        return Integer.parseInt(packageId);
    }
}
