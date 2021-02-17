package com.diplomna.exceptions;

public class AssetNotFoundException extends Exception{
    /*
        Asset not found!
     */
    public AssetNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
