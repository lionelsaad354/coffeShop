package net.lionelsaad.coffeshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int quantity=0;
    boolean hasWhippedCream=false;
    boolean hasChocolate=false;
    String customerName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        String priceMessage = "Total = Rp.";
        hasWhippedCream= CheckForWhippedCream();
        hasChocolate=CheckForChocolate();
        customerName=getCustomerName();
        int price = calculatePrice();
        createOrderSummary(price);
    }

    public void increment(View view) {
        if(quantity>=100) {
            ShowMessage("Pesanan Kopi Anda terlalu banyak");
            return;
        }
        else{
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    public void ShowMessage(String messageText)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,messageText,duration);
        toast.show();
    }

    public void decrement(View view) {
        if(quantity<=1) {
            ShowMessage("Anda tidak bisa mengorder kopi kurang dari 1");
            return;
        }
        else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }

    }

    /**
     * Method ini memberikan/menampilkan nilai pada layar.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * Kalkulasi harga orderan.
     *
     */
    private int calculatePrice() {
        int price = 0;
        int topping=0;
        if(hasChocolate){
            topping+=5000;
        }
        if(hasWhippedCream){
            topping+=7000;
        }
        price= (quantity*(10000+topping));

        return price;
    }

    private boolean CheckForWhippedCream()
    {
        CheckBox checkBox = findViewById(R.id.hasWhippedCream);
        boolean isChecked = checkBox.isChecked();
        return isChecked;
    }

    private boolean CheckForChocolate()
    {
        CheckBox checkBox = findViewById(R.id.hasChocolate);
        boolean isChecked = checkBox.isChecked();
        return isChecked;
    }

    private String getCustomerName()
    {
        EditText txtCustomerName = findViewById(R.id.txtCustomerName);
        String name = txtCustomerName.getText().toString();
        return name;
    }

    /*
    * Return a message with all the information of the order
    * @param price is the price of a cup of coffee
    * */
    private void createOrderSummary(int price){
        String message = getString(R.string.name)+" "+customerName+" \n";
        message+=getString(R.string.order_summary_whipped_cream)+" : "+hasWhippedCream+"\n";
        message+=getString(R.string.order_summary_chocolate)+" : "+hasChocolate+"\n";
        message+=getString(R.string.order_summary_quantity)+quantity+"\n";
        message+=getString(R.string.order_summary_price)+price+"\n";
        message+=getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // hanya aplikasi e-mail yang bisa menghandle ini
        String[] addresses = new String[1];
        addresses[0]="saadfauzi.sf@gmail.com";
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject)+ " "+customerName);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
