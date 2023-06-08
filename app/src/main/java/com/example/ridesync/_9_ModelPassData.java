package com.example.ridesync;

import android.graphics.Bitmap;

import java.util.Date;

public class _9_ModelPassData {
    String _passDate;
    String _key;

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    int _passDays,_passPayment;
    String _passHoldername;
    String _passHolderEmail;
    String _busNumber;
    String Status;

    Bitmap qrcode;

    public Bitmap getQrcode() {
        return qrcode;
    }

    public void setQrcode(Bitmap qrcode) {
        this.qrcode = qrcode;
    }

    public String get_busNumber() {
        return _busNumber;
    }

    public void set_busNumber(String _busNumber) {
        this._busNumber = _busNumber;
    }


    public int getCurrentBalance() {
        return CurrentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        CurrentBalance = currentBalance;
    }

    int CurrentBalance;


    public _9_ModelPassData() {

    }

    public _9_ModelPassData(String _passDate, int _passDays, int _passPayment, String _passHoldername, String _passHolderEmail, String status) {
        this._passDate = _passDate;
        this._passDays = _passDays;
        this._passPayment = _passPayment;
        this._passHoldername = _passHoldername;
        this._passHolderEmail = _passHolderEmail;

        Status=status;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String get_passDate() {
        return _passDate;
    }

    public void set_passDate(String _passDate) {
        this._passDate = _passDate;
    }

    public int get_passDays() {
        return _passDays;
    }

    public void set_passDays(int _passDays) {
        this._passDays = _passDays;
    }

    public int get_passPayment() {
        return _passPayment;
    }

    public void set_passPayment(int _passPayment) {
        this._passPayment = _passPayment;
    }

    public String get_passHoldername() {
        return _passHoldername;
    }

    public void set_passHoldername(String _passHoldername) {
        this._passHoldername = _passHoldername;
    }

    public String get_passHolderEmail() {
        return _passHolderEmail;
    }

    public void set_passHolderEmail(String _passHolderEmail) {
        this._passHolderEmail = _passHolderEmail;
    }
}
