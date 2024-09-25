package ru.liga.consoleParcels.factory.impl;

import ru.liga.consoleParcels.factory.DelimeterFactory;

public class DelimeterFactoryImpl implements DelimeterFactory {
    @Override
    public int createDelimeter(String packageId) {
        return Integer.parseInt(packageId);
    }
}
