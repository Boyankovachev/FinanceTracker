package com.diplomna.exceptions;

public class AssetNotFoundException extends Exception{
    public AssetNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
