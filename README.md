# SSLCommerz android SDK

[![API 14+](https://img.shields.io/badge/API-14+-green.svg)](https://developer.android.com/about/dashboards/index.html#Platform)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.sslwireless-mobile/sslcommerz-android.svg)](https://search.maven.org/artifact/io.github.ParklyInc/sslcommerz-android)
[![JitPack](https://jitpack.io/v/sslwireless-mobile/sslcommerz-android.svg)](https://jitpack.io/#sslwireless-mobile/sslcommerz-android)
[![Code Climate Rating](https://codeclimate.com/github/sslwireless-mobile/sslcommerz-android/badges/gpa.svg)](https://codeclimate.com/github/sslwireless-mobile/sslcommerz-android)
[![Github CI](https://github.com/sslwireless-mobile/sslcommerz-android/actions/workflows/build.yml/badge.svg)](https://github.com/sslwireless-mobile/sslcommerz-android/actions/workflows/build.yml)
[![License](https://img.shields.io/github/license/sslwireless-mobile/sslcommerz-android.svg)](https://github.com/sslwireless-mobile/sslcommerz-android#license)



![Sslcommerz logo](https://www.sslcommerz.com/wp-content/uploads/2019/11/footer_logo.png)


<img width="40%" align="right" src="https://github.com/sslwireless-mobile/sslcommerz-android/blob/master/art/1.jpg"/>



|[API](https://github.com/sslcommerz-android)|[Wiki](https://github.com/sslwireless-mobile/sslcommerz-android/wiki)|[Releases](https://github.com/sslwireless-mobile/sslcommerz-android/releases)|[Screenshots](https://github.com/sslwireless-mobile/sslcommerz-android/wiki/Showcase)|[Demo APK](https://github.com/sslwireless-mobile/sslcommerz-android/releases/download/v3.4/testApp.apk)|
|-|-|-|-|-|



Official Documentation: 

Documents: [Click here](https://drive.google.com/file/d/1xH3u-Aijy-B19cIvpQQmazqZp91Tm6K1/view)

Library: [Click here](https://drive.google.com/file/d/1NRY3D5dDcY6RaXragvhMMywniKFAd_fi/view)

### Features

* One Gateway for all your payment needs
* The Easiest Integration Ever
* Simple way to integrate payments on web and mobile
* Material design
* Responsive, works everywhere
* Developer Friendly API Documentation
* Accept International Payments


## Usage

Check the [release page](https://github.com/sslwireless-mobile/sslcommerz-android/releases) for the latest and older versions:

In your module level ``build.gradle`` when using [mavenCentral](https://search.maven.org/artifact/io.github.sslwireless-mobile/sslcommerz-android):
```groovy
dependencies {
    implementation 'com.sslcommerz:sslcommerz-sdk:1.0.+'
}
```
or if using [JitPack](https://jitpack.io/#sslwireless-mobile/sslcommerz-android):
```groovy
dependencies {
    implementation 'com.sslcommerz:sslcommerz-sdk:v1.0.0'
}
```

### Building dialogs

Building library is very easy and short handed:

```java
sslCommerzInitialization = new SSLCommerzInitialization(storeID,
                    storePass, amount, SSLCCurrencyType.BDT,
                    "123456789098765", "food", SSLCSdkType.TESTBOX)
                    .addLanguage(SSLCLanguage.English);
                    
sslcCustomerInfoInitializer = new SSLCCustomerInfoInitializer(customerName, email,
                    address, city, "", "", phoneNumber);

sslcProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.General("general", "A"));
sslcProductInitializer.addDiscountAmount(Double.parseDouble(productAmount));

sslcAdditionalInitializer = new SSLCAdditionalInitializer();
sslcAdditionalInitializer.setInvoice_id(invoiceID);
sslcAdditionalInitializer.setNo_offer(0);
                    
IntegrateSSLCommerz
                .getInstance(context)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(SSLCCustomerInfoInitializer)
                .addEMITransactionInitializer(sslcEMITransactionInitializer)
                .addShipmentInfoInitializer(sslcShipmentInfoInitializer)
                .addProductInitializer(SSLCProductInitializer)
                .addAdditionalInitializer(sslcAdditionalInitializer)
                .buildApiCall(this);
```

Check the [wiki pages](https://github.com/sslwireless-mobile/sslcommerz-android/wiki) for instructions and examples on how to build the different dialogs available.

### Receive Results
Supply a tag when showing the dialog and let the hosting Activity or Fragment implement the `SSLCTransactionResponseListener`.  
For details, please refere to the [wiki pages](https://github.com/sslwireless-mobile/sslcommerz-android/wiki/SimpleDialog#receiving-results).

```java
@Override
public void transactionSuccess(SSLCTransactionInfoModel transactionInfo) {
    	String vlue = "Transaction successful: Amount " + transactionInfo.getAmount()+" TK";
    	Toast.makeText(context, vlue, Toast.LENGTH_LONG).show();    
}

@Override
public void transactionFail(String message) {
	Toast.makeText(context, message, Toast.LENGTH_LONG).show();
}

@Override
public void closed(String message) {
	Toast.makeText(context, message, Toast.LENGTH_LONG).show();
}

```

## Extensions
Known extensions and projects using this SDK:
- [File/Folder picker](https://github.com/isabsent/FilePicker) (see [#30](https://github.com/sslwireless-mobile/sslcommerz-android/issues/30))

## License

Copyright 2022 SSLCOMMERZ Ltd ([github.com/sslwireless-mobile](https://github.com/sslwireless-mobile))

