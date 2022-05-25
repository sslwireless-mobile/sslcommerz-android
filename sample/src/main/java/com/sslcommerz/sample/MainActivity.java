package com.sslcommerz.sample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCEMITransactionInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer.ProductProfile;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCEnums;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCLanguage;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;
import com.sslwireless.sslsdkintegration.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SSLCTransactionResponseListener {

    private Context context;
    private Button tv_hello;
    private EditText etAmount, etPhone, storeID, storePass,multiCard;
    double amount = 15;
    String phoneNumber = "";
    RadioButton rbTest, rbLive, rbDev;
    SSLCommerzInitialization sslCommerzInitialization;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        context = this;

//        tv_hello = findViewById(R.id.tv_hello);
//        storeID = findViewById(R.id.storeID);
//        storePass = findViewById(R.id.storePass);
//        etAmount = findViewById(R.id.etAmount);
//        etPhone = findViewById(R.id.etPhone);
//        multiCard = findViewById(R.id.multiCard);
//        rbTest = findViewById(R.id.radio0);

        binding.storeID.setText("testbox");   
        binding.storePass.setText("qwerty");  

        binding.etPhone.setText("019xxxxxxxx");  
        binding.etCustomerName.setText("test");
        binding.etEmail.setText("test@gmail.com");
        binding.etAddress.setText("8 Banasree");
        binding.etCity.setText("Dhaka");
        binding.cbCustomer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                binding.etPhone.setText("019xxxxxxxx");
                binding.etCustomerName.setText("test");
                binding.etEmail.setText("test@gmail.com");
                binding.etAddress.setText("8 Banasree");
                binding.etCity.setText("Dhaka");
            }else{
                binding.etPhone.getText().clear();
                binding.etCustomerName.getText().clear();
                binding.etEmail.getText().clear();
                binding.etAddress.getText().clear();
                binding.etCity.getText().clear();
            }
        });

         binding.tvHello.setOnClickListener(v -> initTransaction());
    }

    private void initTransaction() {
        if (!binding.etAmount.getText().toString().isEmpty()) {
            amount = Double.parseDouble(binding.etAmount.getText().toString().trim());
        }
        if (!binding.etPhone.getText().toString().isEmpty()) {
            phoneNumber = binding.etPhone.getText().toString().trim();
        }


        if(binding.radio0.isChecked()) {
            sslCommerzInitialization = new SSLCommerzInitialization(binding.storeID.getText().toString(),
                    binding.storePass.getText().toString(), amount, SSLCCurrencyType.BDT,
                    "123456789098765", "food", SSLCSdkType.TESTBOX)
                    .addMultiCardName(binding.multiCard.getText().toString())
                    .addLanguage(SSLCLanguage.English)
                    .addOTPReadHashKey("");
        }else if(binding.radio1.isChecked()) {
            sslCommerzInitialization = new SSLCommerzInitialization(binding.storeID.getText().toString(),
                    binding.storePass.getText().toString(), amount, SSLCCurrencyType.BDT,
                    "123456789098765", "food", SSLCSdkType.LIVE)
                    .addMultiCardName(binding.multiCard.getText().toString())
                    .addOTPReadHashKey("");
        }

        //sslCommerzInitialization.addIpnUrl(binding.etIpn.getText().toString());

        final SSLCCustomerInfoInitializer SSLCCustomerInfoInitializer = setCustomerInfo();
        SSLCEMITransactionInitializer sslcEMITransactionInitializer = null;
        if (binding.cbEmiEnabled.isChecked()){
            sslcEMITransactionInitializer = new SSLCEMITransactionInitializer(1);
            sslcEMITransactionInitializer.setEmi_max_list_options(1);
            sslcEMITransactionInitializer.setEmi_options(1);
            sslcEMITransactionInitializer.setEmi_selected_inst(2);
        }
        SSLCProductInitializer SSLCProductInitializer = setProductInfo();

        SSLCShipmentInfoInitializer sslcShipmentInfoInitializer = null;
        if (!binding.etShippingItem.getText().toString().isEmpty()) {
            sslcShipmentInfoInitializer = new SSLCShipmentInfoInitializer(binding.etShippingMethod.getText().toString(),
                    Integer.parseInt(binding.etShippingItem.getText().toString()), new SSLCShipmentInfoInitializer.ShipmentDetails(binding.etShpiName.getText().toString(), binding.etShipAddress.getText().toString(),
                    "Dhaka", "1000", "BD"));
        }
        //ProviderInstaller.installIfNeeded(getContext());
        SSLCAdditionalInitializer sslcAdditionalInitializer = setAdditionalInfo();

        IntegrateSSLCommerz
                .getInstance(context)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(SSLCCustomerInfoInitializer)
                .addEMITransactionInitializer(sslcEMITransactionInitializer)
                .addShipmentInfoInitializer(sslcShipmentInfoInitializer)
                .addProductInitializer(SSLCProductInitializer)
                .addAdditionalInitializer(sslcAdditionalInitializer)
                .buildApiCall(this);
    }

    private SSLCCustomerInfoInitializer setCustomerInfo() {
        SSLCCustomerInfoInitializer sslcCustomerInfoInitializer = null;
        if (!phoneNumber.isEmpty()) {
            String customerName = binding.etCustomerName.getText().toString();
            String email = binding.etEmail.getText().toString();
            String address = binding.etAddress.getText().toString();
            String city = binding.etCity.getText().toString();
            sslcCustomerInfoInitializer = new SSLCCustomerInfoInitializer(customerName, email,
                    address, city, "", "", phoneNumber);
        }

        return sslcCustomerInfoInitializer;
    }

    private SSLCProductInitializer setProductInfo(){
        SSLCProductInitializer SSLCProductInitializer = null;
        if (!binding.etProductName.getText().toString().isEmpty()) {
            String productName = binding.etProductName.getText().toString();
            String productCat = binding.etProductCategory.getText().toString();
            String productAmount = binding.etProductAmount.getText().toString();
            String discountAmount = binding.etDiscountAmount.getText().toString();

            String spinnertext = binding.spinnerProfile.getSelectedItem().toString();
            if (spinnertext.equalsIgnoreCase("general")){
                SSLCProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.General(spinnertext, "A"));
            }else if (spinnertext.equalsIgnoreCase("physical-goods")) {
                SSLCProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.PhysicalGoods(spinnertext, "A"));
            }else if (spinnertext.equalsIgnoreCase("non-physical-goods")) {
                SSLCProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.NonPhysicalGoods(spinnertext, "A"));
            }else if (spinnertext.equalsIgnoreCase("telecom-vertical")) {
                SSLCProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.TelecomVertical(spinnertext, "A", "", ""));
            }else if (spinnertext.equalsIgnoreCase("travel-vertical")) {
                SSLCProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.TravelVertical(spinnertext, "A", "", "", ""));
            }else{
                SSLCProductInitializer = new SSLCProductInitializer(productName, productCat, new SSLCProductInitializer.ProductProfile.AirlinesTicket(spinnertext, "A", "", "", "",""));
            }
            if (!productAmount.isEmpty()) {
                SSLCProductInitializer.addDiscountAmount(Double.parseDouble(productAmount));
            }
            if (!discountAmount.isEmpty()) {
                SSLCProductInitializer.addDiscountAmount(Double.parseDouble(discountAmount));
            }
        }

        return SSLCProductInitializer;
    }

    private SSLCAdditionalInitializer setAdditionalInfo(){
        final SSLCAdditionalInitializer sslcAdditionalInitializer = new SSLCAdditionalInitializer();
        if (!binding.etValueA.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setValueA(binding.etValueA.getText().toString());
        }
        if (!binding.etValueB.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setValueB(binding.etValueB.getText().toString());
        }
        if (!binding.etValueC.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setValueC(binding.etValueC.getText().toString());
        }
        if (!binding.etValueD.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setValueD(binding.etValueD.getText().toString());
        }
        if (!binding.etBillNumber.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setBill_number(binding.etBillNumber.getText().toString());
        }
        if (!binding.etUserRefer.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setUser_refer(binding.etUserRefer.getText().toString());
        }
        if (!binding.etCampaignCode.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setCampaign_code(binding.etCampaignCode.getText().toString());
        }
        if (!binding.etInvoiceId.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setInvoice_id(binding.etInvoiceId.getText().toString());
        }
        if (!binding.etNoOffer.getText().toString().isEmpty()) {
            sslcAdditionalInitializer.setNo_offer(Integer.parseInt(binding.etNoOffer.getText().toString()));
        }

        return sslcAdditionalInitializer;
    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel transactionInfo) {
        //Toast.makeText(context, "Transaction successful: Amount " + transactionInfo.getAmount()+"TK", Toast.LENGTH_LONG).show();
        String vlue = "Transaction successful: Amount " + transactionInfo.getAmount()+" TK";
        FragmentManager manager = getSupportFragmentManager();
        final ConfirmDF dialogFragment = new ConfirmDF();
        Bundle args = new Bundle();
        args.putString(SSLCEnums.Common.Type.name(), vlue);
        dialogFragment.setArguments(args);
        //dialogFragment.show(manager, "dialog_fragment");
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(dialogFragment, "fragment_tag");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void transactionFail(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void closed(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
