package ru.liga.parcels.factory.impl;

import ru.liga.parcels.factory.DelimeterFactory;

public class DelimeterFactoryImpl implements DelimeterFactory {
    @Override
    public int createDelimeter(String packageId) {
        return Integer.parseInt(packageId);
    }
}
